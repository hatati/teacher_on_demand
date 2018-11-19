package controllers;

import play.*;
import play.mvc.*;
import java.util.List;

import views.html.*;
import database.IDatabase;
import models.Teacher;

public class TeacherViewController extends Controller {
	// - View multiple teachers in list view
	// - View single teacher in "profile view"
	private IDatabase database;
	
	public Result presentTeacherList() {
		// Return the teacherListView to be rendered client side -> this view will fetch the teachers async via post requests.
		return ok("teacherListView.render()");
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
		return ok();
	}
}
