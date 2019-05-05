package test;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Random;

@Slf4j
public class MyListener implements JobListener {

    private String name;

    public MyListener(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void jobToBeExecuted(JobExecutionContext context) {
        // do something with the event
        log.info("init job");
        Integer executionId= new Random().nextInt(10);
        String infoPath= (String) context.getJobDetail().getJobDataMap().get("infoPath");
        log.info(infoPath);
        context.getJobDetail().getJobDataMap().put("infoPath",infoPath+"_"+executionId);
        String infoPath1= (String) context.getJobDetail().getJobDataMap().get("infoPath");
        log.info(infoPath1);

    }

    public void jobWasExecuted(JobExecutionContext context,
                               JobExecutionException jobException) {
        // do something with the event
        log.info("update job");
        log.info("res "+context.getResult().toString());
    }

    public void jobExecutionVetoed(JobExecutionContext context) {
        // do something with the event
    }
}