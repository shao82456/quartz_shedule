package test;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import test.inf.INodeTaskWork;

import java.util.Set;

/**
 */
@Data
@AllArgsConstructor
public abstract class NodeTask implements INodeTaskWork {
    private String id;                   //唯一标示
    private Set<String> dependences = Sets.newConcurrentHashSet();   //依赖的nodeTask id
    private NodeTaskStatus nodeTaskStatus = NodeTaskStatus.init;

    /**
     * @param id          nodeTaskId
     * @param dependences 依赖
     */
    public NodeTask(String id, Set<String> dependences) {
        this.id = id;
        this.dependences = dependences;
    }

    public NodeTask(String id) {
        this.id = id;
    }
}
