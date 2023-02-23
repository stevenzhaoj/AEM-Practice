package com.adobe.aem.guides.wknd.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;

/**
 * @ObjectClassDefinition 声明此类是一个OSGi配置信息类
 */
@ObjectClassDefinition(name = "Translate Configurations", description = "Translate Configurations")
public @interface TranslateConfig {

    /**
     * @AttributeDefinition 声明字段名称和描述
     */
    @AttributeDefinition(name = "App Id", description = "App Id")
    String appId() default "";

    @AttributeDefinition(name = "App Key", description = "App Key")
    String appKey() default "";

    @AttributeDefinition(name = "Enable or Disable to translate", description = "Enable or Disable to translate")
    boolean isEnable() default true;

    @AttributeDefinition(name = "Translate Type", description = "Translate Type", options = {
            @Option(label = "YouDao", value = "1"),
            @Option(label = "BaiDu", value = "2")
    })
    String type() default "1";
}
