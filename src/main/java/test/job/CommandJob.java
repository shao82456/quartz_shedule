package test.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.*;

@Setter
@Getter
@Slf4j
public class CommandJob implements Job {
        String cmdHome;
        String cmdLine;
        String infoPath;
        String errorPath;

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
            try{
                log.info(jobExecutionContext.getJobDetail().getJobDataMap().getString("infoPath"));
                //配置executor
                DefaultExecutor executor=getExecutor();
                //执行前更新状态，记录log
                int exitValue = executor.execute(CommandLine.parse(cmdLine));

                //执行结束更新状态，记录log

                jobExecutionContext.setResult(exitValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




        public DefaultExecutor getExecutor() throws FileNotFoundException {
            DefaultExecutor executor = new DefaultExecutor();
            executor.setWorkingDirectory(new File(cmdHome));
            executor.setStreamHandler(new PumpStreamHandler(new FileOutputStream(infoPath),new FileOutputStream(errorPath)));
            return executor;
        }
}
