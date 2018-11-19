package database;

import java.util.List;
import java.lang.Class;

public interface IDatabase {

    /**
     * Reads a range of objects from the database.
     *
     * @param clazz the class of the object you wan't to read
     * @param from the ranking you wan't to read from
     * @param to the ranking you wan't to read to
     * @param <T> the type of the object to be read
     * @return a list of objects of type T
     */
    public <T> List<T> readAll(Class<T> clazz, int from, int to);

    /**
     * Reads a single object from the database.
     *
     * @param clazz the class of the object you wan't to read
     * @param ranking the ranking of the object you wan't to read (if you don't wan't to read with this parameter, set it to null)
     * @param username the username of the object you wan't to read (if you don't wan't to read with this parameter, set it to null)
     * @param <T> The type of the object to be read
     * @return An object of type T
     */
    public <T> T read(Class<T> clazz, int ranking, String username);

    /**
     * Writes an object to the database.
     *
     * @param object the object to be written.
     * @param <T> the type of the object
     */
    public <T> void write(T object);

    /**
     * Deletes an object in the database.
     *
     * @param object the object to be deleted
     * @param <T> the type of the object
     */
    public <T> void delete(T object);

    /**
     * Updates an object in the database.
     *
     * @param object the object to be updated
     * @param <T> the type of the object
     */
    public <T> void update(T object);
}
