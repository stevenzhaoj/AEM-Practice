package com.adobe.aem.guides.wknd.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Component;

import static com.adobe.aem.guides.wknd.core.listener.OSGiEventHandler.JOB_TOPIC;

@Slf4j
@Component(immediate = true, service = JobConsumer.class, property = {
        JobConsumer.PROPERTY_TOPICS + "=" + JOB_TOPIC
})
public class TestJobConsumer implements JobConsumer {

    @Override
    public JobResult process(Job job) {
        log.info("job topic is {}, path is {}", job.getProperty("event"), job.getProperty("path"));
        return JobResult.OK;
    }
}
