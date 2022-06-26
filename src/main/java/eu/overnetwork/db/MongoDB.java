package eu.overnetwork.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import eu.overnetwork.cfg.Settings;
import org.bson.Document;


public class MongoDB {
    static Settings cfg = new Settings();
    private static final MongoClient client = MongoClients.create(MongoClientSettings.builder().applicationName("Over-Network")
            .applyConnectionString(new ConnectionString(cfg.getDatabase())).build());

    // retrieve data base from server
    public static MongoDatabase database(String database) {
        return client.getDatabase(database);
    }

    // retrive a collection from a certain database on mongodb
    public static MongoCollection<Document> collection(String collectionName, String database) {
        return client.getDatabase(database).getCollection(collectionName);
    }

    // shutdown mongoclient
    public static void shutdown(){
        client.close();
    }

    // test connectivity print all the database names
    public static void testConnectivity() {
        client.listDatabaseNames().forEach(System.out::println);
    }

}
