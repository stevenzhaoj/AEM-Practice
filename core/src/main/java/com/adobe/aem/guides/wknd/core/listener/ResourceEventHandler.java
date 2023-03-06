package com.adobe.aem.guides.wknd.core.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.service.component.annotations.Component;

import java.util.List;


@Slf4j
@Component(immediate = true, service = ResourceChangeListener.class, property = {
        ResourceChangeListener.PATHS + "=/content/wknd/us/en/listener/resourcechangelistener",
        ResourceChangeListener.CHANGES + "=ADDED",
        ResourceChangeListener.CHANGES + "=CHANGED",
        ResourceChangeListener.CHANGES + "=REMOVED"
})
public class ResourceEventHandler  implements ResourceChangeListener {
    @Override
    public void onChange(List<ResourceChange> list) {
        for (ResourceChange resourceChange : list) {
            log.info("Event is {}, Resource is {}", resourceChange.getType(), resourceChange.getPath());
        }
    }
}
