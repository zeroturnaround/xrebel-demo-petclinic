package org.springframework.samples.petclinic.schedulers;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.net.UnknownHostException;

public class PeriodicRepositoryPoller implements Job, Runnable {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    run();
  }

  @Override
  public void run() {
    MongoClient mongoClient;
    try {
      String host = System.getProperty("mongo.host", "localhost");
      int port = 27017;
      System.out.println("Polling Mongo at " + host + ":" + port);
      mongoClient = new MongoClient(host, port);
    } catch (UnknownHostException e) {
      System.err.println(e.getMessage());
      throw new RuntimeException(e);
    }

    DB db = mongoClient.getDB("mydb");
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

