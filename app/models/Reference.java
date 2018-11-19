package models;

import java.nio.Buffer;
import java.util.Date;

public class Reference {

    public final Date start;
    public final Date end;
    public final String where;
    public final String description;
    public final Buffer attachment;

    public Reference(Date start, Date end, String where, String description, Buffer attachment) {
        this.start = start;
        this.end = end;
        this.where = where;
        this.description = description;
        this.attachment = attachment;
    }
}
