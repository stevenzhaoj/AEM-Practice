package com.adobe.aem.guides.wknd.core.service.impl;

import com.adobe.aem.guides.wknd.core.service.InjectService;
import org.osgi.service.component.annotations.Component;

@Component(service = InjectService.class, immediate = true, name = "injectService")
public class InjectServiceImpl implements InjectService {
    @Override
    public String getName() {
        return "InjectServiceImpl";
    }
}
