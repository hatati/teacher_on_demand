package controllers;

import database.Database;
import play.mvc.*;
import play.libs.Json;

import java.io.*;
import java.util.Base64;
import java.util.List;
import java.util.Date;

import views.html.*;
import database.IDatabase;
import models.Teacher;
import models.Reference;

public class TeacherViewController extends Controller {
	// - View multiple teachers in list view
	// - View single teacher in "profile view"
	private IDatabase database;
	
	public Result presentTeacherList() {
		// Return the teacherListView to be rendered client side -> this view will fetch the teachers async via post requests.
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

	public Result teachers() {
		// Retrieve post parameters
		// Call database to get teachers within the required range
		// return the teacher objects as json
		//

		String flamingos = loadAndEncodeImage("public/images/flamingos.jpg");

		Teacher teacher1 = new Teacher(
				"1", // id
				"Nermin", // name
				"1234", // password
				"nermin@student.sdu.dk", // email
				"Odense",
				List.of(new Reference( // jobs
						"1",
						new Date(2018, 1, 1), // from
						new Date(2018, 2, 1), // to
						"Teacher of Software Architecture", // where
						"I helped teach a database course", // description
						null)),
				List.of(new Reference( // education
						"1",
						new Date(2017, 2, 1),
						new Date(2017, 3, 1),
						"Software Engineer",
						"I studied Software Engineering at SDU",
						null)),
				flamingos, // image
				"I am 37 year old software engineer with teaching experience",
				List.of("Java", "Python", "NoSql"));
		
		Teacher teacher2 = new Teacher(
						"2", // id
						"Emil", // name
						"1234", // password
						"emil@student.sdu.dk", // email
						"Odense",
						List.of(new Reference( // jobs
								"1",
								new Date(2018, 1, 1), // from
								new Date(2018, 2, 1), // to
								"Teacher of Database Programming", // where
								"I helped teach a database course", // description
								null)),
						List.of(new Reference( // education
								"1",
								new Date(2017, 2, 1),
								new Date(2017, 3, 1),
								"Software Engineer",
								"I studied Software Engineering at SDU",
								null)),
						flamingos, // image
						"I am 37 year old software engineer with teaching experience",
						List.of("Java", "Python", "NoSql"));


		Teacher teacher3 = new Teacher(
				"3", // id
				"Anestis", // name
				"1234", // password
				"anestis@student.sdu.dk", // email
				"Odense",
				List.of(new Reference( // jobs
                        "1",
						new Date(2018, 1, 1), // from
						new Date(2018, 2, 1), // to
						"Teacher of Interaction Design", // where
						"I helped teach a database course", // description
						null)),
				List.of(new Reference( // education
						"1",
						new Date(2017, 2, 1),
						new Date(2017, 3, 1),
						"Software Engineer",
						"I studied Software Engineering at SDU",
						null)),
				flamingos, // image
				"I am 37 year old software engineer with teaching experience",
				List.of("Java", "Python", "NoSql"));

		Teacher teacher4 = new Teacher(
				"2", // id
				"Emil", // name
				"1234", // password
				"emil@student.sdu.dk", // email
				"Odense",
				List.of(new Reference( // jobs
						"2",
						new Date(2018, 1, 1), // from
						new Date(2018, 2, 1), // to
						"Tutor SDU", // where
						"I helped teach a database course", // description
						null)),
				List.of(new Reference( // education
						"2",
						new Date(2017, 2, 1),
						new Date(2017, 3, 1),
						"Software Engineering",
						"I studied Software Engineering at SDU",
						null)),
				flamingos, // image
				"I am 37 year old software engineer with teaching experience",
				List.of("Java", "Python", "NoSql"));

		Teacher teacher5 = new Teacher(
				"2", // id
				"Emil", // name
				"1234", // password
				"emil@student.sdu.dk", // email
				"Odense",
				List.of(new Reference( // jobs
						"2",
						new Date(2018, 1, 1), // from
						new Date(2018, 2, 1), // to
						"Tutor SDU", // where
						"I helped teach a database course", // description
						null)),
				List.of(new Reference( // education
						"2",
						new Date(2017, 2, 1),
						new Date(2017, 3, 1),
						"Software Engineering",
						"I studied Software Engineering at SDU",
						null)),
				flamingos, // image
				"I am 37 year old software engineer with teaching experience",
				List.of("Java", "Python", "NoSql"));
		List<Teacher> teachers = List.of(teacher1, teacher2, teacher3, teacher4, teacher5);

		return ok(Json.toJson(teachers));
	}

	public Result teachersFromDatabase() {
		Database database = Database.getInstance();
		List<Teacher> teachers = database.readAll(Teacher.class, 0, 10);

		return ok(Json.toJson(teachers));
	}

	public String loadAndEncodeImage(String path) {
		try {
			File file = new File(path);
			InputStream fileInputStream = new FileInputStream(file);
			byte[] imageBytes = new byte[(int) file.length()];
			fileInputStream.read(imageBytes, 0, imageBytes.length);
			fileInputStream.close();
			String imageString = Base64.getEncoder().encodeToString(imageBytes);
			return imageString;
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		return "";
	}
}
