package com.adobe.aem.guides.wknd.core.enums;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.HashMap;

public enum SystemUserEnum {

    USER_STEVEN {
        @Override
        public ResourceResolver getResourceResolver(final ResourceResolverFactory resourceResolverFactory) throws LoginException {
            HashMap<String, Object> map = new HashMap<>();
            map.put(ResourceResolverFactory.SUBSERVICE, SERVICE_USER_STEVEN);
            return resourceResolverFactory.getServiceResourceResolver(map);
        }
    };

    public static final String SERVICE_USER_STEVEN = "service-user-steven";

    public abstract ResourceResolver getResourceResolver(final ResourceResolverFactory resourceResolverFactory) throws LoginException;
}
