package com.adobe.aem.guides.wknd.core.service;

import org.apache.sling.api.resource.Resource;

public interface TranslateService {

    String getAppId(Resource resource);

    String getAppKey(Resource resource);

    String getName();

    String translate();
}
