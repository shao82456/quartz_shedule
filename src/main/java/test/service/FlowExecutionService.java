package test.service;

import test.po.Flow;
import test.po.FlowExecution;

import java.util.Date;

public class FlowExecutionService {
    public FlowExecution prepareExecution() {
        FlowExecution flowExecution=new FlowExecution();
        //insert db
        return flowExecution;
    }

    public void successExecution(Integer flowExecutionId){
        FlowExecution flowExecution=findExecutionById(flowExecutionId);
        flowExecution.setEndTime(new Date());
        flowExecution.setStatus(FlowExecution.SUCCEED);
        //success status,endTime
    }

    private FlowExecution findExecutionById(Integer flowExecutionId) {
        //find db
        return null;
    }

    public void failExecution(Integer flowExecutionId){
        //fail status,endTime
        FlowExecution flowExecution=findExecutionById(flowExecutionId);
        flowExecution.setEndTime(new Date());
        flowExecution.setStatus(FlowExecution.FAILED);

    }
}
