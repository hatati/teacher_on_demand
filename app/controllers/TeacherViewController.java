package controllers;

import database.Database;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;
import play.libs.Json;

import java.io.*;
import java.sql.SQLOutput;
import java.util.Base64;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import util.Encoding;
import views.formdata.ReferenceFormData;
import views.formdata.TeacherFormData;
import views.html.*;
import database.IDatabase;
import models.Teacher;
import models.Reference;

import javax.inject.Inject;

import static util.Encoding.unpackEncodeAddImageTo;

public class TeacherViewController extends Controller {
	// - View multiple teachers in list view
	// - View single teacher in "profile view"
	@Inject
	FormFactory formFactory;

	private IDatabase database;
	
	public Result presentTeacherList() {
		return ok(teacher_list.render());
	}	

	public Result presentTeacher() {
		// Get the get parameter for the teacher
		// get the teacher object
		// pass the teacher to the view and return the view to be rendered clientside
		String username = "some_unique_username";
		Teacher teacher = database.read(Teacher.class, 0, username);
		return ok("teacher.render(teacher)");
	}

	public Result teachersFromDatabase() {
		Database database = Database.getInstance();
		List<Teacher> teachers = database.readAll(Teacher.class, 0, 10);

		return ok(Json.toJson(teachers));
	}

	public Result teacherProfile(String username) {
		Form<TeacherFormData> teacherForm;
		try {
			Teacher teacher = Database.getInstance().read(Teacher.class, 0, username);
			TeacherFormData teacherFormData = (new TeacherFormData()).setWithTeacher(teacher);
			teacherForm = formFactory.form(TeacherFormData.class).fill(teacherFormData);
		} catch (NullPointerException exception) {
			return notFound("Teacher doesn't exist.");
		}

		return ok(teacher_profile.render(teacherForm));
	}


	public void readMultipartImagesInto(TeacherFormData formData, Http.Request request) {
		Http.MultipartFormData<File> multipartFormData = request.body().asMultipartFormData();

        Http.MultipartFormData.FilePart<File> imagePart = multipartFormData.getFile("image");
		System.out.println("image: " + imagePart.getFile());
        if (imagePart != null) {
            String imageString = Encoding.encodeFile(imagePart.getFile());
            formData.setImage(imageString);
		}

        List<ReferenceFormData> jobs = formData.getJobs();
        for (int i = 0; i < jobs.size(); i++) {
            Http.MultipartFormData.FilePart<File> jobImagePart = multipartFormData.getFile("jobs[" + i + "].image");
            unpackEncodeAddImageTo(jobs, jobImagePart, i);
        }
        formData.setJobs(jobs);

        List<ReferenceFormData> educations = formData.getEducations();
        for (int i = 0; i < educations.size(); i++) {
            Http.MultipartFormData.FilePart<File> educationImagePart = multipartFormData.getFile("educations[" + i + "].image");
            unpackEncodeAddImageTo(educations, educationImagePart, i);
        }
        formData.setEducations(educations);
	}

	public Result submitUpdateTeacher() {
		Form<TeacherFormData> teacherForm = formFactory.form(TeacherFormData.class).bindFromRequest();
		TeacherFormData formData = teacherForm.get();

		Http.MultipartFormData<File> multipartFormData = request().body().asMultipartFormData();

		System.out.println("multipartform: " + multipartFormData);
        Http.MultipartFormData.FilePart<File> imagePart = multipartFormData.getFile("image");
        if (imagePart != null) {
            String imageString = Encoding.encodeFile(imagePart.getFile());
            formData.setImage(imageString);
        }

        int numberJobs = teacherForm.apply("jobs").indexes().size();
        List<ReferenceFormData> jobs = formData.getJobs();
        for (int i = 0; i < numberJobs; i++) {
            Http.MultipartFormData.FilePart<File> jobImagePart = multipartFormData.getFile("jobs[" + i + "].image");
            System.out.println(jobImagePart);
            unpackEncodeAddImageTo(jobs, jobImagePart, i);
        }

        List<ReferenceFormData> educations = formData.getEducations();
        for (int i = 0; i < educations.size(); i++) {
            Http.MultipartFormData.FilePart<File> educationImagePart = multipartFormData.getFile("educations[" + i + "].image");
            unpackEncodeAddImageTo(educations, educationImagePart, i);
        }
        formData.setEducations(educations);

		Teacher oldTeacher = Database.getInstance().read(Teacher.class, 0, formData.getEmail());
		System.out.println("teachers id: " + oldTeacher.getId());

		System.out.println("Image data: " + formData.getImage());

		String image = oldTeacher.image;
		if (formData.getImage() != null && !formData.getImage().equals("")) {
			image = formData.getImage();
		}

		Teacher newTeacher = new Teacher(oldTeacher.id,
				formData.getName(),
				formData.getPassword(),
				formData.getEmail(),
				formData.getCity(),
				formData.getJobs().stream().map(Reference::new).collect(Collectors.toList()),
				formData.getEducations().stream().map(Reference::new).collect(Collectors.toList()),
				image,
				formData.getDescription(),
				formData.getSkills());

		Database.getInstance().update(newTeacher);

		return redirect(routes.TeacherViewController.teacherProfile(newTeacher.email));
	}

	public Result addSkill() {
		Form<TeacherFormData> teacherSignupForm =
				formFactory.form(TeacherFormData.class).bindFromRequest();
		TeacherFormData formData = teacherSignupForm.get();
		readMultipartImagesInto(formData, request());
		teacherSignupForm.fill(formData);

		int index = teacherSignupForm.apply("skills").indexes().size();
		Map<String, String> data = teacherSignupForm.data();
		data.put("skills[" + index + "]", "");
		teacherSignupForm = teacherSignupForm.bind(data);

		return ok(teacher_profile.render(teacherSignupForm));
	}

	public Result addJob() {
		Form<TeacherFormData> teacherSignupForm =
				formFactory.form(TeacherFormData.class).bindFromRequest();
		TeacherFormData formData = teacherSignupForm.get();
		readMultipartImagesInto(formData, request());
		teacherSignupForm.fill(formData);

		int index = teacherSignupForm.apply("jobs").indexes().size();
		Map<String, String> data = teacherSignupForm.data();
		data.put("jobs[" + index + "]", "");
		teacherSignupForm = teacherSignupForm.bind(data);

		return ok(teacher_profile.render(teacherSignupForm));
	}

	public Result addEducation() {
		Form<TeacherFormData> teacherSignupForm =
				formFactory.form(TeacherFormData.class).bindFromRequest();
		TeacherFormData formData = teacherSignupForm.get();
		readMultipartImagesInto(formData, request());
		teacherSignupForm.fill(formData);


		int index = teacherSignupForm.apply("educations").indexes().size();
		Map<String, String> data = teacherSignupForm.data();
		data.put("educations[" + index + "]", "");
		teacherSignupForm = teacherSignupForm.bind(data);

		return ok(teacher_profile.render(teacherSignupForm));
	}
}
