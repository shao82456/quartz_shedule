package test.listener;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import test.service.FlowExecuteManager;
import test.service.NodeTaskExecutionService;

import java.util.Map;

@Data
@AllArgsConstructor
public class NodeTaskListener implements JobListener {
    NodeTaskExecutionService executionService;
    FlowExecuteManager flowExecuteManager;
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        //do some log
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        JobDataMap dataMap= context.getJobDetail().getJobDataMap();
        Integer executionId=dataMap.getInt("executionId");
        Integer exitVal= (Integer) context.getResult();
        Integer flowId=dataMap.getInt("flowId");

        if(!executionService.updateAndCheck(executionId,exitVal))
            flowExecuteManager.fail(flowId);
        else
            flowExecuteManager.continueRunNode(flowId);
    }
}
