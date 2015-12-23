package org.springframework.samples.petclinic.schedulers;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;

public class QuartzJobSpawner implements ServletContextListener {

  private Scheduler scheduler;

  public QuartzJobSpawner() {
    System.out.println("starting QuartzJobSpawner");
  }

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    try {
      scheduler = new StdSchedulerFactory().getScheduler();
      scheduler.start();

      JobDetail job = new JobDetail();
      job.setName("Periodic repository poller");
      job.setJobClass(PeriodicRepositoryPoller.class);

      SimpleTrigger trigger = new SimpleTrigger();
      trigger.setStartTime(new Date(System.currentTimeMillis() + 10000));
      trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
      trigger.setRepeatInterval(30000);
      trigger.setName("Custom repository trigger");

      scheduler.scheduleJob(job, trigger);

    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    try {
      if (scheduler != null) scheduler.shutdown();
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
  }
}
