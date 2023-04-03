package com.adobe.aem.guides.wknd.core.models.impl;

import com.adobe.aem.guides.wknd.core.models.HTLSightly;
import com.adobe.aem.guides.wknd.core.models.dto.Book;
import com.adobe.cq.export.json.ExporterConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Model(
        adaptables = {SlingHttpServletRequest.class, Resource.class},
        adapters = {HTLSightly.class},
        resourceType = {HTLSightlyImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class HTLSightlyImpl implements HTLSightly {

    public final static String RESOURCE_TYPE = "wknd-guides/components/htlsightly";

    @Override
    public List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        books.add(Book.builder().name("AEM").price(11.11).build());
        books.add(Book.builder().name("Java").price(22.22).build());
        books.add(Book.builder().name("JavaScript").price(33.33).build());
        return books;
    }
}
