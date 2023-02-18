package com.adobe.aem.guides.wknd.core.models;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.ModelFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({AemContextExtension.class})
class TranslateTest {

    private final AemContext ctx = new AemContext();
    private Translate translate;

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(Translate.class);
        ctx.load().json("/com/adobe/aem/guides/wknd/core/models/TranslateTest.json", "/content");
        Resource resource = ctx.currentResource("/content/translate");
        translate = ctx.getService(ModelFactory.class).createModel(resource, Translate.class);
    }

    @Test
    void init() {
        assertEquals(translate.getAppId(), "appId");
        assertEquals(translate.getAppKey(), "appKey");
    }
}