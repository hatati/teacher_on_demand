package controllers;

import database.Database;
import models.Teacher;
import models.University;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.*;

import views.formdata.LoginFormData;
import views.html.login_teacher;
import views.html.login_university;

import javax.inject.Inject;

public class LoginController extends Controller {

    @Inject
    FormFactory formFactory;

    public Result loginTeacher() {
        Form<LoginFormData> loginForm = formFactory.form(LoginFormData.class);

        return ok(login_teacher.render(loginForm, ""));
    }

    public Result submitTeacherLogin() {
        Form<LoginFormData> loginForm = formFactory.form(LoginFormData.class).bindFromRequest();
        LoginFormData loginFormData = loginForm.get();

        Teacher teacher;
        try {
            teacher = Database.getInstance().read(Teacher.class, 0, loginFormData.getUsername());
        } catch (NullPointerException exception) {
            exception.printStackTrace();
            teacher = null;
        }

        if (teacher != null && loginFormData.getPassword().equals(teacher.password)) {
            return redirect(routes.TeacherViewController.teacherProfile(teacher.email));
        } else {
            return ok(login_teacher.render(loginForm, "Coulnd't verify user, try again!"));
        }
    }

    public Result loginUniversity() {
        Form<LoginFormData> loginForm = formFactory.form(LoginFormData.class);

        return ok(login_university.render(loginForm, ""));
    }

    public Result submitUniversityLogin() {
        Form<LoginFormData> loginForm = formFactory.form(LoginFormData.class).bindFromRequest();
        LoginFormData formData = loginForm.get();

        University university;
        try {
            university = Database.getInstance().read(University.class, 0, formData.getUsername());
        } catch (NullPointerException exception) {
            System.out.println("Couldn't find university!");
            university = null;
        }

        if (university != null && formData.getPassword().equals(university.password)) {
            return redirect(routes.TeacherViewController.presentTeacherList());
        } else {
            return ok(login_university.render(loginForm, "Couldn't verify user, try again!"));
        }

    }
}
