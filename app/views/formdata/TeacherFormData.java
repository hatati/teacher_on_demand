package views.formdata;

import models.Reference;
import models.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeacherFormData {
    protected String name;
    protected String city;
    protected String email;
    protected String password;
    protected String repeatPassword;
    protected String description;
    protected String image;
    protected List<ReferenceFormData> jobs = new ArrayList<>();
    protected List<ReferenceFormData> educations = new ArrayList<>();
    protected List<String> skills = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<ReferenceFormData> getJobs() {
        return jobs;
    }

    public void setJobs(List<ReferenceFormData> jobs) {
        this.jobs = jobs;
    }

    public List<ReferenceFormData> getEducations() {
        return educations;
    }

    public void setEducations(List<ReferenceFormData> educations) {
        this.educations = educations;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Name: " + nullToString(getName()) + "\n" +
                "City: " + nullToString(getCity()) + "\n" +
                "Email: " + nullToString(getEmail()) + "\n" +
                "Password: " + nullToString(getPassword()) + "\n" +
                "repeatPassword: " + nullToString(getRepeatPassword()) + "\n" +
                "description: " + nullToString(getDescription()) + "\n" +
                "image: " + shorten(nullToString(getImage())) + "\n" +
                "jobs: " + nullToString(jobs.toString()) + "\n" +
                "educations: " + nullToString(educations.toString()) + "\n" +
                "skills" + nullToString(skills.toString()) + "\n";
    }

    public String nullToString(String s) {
        return s == null ? "" : s;
    }

    public String shorten(String s) {
        if (s.length() > 20) return s.substring(0, 20);
        else return s;
    }

    public TeacherFormData setWithTeacher(Teacher teacher) {
       this.name = teacher.name;
       this.city = teacher.city;
       this.email = teacher.email;
       this.description = teacher.description;
       this.image = teacher.image;
       this.jobs = teacher.jobs.stream().map(x -> (new ReferenceFormData()).setFromReference(x)).collect(Collectors.toList());
       this.jobs = teacher.educations.stream().map(x -> (new ReferenceFormData()).setFromReference(x)).collect(Collectors.toList());
       this.skills = teacher.skills;

       return this;
    }
}