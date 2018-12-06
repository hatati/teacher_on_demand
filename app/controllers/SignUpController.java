package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.signUp;


public class SignUpController  extends Controller {

    public Result index() {
        //Form<User> profileForm = Form.form(User.class);
        return ok(signUp.render());
    }

    public Result postData(){
        return TODO;
    }
}
