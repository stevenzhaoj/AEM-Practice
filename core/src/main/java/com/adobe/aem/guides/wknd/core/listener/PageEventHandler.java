package com.adobe.aem.guides.wknd.core.listener;

import com.day.cq.wcm.api.PageEvent;
import com.day.cq.wcm.api.PageModification;
import lombok.extern.slf4j.Slf4j;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import java.util.Iterator;

@Slf4j
@Component(immediate = true, service = EventHandler.class, property = {
        EventConstants.EVENT_TOPIC + "=" + PageEvent.EVENT_TOPIC
})
public class PageEventHandler implements EventHandler {
    @Override
    public void handleEvent(Event event) {
        Iterator<PageModification> modifications = PageEvent.fromEvent(event).getModifications();
        while (modifications.hasNext()) {
            PageModification modification = modifications.next();
            log.info("PageEventHandler Event Type : {}", modification.getType());
            log.info("PageEventHandler Event Path : {}", modification.getPath());
        }
    }
}
