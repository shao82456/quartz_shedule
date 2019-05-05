//package test;
//
//import com.google.common.collect.Maps;
//import lombok.extern.slf4j.Slf4j;
//import test.util.NodeTaskUtil;
//
//import java.util.Map;
//import java.util.UUID;
//
///**
// * Desc:
// * <p>
// * User: liulin ,Date: 2018/3/27 , Time: 22:15 <br/>
// * Email: liulin@cmss.chinamobile.com <br/>
// * To change this template use File | Settings | File Templates.
// */
//
//@Slf4j
//public enum TaskScheduleManager {
//    instance;
//
//
//    /**
//     * 当前所有 NodeTasks 会被当成一个整体进行调度（形成一个 有向无环图 tasks）
//     *
//     * @param nodeTasks      所有的tasks
//     * @param statusListener 用于监听任务的状态
//     */
//    public boolean startNodeTasks(Map<String, NodeTask> nodeTasks,Integer flowId) {
//
//        if(!NodeTaskUtil.ensureTasksMap(nodeTasks)){
//            log.error("flow {} node task map check fail",flowId);
//        }
//
//        ParentTask parentTask = ParentTask.builder()
//                .id(parentTaskId)
//                .nodeTasks(nodeTasksConcurrent)
//                .taskStatusListener(statusListener).build();
//
//        TaskScheduleManager.instance.startParentTask(parentTask);
//    }
//
//
//    /**
//     * 当前所有 NodeTasks 会被当成一个整体进行调度（形成一个 有向无环图 tasks）
//     *
//     * @param nodeTasks 所有的tasks
//     */
//    public void startNodeTasks(Map<String, NodeTask> nodeTasks) {
//        startNodeTasks(nodeTasks, null);
//    }
//
//    /**
//     * 开始ParentTask调度
//     * <p>
//     *
//     * @param parentTask
//     */
//    private void startParentTask(ParentTask parentTask) {
//        if (taskScheduleThreadMap.get(parentTask.getId()) == null) {
//            synchronized (taskScheduleThreadMap) {
//                TaskManager.instance.addTask(parentTask);
//
//                Thread scheduleThread = new Thread(() -> {
//                    TaskExecutor.instance.startTaskSchedule(parentTask.getId());
//                });
//                taskScheduleThreadMap.put(parentTask.getId(), scheduleThread);
//                scheduleThread.start();
//            }
//        } else {
//            throw new RuntimeException("Duplicate start parentTask:" + parentTask.getId());
//        }
//    }
//
////    /**
////     * 取消 ParentTask 调度
////     *
////     * @param parentTaskId
////     */
////    public void cancelParentTskSchedule(String parentTaskId) {
////        synchronized (taskScheduleThreadMap) {// 可能两个NodeTask同时失败，同时取消
////            if (taskScheduleThreadMap.get(parentTaskId) != null) {
////                taskScheduleThreadMap.get(parentTaskId).interrupt();
////            }
////        }
////    }
//}
