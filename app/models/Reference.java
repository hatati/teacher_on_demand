package models;

import org.bson.Document;
import views.formdata.ReferenceFormData;

import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reference implements Jsonable, Idable {

    public final String id;
    public final Date start;
    public final Date end;
    public final String where;
    public final String description;
    public final String attachment;

    public Reference(String id, Date start, Date end, String where, String description, String attachment) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.where = where;
        this.description = description;
        this.attachment = attachment;
    }

    public Reference() {
        this.id = null;
        this.start = null;
        this.end = null;
        this.where = null;
        this.description = null;
        this.attachment = null;
    }

    public Reference(ReferenceFormData formData) {
        this.id = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date start = null;
        Date end = null;
        try {
            start = dateFormat.parse(formData.getStart());
            end = dateFormat.parse(formData.getEnd());
        } catch (ParseException exception) {
            System.out.println("Couldn't parse dates");
        }
        this.start = start;
        this.end = end;
        this.where = formData.getWhere();
        this.description = formData.getDescription();
        this.attachment = formData.getImage();
    }

    @Override
    public Document asDocument() {
        return new Document("id", id)
                .append("start", start)
                .append("end", end)
                .append("where", where)
                .append("description", description)
                .append("attachment", attachment);
    }

    @Override
    public Jsonable fromDocument(Document document) {
        return new Reference(document.getString("id"),
                document.getDate("start"),
                document.getDate("end"),
                document.getString("where"),
                document.getString("description"),
                document.get("attachment", attachment.getClass()));
    }

    @Override
    public String getId() {
        return id;
    }
}
