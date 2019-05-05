package test.job;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Getter
@Setter
@Slf4j
public class NodeCommandJob extends CommandJob {
    Integer flowId;
    Integer nodeId;
    Integer executionId;
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
            DefaultExecutor executor=getExecutor();
            int exitValue = executor.execute(CommandLine.parse(cmdLine));
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
