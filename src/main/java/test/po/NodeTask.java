package test.po;

import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class NodeTask {
    public static final Integer RUNNING=2;
    public static final Integer FAILED=1;
    public static final Integer SUCCEED=0;

    Integer id;
    Integer flowId;
    Set<Integer> dependencies;
    Integer status;
    String name;
    String descript;
    String command;
    Date createTime;
    Date updateTime;


    public NodeTask(){
        dependencies=new HashSet<Integer>();
        status=NodeTask.RUNNING;
    }
}
