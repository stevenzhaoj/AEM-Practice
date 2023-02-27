package com.adobe.aem.guides.wknd.core.service.impl;

import com.adobe.aem.guides.wknd.core.service.TranslateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

@Slf4j
@Component(service = TranslateService.class, immediate = true, name = "serviceA")
public class TranslateServiceAImpl implements TranslateService {

    @Activate
    public void activate(ComponentContext componentContext) {
        log.info("============== TranslateService activate ==============");
    }

    @Deactivate
    public void deactivate(ComponentContext componentContext) {
        log.info("============== TranslateService deactivate ==============");
    }

    @Modified
    public void modified(ComponentContext componentContext) {
        log.info("============== TranslateService modified ==============");
    }

    @Override
    public String getAppId(Resource resource) {
        ValueMap valueMap = resource.getValueMap();
        // 查询appId属性，如果为null，则返回""
        return valueMap.get("appId", "");
    }

    @Override
    public String getAppKey(Resource resource) {
        ValueMap valueMap = resource.getValueMap();
        return valueMap.get("appKey", "");
    }

    @Override
    public String getName() {
        return "TranslateServiceAImpl";
    }

    @Override
    public String translate() {
        return null;
    }
}
