package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {

    private static Properties props;

    private static void getProps() {
        try (InputStream in = AlertRabbit.class.getClassLoader()
                .getResourceAsStream("rabbit.properties")) {
            props = new Properties();
            props.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        getProps();
        Class.forName(props.getProperty("driver-class-name"));
        try (Connection connection = DriverManager.getConnection(
                props.getProperty("url"),
                props.getProperty("username"),
                props.getProperty("password"))) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(5)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown(true);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            writeTimeInDB(connection);
        }

        private void writeTimeInDB(Connection connection) {
            try (PreparedStatement statement =
                         connection.prepareStatement(
                                 "insert into rabbit(created_date) values (?)")) {
                statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}