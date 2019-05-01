import lombok.Getter;
import lombok.Setter;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;


public class Client {
    private static Logger log = LoggerFactory.getLogger(Client.class);
    @Setter
    @Getter
    public static class CommandJob implements Job{
        public String cmd;

        /**
         * Quartz requires a public empty constructor so that the
         * scheduler can instantiate the class whenever it needs.
         */
        public CommandJob() {

        }

        /**
         * <p>
         * Called by the <code>{@link org.quartz.Scheduler}</code> when a
         * <code>{@link org.quartz.Trigger}</code> fires that is associated with
         * the <code>Job</code>.
         * </p>
         *
         * @throws JobExecutionException
         *             if there is an exception while executing the job.
         */
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            try {
                log.info(jobExecutionContext.getScheduler().getSchedulerInstanceId()+" running this job");
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

            Process process=null;
            try {
                process=Runtime.getRuntime().exec(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(process==null) {
                System.out.println("程序启动失败!");
                return;
            }
            BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line=null;

            try {
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }catch (IOException ioe){
                System.out.println("获取输出失败");
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                log.info(jobExecutionContext.getScheduler().getSchedulerInstanceId()+" end this job");
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
    }
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