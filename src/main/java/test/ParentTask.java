package test;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Data
@Builder
public class ParentTask {
    private String id;
    private Map<String, NodeTask> nodeTasks;
    private AtomicInteger nodeTskSuccCount;  //成功结束的NodeTask个数
    private volatile boolean isTaskFail = false;

    public void validate() {
        if (Strings.isNullOrEmpty(id) || nodeTasks.isEmpty()) {
            throw new RuntimeException("ParentTask validate fail.");
        }
    }

    public NodeTask getNodeTask(String nodeTaskId) {
        return nodeTasks.get(nodeTaskId);
    }

    public int nodeTaskSuccess() {
        if (nodeTskSuccCount == null) {
            synchronized (this) {
                if (nodeTskSuccCount == null) {
                    nodeTskSuccCount = new AtomicInteger(0);
                }
            }
        }

        return nodeTskSuccCount.addAndGet(1);
    }

    public void nodeTaskFail() {
        this.setTaskFail(false);
    }

    public boolean isParentTaskFailOrFinish() {
        if (isTaskFail || nodeTskSuccCount.get() == nodeTasks.size()) {
            return true;
        }

        return false;
    }
}
