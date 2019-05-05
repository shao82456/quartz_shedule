package test.po;

import lombok.Data;

import java.util.Date;

@Data
public class FlowExecution {
    public static final Integer RUNNING=2;
    public static final Integer FAILED=1;
    public static final Integer SUCCEED=0;

    Integer id;
    Date beginTime;
    Date endTime;
    Integer status;

    public FlowExecution() {
        beginTime=new Date();
        status=FlowExecution.RUNNING;
    }

}
