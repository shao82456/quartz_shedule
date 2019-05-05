package test.po;

import lombok.Data;

import java.util.Date;

@Data
public class NodeTaskExecution {
    public static final Integer RUNNING=2;
    public static final Integer FAILED=1;
    public static final Integer SUCCEED=0;

    Integer id;
    Integer flowId;
    Integer nodeId; //1开始，2结束，其他作为普通节点id
    Date beginTime;
    Date endTime;
    Integer status;
    Integer expectExit;
    Integer exit;
    String logHome;
    String executeHome;

    public NodeTaskExecution(Integer flowId,Integer nodeId,Integer expectExit) {
        this(flowId,nodeId);
        this.expectExit = expectExit;
    }

    public NodeTaskExecution(Integer flowId,Integer nodeId) {
        this.flowId=flowId;
        this.nodeId=nodeId;
        this.beginTime=new Date();
        this.expectExit=0;
        this.status=NodeTaskExecution.RUNNING;
    }
}
