import org.quartz.spi.ThreadExecutor;

public class DryRunThreadExecutor implements ThreadExecutor {

    public void initialize() {
    }
    public void execute(Thread thread) {
        // do nothing;
    }

}
