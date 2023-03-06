package com.adobe.aem.guides.wknd.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingConstants;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.framework.Constants;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component(service = EventHandler.class, immediate = true, property = {
        Constants.SERVICE_DESCRIPTION + "=",
        EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
        EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/CHANGED",
        EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/REMOVED",
        EventConstants.EVENT_FILTER + "(&(path=/content/wknd/us/en/listener/osgieventhandler))"
})
public class OSGiEventHandler implements EventHandler {

    public static final String JOB_TOPIC = "steven/job";

    @Reference
    private JobManager jobManager;

    @Activate
    public void activate(ComponentContext context) {
        log.info("OSGiEventHandler activate");
    }

    @Deactivate
    public void deactivate() {
        log.info("OSGiEventHandler deactivate");
    }

    @Override
    public void handleEvent(Event event) {
        log.info("Resource event topic is : {}, path is : {}", event.getTopic(), event.getProperty(SlingConstants.PROPERTY_PATH));
        Map<String, Object> jobProperties = new HashMap<>();
        jobProperties.put("event", event.getTopic());
        jobProperties.put("path", event.getProperty(SlingConstants.PROPERTY_PATH));
        Job job = jobManager.addJob(JOB_TOPIC, jobProperties);
    }
}
