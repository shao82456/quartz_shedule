package shao;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

@Slf4j
public class ClusterServer {
    public void run() throws Exception {

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        sched.start();
    }

    public static void main(String[] args) throws Exception {

        ClusterServer example = new ClusterServer();
        example.run();
    }
}
