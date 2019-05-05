package test.service;

import test.po.NodeTask;
import test.po.NodeTaskExecution;

import java.util.Date;

public class NodeTaskExecutionService {


    public NodeTaskExecution prepareExecution(NodeTask task) {
        NodeTaskExecution execution=new NodeTaskExecution(task.getFlowId(),task.getId());
        String executeHome="prefix/"+"uploaded/"+"userName/"+"flowName_flowId/lib";
        String logHome="prefix/"+"uploaded/"+"userName/"+"flowName_flowId/log/"+"date";

        //make ,ensure

        execution.setExecuteHome(executeHome);
        execution.setLogHome(logHome);
        //insert db
        return execution;
    }

    public void updateExecution(Integer executionId, Integer exitVal) {
    }

    public boolean updateAndCheck(Integer executionId, Integer exitVal){
        NodeTaskExecution execution=null;//find db
        execution.setExit(exitVal);
        execution.setEndTime(new Date());

        boolean success=exitVal==execution.getExpectExit();
        Integer status=success?NodeTaskExecution.SUCCEED:NodeTaskExecution.FAILED;
        execution.setStatus(status);
        //update db
        return success;
    }
}
