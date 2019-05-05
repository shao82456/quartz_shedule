package test.service;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.GroupMatcher.*;
import org.quartz.impl.StdSchedulerFactory;
import test.job.NodeCommandJob;
import test.listener.NodeTaskListener;
import test.po.Flow;
import test.po.NodeTask;
import test.po.NodeTaskExecution;

import java.io.IOException;
import java.util.Map;
@Slf4j
public class FlowExecuteManager {

    Map<Integer, Map<Integer,NodeTask>> currentFlowsTasks= Maps.newHashMap();
    Map<Integer,Integer> currentFlowsExecution= Maps.newHashMap();

    Scheduler scheduler=new StdSchedulerFactory().getScheduler();
    NodeTaskExecutionService taskExecutionService=null;
    FlowExecutionService flowExecutionService=null;

    public FlowExecuteManager() throws SchedulerException {
        scheduler.start();
    }

    public void startFlow(Flow flow, Integer flowExecutionId) throws Exception {
        currentFlowsTasks.put(flow.getId(),toRunningNodeTasks(flow.getTasks()));
        currentFlowsExecution.put(flow.getId(),flowExecutionId);
        Integer flowId=flow.getId();
        NodeTaskListener flowListener=new NodeTaskListener(taskExecutionService,this);
        scheduler.getListenerManager().addJobListener(flowListener,jobGroupEquals("group"+flowId));

        NodeTask beginNode=currentFlowsTasks.get(flowId).get(1);
        runNode(beginNode);
    }

    private void runNode(NodeTask task) throws Exception {
        JobDetail job=node2JobDetail(task);
        if(job==null){
            log.error("build job fail for flow {} node {}",task.getFlowId(),task.getId());
            fail(task.getFlowId());
            return;
        }
        Trigger trigger =newTrigger()
                .withIdentity("trigger"+task.getId(),"group"+task.getFlowId())
                .startNow().build();
        scheduler.scheduleJob(job,trigger);
    }

    //默认trigger GroupName="trigger"+flowId
    //默认job GroupName="job"+flowId
    public void continueRunNode(Integer flowId) {
        if(isFlowEnd(flowId)) return;
        boolean finishFlow=true;
        Map<Integer,NodeTask> remain=currentFlowsTasks.get(flowId);
        for(NodeTask task:remain.values()){
            if(canRun(task,remain)) {
                try{
                    runNode(task);
                }catch (Exception e){
                    log.error("flow {} continue run failed",flowId);
                    log.error(e.getMessage());
                    fail(flowId);
                }
                finishFlow=false;
            }
        }
        if(finishFlow){
            if(remain.size()==0) success(flowId);
            else fail(flowId);
        }
    }

    private boolean canRun(NodeTask task,Map<Integer,NodeTask> remain){
        for(Integer taskId:task.getDependencies()){
            if(remain.containsKey(taskId))
                return false;
        }
        return true;
    }


    private void removeNode(Integer flowId,Integer nodeId){
        currentFlowsTasks.get(flowId).remove(nodeId);
    }


    public void fail(Integer flowId){
        flowExecutionService.successExecution(currentFlowsExecution.get(flowId));
    }


    public void success(Integer flowId){
        flowExecutionService.failExecution(currentFlowsExecution.get(flowId));
    }

    private boolean isFlowEnd(Integer flowId){
        return true;
    }

    private Map<Integer,NodeTask> toRunningNodeTasks(Map<Integer,NodeTask> tasks){
        return null;
    }

    private JobDetail node2JobDetail(NodeTask task) throws IOException {

        NodeTaskExecution execution=taskExecutionService.prepareExecution(task);
        if(execution==null)
            return null;



//        SUser taskUser = sUserService.findUserByTaskId(task.getId());
//        String taskHome = TaskUtil.getTaskRunHome(task, taskUser);
//
        JobDetail job = newJob(NodeCommandJob.class)
                .withIdentity("job"+task.getId(),"group"+task.getFlowId())
                .usingJobData("cmdHome",execution.getExecuteHome())
                .usingJobData("cmdLine", task.getCommand())
                .usingJobData("infoPath",execution.getLogHome()+"/info_"+execution.getId()+".log")
                .usingJobData("errorPath",execution.getLogHome()+"/error_"+execution.getId()+".log")
                .usingJobData("flowId",task.getFlowId())
                .usingJobData("nodeId",task.getId())
                .usingJobData("executionId",execution.getId())
                .build();

        return job;
    }

}
