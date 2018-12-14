package views.formdata;

import models.Reference;

import java.io.File;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReferenceFormData {
    protected String id;
    protected String start;
    protected String end;
    protected String where;
    protected String description;
    protected String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
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

    @Override
    public String toString() {
        return "id: " + nullToString(getId()) + "\n" +
                "start: " + nullToString(getStart()) + "\n" +
                "end: " + nullToString(getEnd()) + "\n" +
                "where: " + nullToString(getWhere()) + "\n" +
                "description: " + nullToString(getDescription()) + "\n" +
                "image: " + shorten(nullToString(getImage())) + "\n";
    }

    public String nullToString(String s) {
        return s == null ? "" : s;
    }

    public String shorten(String s) {
        if (s.length() > 20) return s.substring(0, 20);
        else return s;
    }

    public ReferenceFormData setFromReference(Reference reference) {
        this.id = reference.id;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        this.start = dateFormat.format(reference.start);
        this.end = dateFormat.format(reference.end);
        this.where = reference.where;
        this.description = reference.description;
        this.image = reference.attachment;

        return this;
    }
}
