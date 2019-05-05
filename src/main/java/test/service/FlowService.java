package test.service;

import org.quartz.SchedulerException;
import test.po.Flow;
import test.po.FlowExecution;

public class FlowService {
    FlowExecuteManager flowExecuteManager=new FlowExecuteManager();
    FlowExecutionService flowExecutionService=new FlowExecutionService();

    public FlowService() throws SchedulerException {
    }

    public boolean ensuerFlow(Flow flow){
        //判断为空，换，加入开始结束task等
        return true;
    }

    public boolean executeFlowNow(Integer flowId) throws Exception {
        Flow flow=findFlowById(flowId);
        if(!ensuerFlow(flow)){
            return false;
        }
        if(!checkReady(flow)){
            return false;
        }

        FlowExecution flowExecution= flowExecutionService.prepareExecution();
        flowExecuteManager.startFlow(flow,flowExecution.getId());
        return true;
    }

    private boolean checkReady(Flow flow) {
        //判断日志，运行依赖
        return true;
    }

    public Flow findFlowById(Integer flowId){
        return new Flow();
    }

}
