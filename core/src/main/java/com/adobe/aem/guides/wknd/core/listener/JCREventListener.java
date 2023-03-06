package com.adobe.aem.guides.wknd.core.listener;

import com.adobe.aem.guides.wknd.core.enums.SystemUserEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import java.util.Objects;

@Slf4j
@Component(immediate = true, service = EventListener.class)
public class JCREventListener implements EventListener {

    @Reference
    private SlingRepository slingRepository;
    private Session session;
    private String[] nodeTypes = {};

    @Activate
    public void activate(ComponentContext context) {
        log.info("JCREventListener activate");
        try {
            session = slingRepository.loginService(SystemUserEnum.SERVICE_USER_STEVEN, null);
            session.getWorkspace().getObservationManager().addEventListener(
                    this,                               // 指定EventHandler
                    Event.NODE_ADDED | Event.PROPERTY_ADDED,        // 监听事件类型，增加节点，增加属性
                    "/content/wknd/us/en/steven",                   // 监听路径
                    true,                                           // 是否监听路径下的子节点
                    null,                                           // UUID过滤器
                    null,                                           // 需要监听的节点类型，例如：cq:Page表示只监听页面
                    false                                           // 是否需要过滤当前用户的操作，一般为false，当前用户通常为ServiceUser
            );
        } catch (RepositoryException e) {
            log.error("Unable to register session : {}", e.getMessage(), e);
        }
    }

    @Deactivate
    public void deactivate() {
        if (Objects.nonNull(session)) {
            session.logout();
        }
    }

    @Override
    public void onEvent(EventIterator eventIterator) {
        while (eventIterator.hasNext()) {
            Event event = eventIterator.nextEvent();
            if (Objects.nonNull(event)) {
                try {
                    log.info("Event Type : {}, Event Path : {}", event.getType(), event.getPath());
                } catch (RepositoryException e) {
                    log.error("Unable to fetch event path");
                }
            }
        }
    }
}
