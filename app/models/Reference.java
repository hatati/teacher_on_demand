package models;

import org.bson.Document;

import java.nio.Buffer;
import java.util.Date;

public class Reference implements Jsonable, Idable {

    public final String id;
    public final Date start;
    public final Date end;
    public final String where;
    public final String description;
    public final Buffer attachment;

    public Reference(String id, Date start, Date end, String where, String description, Buffer attachment) {
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
