package models;


public abstract class User {

    public final String id;
    public final String name;
    public final String password;
    public final String email;
    public final String city;

    public User(String id, String name, String password, String email, String city) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.city = city;
    }
}
