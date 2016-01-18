package org.springframework.samples.petclinic.schedulers;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
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

      JobDetail job = JobBuilder.newJob(PeriodicRepositoryPoller.class)
          .withIdentity("dummyJobName", "group1").build();

      Trigger trigger = TriggerBuilder
          .newTrigger()
          .withIdentity("dummyTriggerName", "group1")
          .withSchedule(
              SimpleScheduleBuilder.simpleSchedule()
                  .withIntervalInSeconds(10).repeatForever())
          .startAt(new Date(System.currentTimeMillis() + 5000))
          .build();

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
