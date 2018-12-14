package database;

import com.mongodb.Block;
import com.mongodb.client.*;
import models.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

public class Database implements IDatabase {
     private final MongoDatabase database;
     private final Map<Class, MongoCollection<Document>> map;

     private static Database instance;

     private Database() {
          database = MongoClients.create().getDatabase("tod");

          map = new HashMap<>();
          map.put(Reference.class, database.getCollection("jobs"));
          map.put(Teacher.class, database.getCollection("teachers"));
          map.put(University.class, database.getCollection("universities"));
     }

     public static Database getInstance() {
          if (instance == null) {
               instance = new Database();
          }

          return instance;
     }

     @Override
     public <T extends Jsonable> List<T> readAll(Class<T> clazz, int from, int to) {
          System.out.printf("readAll(clazz: %s, from: %d, to: %d)\n", clazz.toString(), from, to);

          MongoCollection<Document> collection = map.get(clazz);
          MongoCursor<Document> cursor = collection.find().skip(from).limit(to).iterator();

          try {
               T instance = (T) clazz.getDeclaredConstructor().newInstance();
               List<T> result = new ArrayList<>();

               while(cursor.hasNext()) {
                    result.add((T) instance.fromDocument(cursor.next()));
               }

               return result;
          } catch (NoSuchMethodException
                  | InstantiationException
                  | IllegalAccessException
                  | InvocationTargetException exception) {
               exception.printStackTrace();
          } finally {
               cursor.close();
          }

          return null;
     }

     @Override
     public <T extends Jsonable> T read(Class<T> clazz, int ranking, String username) throws NullPointerException {
          MongoCollection<Document> collection = map.get(clazz);
          Document result = collection.find(eq("email", username)).first();

          try {
               return (T) clazz.getDeclaredConstructor().newInstance().fromDocument(result);
          } catch (NoSuchMethodException |
                  InstantiationException |
                  IllegalAccessException |
                  InvocationTargetException exception) {
               exception.printStackTrace();
          }

          return null;
     }

     // TODO: Remove ID on writes, and then on reads, load "_id" as the id. "_id" is packed in an object.
     @Override
     public <T extends Jsonable> void write(T object) {
          MongoCollection<Document> collection = map.get(object.getClass());
          collection.insertOne(object.asDocument());
     }

     @Override
     public <T extends Jsonable & Idable> void delete(T object) {
          MongoCollection<Document> collection = map.get(object.getClass());
          collection.deleteOne(eq("id", object.getId()));
     }

     @Override
     public <T extends Jsonable & Idable> void update(T object) {
          MongoCollection<Document> collection = map.get(object.getClass());
          collection.replaceOne(eq("_id", new ObjectId(object.getId())), object.asDocument());
     }
}
