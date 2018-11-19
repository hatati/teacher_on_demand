package models;

import java.nio.Buffer;
import java.util.List;

public class Teacher extends User{

    public final List<Reference> jobs;
    public final List<Reference> educations;
    public final Buffer image;
    public final String description;
    public final List<String> skills;

    public Teacher(String id, String name, String password, String email, String city,
                   List<Reference> jobs, List<Reference> educations, Buffer image,
                   String description, List<String> skills) {
        super(id, name, password, email, city);
        this.jobs = jobs;
        this.educations = educations;
        this.image = image;
        this.description = description;
        this.skills = skills;
    }
}
