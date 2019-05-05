package test.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flow {
    Integer id;
    Integer uid;
    String name;
    String descript;
    String cron;
    String libpath;
    Integer status;
    Date createTime;
    Date updateTime;

    Map<Integer,NodeTask> tasks;
}
