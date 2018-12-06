package models;

import org.bson.Document;

import java.nio.Buffer;
import java.util.List;

public class Teacher extends User implements Jsonable, Idable {

    public final List<Reference> jobs;
    public final List<Reference> educations;
    public final String image;
    public final String description;
    public final List<String> skills;

    public Teacher(String id, String name, String password, String email, String city,
                   List<Reference> jobs, List<Reference> educations, String image,
                   String description, List<String> skills) {
        super(id, name, password, email, city);
        this.jobs = jobs;
        this.educations = educations;
        this.image = image;
        this.description = description;
        this.skills = skills;
    }

    public Teacher() {
        super(null, null, null, null, null);
        this.jobs = null;
        this.educations = null;
        this.image = null;
        this.description = null;
        this.skills = null;
    }

    @Override
    public Document asDocument() {
        return new Document("id", id)
                .append("name", name)
                .append("password", password)
                .append("email", email)
                .append("city", city)
                .append("jobs", jobs)
                .append("educations", educations)
                .append("image", image)
                .append("description", description)
                .append("skills", skills);
    }

    @Override
    public Jsonable fromDocument(Document document) {
        return new Teacher(document.getString("id"),
                document.getString("name"),
                document.getString("password"),
                document.getString("email"),
                document.getString("city"),
                document.get("jobs", this.jobs.getClass()),
                document.get("educations", this.educations.getClass()),
                document.getString("image"),
                document.getString("description"),
                document.get("skills", this.skills.getClass()));
    }

    @Override
    public String getId() {
        return id;
    }
}
