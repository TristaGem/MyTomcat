package org.trista.core.wrapper.mapper;

import org.trista.Request;
import org.trista.connector.http.HttpRequest;
import org.trista.container.Container;
import org.trista.core.context.Context;
import org.trista.core.wrapper.Wrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SimpleContextMapper implements Mapper{

    private Context context;

    private String protocol;

    @Override
    public Container getContainer() {
        return context;
    }

    @Override
    public void setContainer(Container container) {
        context = (Context)container;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public Container map(Request request, boolean update) {
        // Identify the context-relative URI to be mapped
        String contextPath =
                ((HttpServletRequest) request.getRequest()).getContextPath();
        String requestURI = ((HttpRequest) request).getDecodedRequestURI();
        String relativeURI = requestURI.substring(contextPath.length());
        // Apply the standard request URI mapping rules from the specification
        Wrapper wrapper = null;
        String servletPath = relativeURI;
        String pathInfo = null;
        String name = context.findServletMapping(relativeURI);
        if (name != null)
            wrapper = (Wrapper) context.findChild(name);
        return (wrapper);
    }

}
