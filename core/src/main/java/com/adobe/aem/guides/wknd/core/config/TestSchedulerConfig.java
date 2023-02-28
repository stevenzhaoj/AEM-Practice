package com.adobe.aem.guides.wknd.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Test Scheduler Configuration", description = "Test Scheduler Configuration")
public @interface TestSchedulerConfig {

    @AttributeDefinition(
            name = "Scheduler name",
            description = "Name of the scheduler",
            type = AttributeType.STRING)
    public String schedulerName() default "Test Scheduler Configuration";

    @AttributeDefinition(
            name = "Enabled",
            description = "True, if scheduler service is enabled",
            type = AttributeType.BOOLEAN)
    public boolean enabled() default true;

    @AttributeDefinition(
            name = "scheduler expression",
            description = "Cron expression used by the scheduler",
            type = AttributeType.STRING)
    public String schedulerExpression() default "0/10 * * * * ?";
}
