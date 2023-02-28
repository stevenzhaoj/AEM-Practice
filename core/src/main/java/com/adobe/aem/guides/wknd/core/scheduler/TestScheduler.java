package com.adobe.aem.guides.wknd.core.scheduler;

import com.adobe.aem.guides.wknd.core.config.TestSchedulerConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import java.time.LocalDateTime;

@Slf4j
@Designate(ocd = TestSchedulerConfig.class)
@Component(immediate = true, service = Runnable.class)
public class TestScheduler implements Runnable {

    private String schedulerName;
    private int schedulerId;

    @Reference
    private Scheduler scheduler;

    @Activate
    protected void activate(final TestSchedulerConfig config) {
        log.info("===== TestScheduler activate =====");
        schedulerName = config.schedulerName();
        schedulerId = config.schedulerName().hashCode();
        addScheduler(config);
    }

    @Deactivate
    protected void deactivate(TestSchedulerConfig config) {
        log.info("===== TestScheduler deactivate =====");
        removeScheduler(config);
    }

    private void removeScheduler(TestSchedulerConfig config) {
        scheduler.unschedule(config.schedulerName());
        scheduler.unschedule(String.valueOf(schedulerId));
    }

    private void addScheduler(TestSchedulerConfig config) {
        boolean isEnabled = config.enabled();
        if (isEnabled) {
            ScheduleOptions scheduleOptions = scheduler.EXPR(config.schedulerExpression());
            scheduleOptions.name(config.schedulerName());
            scheduleOptions.name(String.valueOf(schedulerId));
            scheduleOptions.canRunConcurrently(true);
            scheduler.schedule(this, scheduleOptions);
            log.info("Test Scheduler {} is added", schedulerId);
        } else {
            log.info("Test Scheduler {} is disabled", schedulerId);
        }
    }

    @Modified
    protected void modified(TestSchedulerConfig config) {
        log.info("===== TestScheduler modified =====");
        removeScheduler(config);
        schedulerId = config.schedulerName().hashCode();
        addScheduler(config);
    }

    @Override
    public void run() {
        log.info("TestScheduler running by : {}", LocalDateTime.now());
    }
}
