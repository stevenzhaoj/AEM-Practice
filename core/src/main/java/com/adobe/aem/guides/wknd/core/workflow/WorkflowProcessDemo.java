package com.adobe.aem.guides.wknd.core.workflow;

import com.adobe.aem.guides.wknd.core.enums.SystemUserEnum;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Component(service = WorkflowProcess.class, immediate = true, property = {
        "process.label=Workflow Process Demo",
        Constants.SERVICE_VENDOR + "=Workflow Process Demo",
        Constants.SERVICE_DESCRIPTION + "=Workflow Process Demo"
})
public class WorkflowProcessDemo implements WorkflowProcess {

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) throws WorkflowException {
        WorkflowData workflowData = workItem.getWorkflowData();
        if (workflowData.getPayloadType().equals("JCR_PATH")) {
            try {
                ResourceResolver resourceResolver = SystemUserEnum.USER_STEVEN.getResourceResolver(resourceResolverFactory);
                Session session = resourceResolver.adaptTo(Session.class);
                String path = workflowData.getPayload().toString() + "/jcr:content";
                log.info("path is {}", path);
                Node node = (Node) session.getItem(path);
                log.info("node is {}", node);
                String[] split = metaDataMap.get("PROCESS_ARGS", "Default").split(",");
                log.info("metaDataMap is {}", metaDataMap);
                log.info("split is {}", Arrays.toString(split));
                for (String arg : split) {
                    String[] strings = arg.split(":");
                    String key = strings[0];
                    log.info("key is {}", key);
                    String value = strings[1];
                    log.info("value is {}", value);
                    if (Objects.nonNull(node)) {
                        node.setProperty(key, value);
                    }
                }
                session.save();
            } catch (LoginException e) {
                log.error(e.getMessage(), e);
            } catch (PathNotFoundException e) {
                log.error(e.getMessage(), e);
            } catch (RepositoryException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}