package shao;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


public class Client {
    private static Logger log = LoggerFactory.getLogger(Client.class);

    public void run(String jobname,String trigername,String cmd,int repeat) throws Exception {

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        // define the job and ask it to run
        JobDetail job = newJob(CommandJob.class)
                .withIdentity(jobname, "default")
                .usingJobData("cmd",cmd)
                .build();



        Trigger trigger = newTrigger()
                .startNow()
                .withIdentity(trigername)
                .withSchedule(simpleSchedule().withIntervalInSeconds(3).withRepeatCount(repeat))
                .build();

        // schedule the job
        sched.scheduleJob(job, trigger);

        log.info("Remote job scheduled.");
        System.out.println("....");
    }

    public static void main(String[] args) throws Exception {
        if(args.length<3) {
            log.error("input job name and triger name");
        }
        Client example = new Client();
        example.run(args[0],args[1],args[2],Integer.parseInt(args[3]));
    }

}