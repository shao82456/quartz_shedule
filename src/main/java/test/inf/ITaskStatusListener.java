package test.inf;


/**
 * 用于主任务状态检查
 */
public interface ITaskStatusListener {
    /**
     * 当nodeTask执行success后，会触发该方法
     *
     * @param process    总任务进度
     * @param nodeTaskId 当前执行success的NodeTaskId
     */

    void before(double process, String nodeTaskId);

    void process(double process, String nodeTaskId);

    void onFail(String nodeTaskId, Throwable t);

    void onSuccess(String nodeTaskId, Throwable t);

}
