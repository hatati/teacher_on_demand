package models;

import org.bson.Document;

public interface Jsonable {
    public Document asDocument();
    public Jsonable fromDocument(Document document);
}
