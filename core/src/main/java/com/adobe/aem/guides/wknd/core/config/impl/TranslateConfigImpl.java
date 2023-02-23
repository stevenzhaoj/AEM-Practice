package com.adobe.aem.guides.wknd.core.config.impl;

import com.adobe.aem.guides.wknd.core.config.TranslateConfig;
import lombok.Getter;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;

/**
 * @Component 配置实现类是一个Service
 * @Designate 声明这个实现类是基于OSGiConfiguration信息类的
 */
@Component(service = TranslateConfigImpl.class, immediate = true, configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = TranslateConfig.class)
public class TranslateConfigImpl {

    @Getter
    private String appId;

    @Getter
    private String appKey;

    @Getter
    private boolean isEnable;

    @Getter
    private String type;

    @Activate
    @Modified
    protected void activate(final TranslateConfig translateConfig) {
        appId = translateConfig.appId();
        appKey = translateConfig.appKey();
        isEnable = translateConfig.isEnable();
        type = translateConfig.type();
    }

    @Deactivate
    protected void deactivate() {
        appId = null;
        appKey = null;
        isEnable = true;
        type = null;
    }
}
