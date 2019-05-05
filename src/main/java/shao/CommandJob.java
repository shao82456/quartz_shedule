package shao;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Setter
@Getter
@Slf4j
public class CommandJob implements Job {
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

        try {
            log.info(jobExecutionContext.getScheduler().getSchedulerInstanceId()+" end this job");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}