package org.springframework.samples.petclinic.schedulers;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

class MongoBackend {

  static MongoBackend INSTANCE = new MongoBackend();

  private DB db;

  private MongoBackend() {
    try {
      String host = System.getProperty("mongo.host", "localhost");
      int port = 27017;
      System.out.println("Initializing Mongo at " + host + ":" + port);
      MongoClient mongoClient = new MongoClient(host, port);
      db = mongoClient.getDB("mydb");
      db.cleanCursors(true);
    } catch (UnknownHostException e) {
      System.err.println(e.getMessage());
    }
  }

  void poll() {
    if (db == null)
      throw new RuntimeException("Mongo client should not be null");

    System.out.println("MongoBackend.poll");

    DBCollection supplements = db.getCollection("supplements");
    DBCollection supplementPrices = db.getCollection("supplement_prices");

    DBCursor cursor = supplements.find();

    while (cursor.hasNext()) {
      DBObject supplement = cursor.next();
      Object priceId = supplement.get("price_id");

      BasicDBObject query = new BasicDBObject("id", new BasicDBObject("$eq", priceId));
      DBObject price = supplementPrices.findOne(query);

      String name = String.valueOf(supplement.get("name"));
      String value = String.valueOf(price.get("value"));

      String item = "{\"" + name + "\": " + value + "}";
      System.out.println(item);
    }
    cursor.close();
  }

}
