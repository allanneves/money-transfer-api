package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class ApiController extends Controller {

    public Result index() {
        return ok("Loaded");
    }
}
