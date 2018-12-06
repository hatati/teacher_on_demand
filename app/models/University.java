package models;

import org.bson.Document;

public class University extends User implements Jsonable, Idable {

    public University(String id, String name, String password, String email, String city) {
        super(id, name, password, email, city);
    }

    public University() {
        super(null, null, null, null, null );
    }

    @Override
    public Document asDocument() {
        return new Document("id", id)
                .append("name", name)
                .append("password", password)
                .append("email", email)
                .append("city", city);
    }

    @Override
    public Jsonable fromDocument(Document document) {
        return new University(document.getString("id"),
                document.getString("name"),
                document.getString("password"),
                document.getString("email"),
                document.getString("city"));
    }

    @Override
    public String getId() {
        return id;
    }
}
