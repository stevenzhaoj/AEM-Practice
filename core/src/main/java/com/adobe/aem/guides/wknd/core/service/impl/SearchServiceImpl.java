package com.adobe.aem.guides.wknd.core.service.impl;

import com.adobe.aem.guides.wknd.core.enums.SystemUserEnum;
import com.adobe.aem.guides.wknd.core.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component(immediate = true, service = SearchService.class)
public class SearchServiceImpl implements SearchService {

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public List<String> searchPageByName(String pageName) {
        String sql = "SELECT * FROM [cq:Page] AS nodes WHERE NAME(nodes) = \"" + pageName + "\"";
        List<String> pages = new ArrayList<>();
        try {
            ResourceResolver resourceResolver = SystemUserEnum.USER_STEVEN.getResourceResolver(resourceResolverFactory);
            Session session = resourceResolver.adaptTo(Session.class);
            Query query = session.getWorkspace().getQueryManager().createQuery(sql, Query.JCR_SQL2);
            QueryResult queryResult = query.execute();
            NodeIterator nodes = queryResult.getNodes();
            while (nodes.hasNext()) {
                Node node = nodes.nextNode();
                pages.add(node.getPath());
                log.info("node name is {}", node.getName());
            }
        } catch (LoginException | RepositoryException e) {
            log.error(e.getMessage(), e);
        }
        return pages;
    }
}
