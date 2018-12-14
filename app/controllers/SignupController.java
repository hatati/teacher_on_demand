package controllers;

import database.Database;
import models.Teacher;
import models.University;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import util.Encoding;
import views.formdata.LoginFormData;
import views.formdata.ReferenceFormData;
import views.formdata.TeacherFormData;
import views.formdata.UniversityFormData;
import views.html.login_teacher;
import views.html.signup_teacher;
import views.html.signup_university;

import javax.inject.Inject;
import java.io.File;
import java.util.List;
import java.util.Map;


public class SignupController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result signupTeacher() {
        Form<TeacherFormData> teacherSignupForm = formFactory.form(TeacherFormData.class);
        return ok(signup_teacher.render(teacherSignupForm));
    }

    public void unpackEncodeAddImageTo(List<ReferenceFormData> references,
                                       Http.MultipartFormData.FilePart<File> imagePart, int index) {
        if (imagePart != null) {
            String jobImageString = Encoding.encodeFile(imagePart.getFile());
            ReferenceFormData reference = references.get(index);
            reference.setImage(jobImageString);
            references.set(index, reference);
        }
    }

    public Result submitTeacher() {
        Form<TeacherFormData> teacherSignupForm = formFactory
                .form(TeacherFormData.class).bindFromRequest();

        TeacherFormData teacherSignupFormData = teacherSignupForm.get();

        Http.MultipartFormData<File> multipartFormData = request().body().asMultipartFormData();

        Http.MultipartFormData.FilePart<File> imagePart = multipartFormData.getFile("image");
        if (imagePart != null) {
            String imageString = Encoding.encodeFile(imagePart.getFile());
            teacherSignupFormData.setImage(imageString);
        }

        int numberJobs = teacherSignupForm.apply("jobs").indexes().size();
        List<ReferenceFormData> jobs = teacherSignupFormData.getJobs();
        for (int i = 0; i < numberJobs; i++) {
            Http.MultipartFormData.FilePart<File> jobImagePart = multipartFormData.getFile("jobs[" + i + "].image");
            System.out.println(jobImagePart);
            unpackEncodeAddImageTo(jobs, jobImagePart, i);
        }

        List<ReferenceFormData> educations = teacherSignupFormData.getEducations();
        for (int i = 0; i < educations.size(); i++) {
            Http.MultipartFormData.FilePart<File> educationImagePart = multipartFormData.getFile("educations[" + i + "].image");
            unpackEncodeAddImageTo(educations, educationImagePart, i);
        }
        teacherSignupFormData.setEducations(educations);

        //TODO: Do some validation on the data. If validation doesn't check out -> render signup view with errors.

        Teacher teacher = new Teacher(teacherSignupFormData);
        Database.getInstance().write(teacher);

        return ok(login_teacher.render(formFactory.form(LoginFormData.class), ""));
    }

    public Result signupUniversity() {
        Form<UniversityFormData> universitySignupForm =
                formFactory.form(UniversityFormData.class);

        return ok(signup_university.render(universitySignupForm));
    }

    public Result submitUniversity() {
        Form<UniversityFormData> universitySignupForm =
                formFactory.form(UniversityFormData.class).bindFromRequest();
        UniversityFormData formData = universitySignupForm.get();

        University university = new University(formData);
        Database.getInstance().write(university);

        return redirect(routes.LoginController.loginUniversity());
    }

    public Result addSkill() {
        Form<TeacherFormData> teacherSignupForm =
                formFactory.form(TeacherFormData.class).bindFromRequest();
        int index = teacherSignupForm.apply("skills").indexes().size();
        Map<String, String> data = teacherSignupForm.data();
        data.put("skills[" + index + "]", "");
        teacherSignupForm = teacherSignupForm.bind(data);

        return ok(signup_teacher.render(teacherSignupForm));
    }

    public Result addJob() {
        Form<TeacherFormData> teacherSignupForm =
                formFactory.form(TeacherFormData.class).bindFromRequest();
        int index = teacherSignupForm.apply("jobs").indexes().size();
        Map<String, String> data = teacherSignupForm.data();
        data.put("jobs[" + index + "]", "");
        teacherSignupForm = teacherSignupForm.bind(data);

        return ok(signup_teacher.render(teacherSignupForm));
    }

    public Result addEducation() {
        Form<TeacherFormData> teacherSignupForm =
                formFactory.form(TeacherFormData.class).bindFromRequest();
        int index = teacherSignupForm.apply("educations").indexes().size();
        Map<String, String> data = teacherSignupForm.data();
        data.put("educations[" + index + "]", "");
        teacherSignupForm = teacherSignupForm.bind(data);

        return ok(signup_teacher.render(teacherSignupForm));
    }

}
