package org.trista.pipeline.valve;

import org.trista.Request;
import org.trista.Response;
import org.trista.container.Contained;
import org.trista.container.Container;
import org.trista.pipeline.Valve;
import org.trista.pipeline.ValveContext;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class HeaderLoggerValve implements Valve, Contained {
    protected Container container;

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {
        valveContext.invokeNext(request, response);
        System.out.println("Header Logger Valve");
        ServletRequest sreq = request.getRequest();

        if(sreq instanceof HttpServletRequest) {
            HttpServletRequest hreq = (HttpServletRequest) sreq;

            Enumeration headerNames = hreq.getHeaderNames();
            while(headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement().toString();
                String headerValue = hreq.getHeader(headerName);
                System.out.println(headerName + ':' + headerValue);
            }
        } else {
            System.out.println("Not an HTTP request");
        }
        System.out.println("----------------------------------------");
    }

    @Override
    public Container getContainer() {
        return null;
    }

    @Override
    public void setContainer(Container container) {

    }
}
