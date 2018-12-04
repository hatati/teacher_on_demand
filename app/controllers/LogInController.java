package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class LogInController extends Controller {

    public Result index() {
        return ok(LogIn.render());
    }

    public Result login(){
        return TODO;
    }
}
