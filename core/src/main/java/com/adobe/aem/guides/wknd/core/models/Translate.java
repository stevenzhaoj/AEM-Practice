package com.adobe.aem.guides.wknd.core.models;

import com.adobe.aem.guides.wknd.core.config.ContextAwareConfigurationDemo;
import com.adobe.aem.guides.wknd.core.config.TranslateConfig;
import com.adobe.aem.guides.wknd.core.config.impl.TranslateConfigImpl;
import com.adobe.aem.guides.wknd.core.service.TranslateService;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.osgi.service.component.annotations.Reference;
import com.day.cq.search.PredicateGroup;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Model(
        adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = {Translate.class},
        resourceType = {Translate.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class Translate {

    protected static final String RESOURCE_TYPE = "wknd/components/translate";

    @SlingObject
    private ResourceResolver resourceResolver;
    @SlingObject
    private Resource resource;
    @Self
    private SlingHttpServletRequest request;
    @ScriptVariable
    private ValueMap properties;
    @Inject
    private Page currentPage;
    @Inject
    private InheritanceValueMap pageProperties;

    @Getter
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String appId;

    @Getter
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String appKey;

    private String className;

    @Getter
    private boolean isEnable;

    @Getter
    private String type;

    @OSGiService(filter = "(component.name=service)")
//    @OSGiService
    private TranslateService translateService;

    @OSGiService
    private TranslateConfigImpl translateConfig;

    private ContextAwareConfigurationDemo config;

    @PostConstruct
    public void init() throws WCMException, PersistenceException, RepositoryException {
        // resourceResolver是资源转换类，可以通过此类获取对应的页面，也可以转换为其他API
//        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        // pageManager可以通过页面路径获取到对应的Page对象
//        Page page = pageManager.getPage("/content/wknd/us/en/steven");
        // 也可以直接使用currentPage
//        log.info("page title = {}", page.getTitle());
        // 所以从pageManager中通过路径获取到Page对象和currentPage对象，表示的是JCR中同一个页面节点
//        log.info("current page title = {}", currentPage.getTitle());
        // 通过SlingHttpServletRequest也可以获取到当前页面的地址信息
//        String path = request.getHeader("Referer");
//        log.info("page request path : {}", path);
        // 获取到地址信息后，就可以拿到这个页面下所有的节点和数据内容，由于page和currentPage表示同一个页面节点，所以拿到的页面内容是一致的
//        ValueMap pageVM = page.getProperties();
//        ValueMap currentPageVM = currentPage.getProperties();
//        log.info("title = {}", pageVM.get(JcrConstants.JCR_TITLE, String.class));
//        log.info("title = {}", currentPageVM.get(JcrConstants.JCR_TITLE, String.class));
        // 也可以通过Sling API来创建页面和资源，通过模板来创建页面
//        String templatePath = "/conf/wknd/settings/wcm/templates/steven";
//        pageManager.create("/content/wknd/us/en", "steven-new", templatePath, "steven new page", true);
//        resourceResolver.commit();
        // 获取组件属性及内容
//        ValueMap valueMap = resource.getValueMap();
//        log.info("appId from resource = {}", valueMap.get("appId", String.class));
//        log.info("appId from properties = {}", properties.get("appId", String.class));

//        String appId1 = translateService.getAppId(resource);
//        log.info("appId = {}, appId1 = {}", appId, appId1);
        appId = translateConfig.getAppId();
        log.info("app id = {}", appId);
        appKey = translateConfig.getAppKey();
        log.info("app key = {}", appKey);
        isEnable = translateConfig.isEnable();
        log.info("isEnable = {}", isEnable);
        type = translateConfig.getType();
        log.info("type = {}", type);

        // QueryBuild
        Map<String, String> map = new HashMap<>();
        map.put("path", "/content/wknd");
        map.put("property", "sling:resourceType");
        map.put("property.value", "wknd/components/translate");
        map.put("1_property", "appId");
        map.put("1_property.value", "1111");
        PredicateGroup predicateGroup = PredicateGroup.create(map);
        QueryBuilder queryBuilder = resourceResolver.adaptTo(QueryBuilder.class);
        Session session = resourceResolver.adaptTo(Session.class);
        Query query = queryBuilder.createQuery(predicateGroup, session);
        SearchResult result = query.getResult();
        List<Hit> hits = result.getHits();
        for (Hit hit : hits) {
            String path = hit.getPath();
            log.info("hit path : {}", path);
            Resource componentResource = resourceResolver.getResource(path);
            ValueMap valueMap = componentResource.getValueMap();
            String appId1 = valueMap.get("appId", String.class);
            String appKey1 = valueMap.get("appKey", String.class);
            log.info("appId1 = {}, appKey1 = {}", appId1, appKey1);
        }

        config = resource.adaptTo(ConfigurationBuilder.class).as(ContextAwareConfigurationDemo.class);
        log.info("siteName is {}", config.siteName());
        log.info("siteUrl is {}", config.siteUrl());
    }

    public String getClassName() {
        return Objects.isNull(translateService) ? "" : translateService.getName();
    }

    public String getSiteName() {
        return Objects.nonNull(config) ? config.siteName() : "empty";
    }

    public String getSiteUrl() {
        return Objects.nonNull(config) ? config.siteUrl() : "empty";
    }
}
