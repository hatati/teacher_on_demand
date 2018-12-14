package models;

import org.bson.Document;
import org.bson.types.ObjectId;
import views.formdata.ReferenceFormData;
import views.formdata.TeacherFormData;

import java.util.ArrayList;
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

public Teacher(TeacherFormData formData) {
        super(null, formData.getName(), formData.getPassword(), formData.getEmail(), formData.getCity());
        this.jobs = convertList(formData.getJobs());
        this.educations = convertList(formData.getEducations());
        this.image = formData.getImage();
        this.description = formData.getDescription();
        this.skills = formData.getSkills();
    }

    public List<Reference> convertList(List<ReferenceFormData> formDataList) {
        List<Reference> references = new ArrayList<>();
        for (int i = 0; i < formDataList.size(); i++) {
            references.add(new Reference(formDataList.get(i)));
        }

        return references;
    }

    private List<Document> referencesToDocuments(List<Reference> references) {
        List<Document> result = new ArrayList<>();
        for (int i = 0; i < references.size(); i++) {
            result.add(references.get(i).asDocument());
        }

        return result;
    }

    private List<Reference> documentsToReference(List<Document> documents) {
        List<Reference> result = new ArrayList<>();
        for (int i = 0; i < documents.size(); i++) {
            Reference reference = (Reference) (new Reference()).fromDocument(documents.get(i));
            result.add(reference);
        }

        return result;
    }

    @Override
    public Document asDocument() {
        List<Document> jobs = referencesToDocuments(this.jobs);
        List<Document> educations = referencesToDocuments(this.educations);

        return new Document()
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
        List<Document> jobs_ = (List<Document>) document.get("jobs");
        List<Document> educations_ = (List<Document>) document.get("educations");
        List<Reference> jobs = documentsToReference(jobs_);
        List<Reference> educations = documentsToReference(educations_);
        List<String> skills = (List<String>) document.get("skills");


        return new Teacher(document.getObjectId("_id").toHexString(),
                document.getString("name"),
                document.getString("password"),
                document.getString("email"),
                document.getString("city"),
                jobs,
                educations,
                document.getString("image"),
                document.getString("description"),
                skills);
    }

    @Override
    public String getId() {
        return id;
    }
}
