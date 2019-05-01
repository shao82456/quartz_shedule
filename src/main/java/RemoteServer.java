import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;


@Slf4j
public class RemoteServer {

    public void run(boolean clear) throws Exception {

        // First we must get a reference to a scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler scheduler = sf.getScheduler();

        if (clear) {
            log.warn("deleting all existing jobs/triggers");
            scheduler.clear();
        }
        scheduler.start();
}

    public static void main(String[] args) throws Exception {
        boolean clearJobs=false;
        if(args.length>0&&args[0].equalsIgnoreCase("clear"))
            clearJobs=true;

        new RemoteServer().run(clearJobs);
    }
}
