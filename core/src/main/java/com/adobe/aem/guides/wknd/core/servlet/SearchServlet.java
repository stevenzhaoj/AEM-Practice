package com.adobe.aem.guides.wknd.core.servlet;

import com.adobe.aem.guides.wknd.core.service.SearchService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.osgi.framework.Constants.SERVICE_DESCRIPTION;

@Slf4j
@Component(service = Servlet.class, property = {
        SERVICE_DESCRIPTION + "= Search page by name",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/search"
})
public class SearchServlet extends SlingAllMethodsServlet {

    @Reference
    private SearchService searchService;

    @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws IOException {
        String name = request.getParameter("name");
        log.info("name is : {}", name);
        List<String> strings = searchService.searchPageByName(name);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        writer.print(gson.toJson(strings));
        writer.close();
    }
}
