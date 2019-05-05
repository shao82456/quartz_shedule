package test;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import test.job.CommandJob;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.GroupMatcher.*;

public class JobListen {
    public static void main(String[] args) throws SchedulerException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        // define the job and ask it to run
        JobDetail job = newJob(CommandJob.class)
                .withIdentity("job2", "group1")
                .usingJobData("cmdHome","test_run")
                .usingJobData("cmdLine","date")
                .usingJobData("infoPath","test_run/log/info.log")
                .usingJobData("errorPath","test_run/log/error.log")
                .build();


        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .build();




        sched.scheduleJob(job, trigger);


        sched.getListenerManager().addJobListener(new MyListener("job1l"), jobGroupEquals("group1"));
        sched.start();
//        sched.shutdown(true);
    }
}
