package com.adobe.aem.guides.wknd.core.config;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

@Configuration(label = "Context Aware Configuration Demo", description = "Context Aware Configuration Demo")
public @interface ContextAwareConfigurationDemo {

    @Property(label = "Site Name", description = "Please enter Site Name")
    String siteName() default "steven aem practice";

    @Property(label = "Site Url", description = "Please enter Site Url")
    String siteUrl() default "http://localhost:4502";
}
