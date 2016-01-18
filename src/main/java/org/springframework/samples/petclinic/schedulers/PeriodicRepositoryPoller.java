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
    MongoBackend.INSTANCE.poll();
  }
}

