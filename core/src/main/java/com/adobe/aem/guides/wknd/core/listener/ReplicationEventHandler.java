package com.adobe.aem.guides.wknd.core.listener;

import com.day.cq.replication.ReplicationAction;
import lombok.extern.slf4j.Slf4j;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

@Slf4j
@Component(immediate = true, service = EventHandler.class, property = {
        EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC
})
public class ReplicationEventHandler implements EventHandler {
    @Override
    public void handleEvent(Event event) {
        log.info("ReplicationEventHandler Event Type : {}", event.getTopic());
        log.info("ReplicationEventHandler Event Path : {}", ReplicationAction.fromEvent(event).getPath());
    }
}
