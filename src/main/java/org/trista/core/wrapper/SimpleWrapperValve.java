package org.trista.core.wrapper;

import org.trista.Request;
import org.trista.Response;
import org.trista.pipeline.Valve;
import org.trista.pipeline.ValveContext;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleWrapperValve implements Valve {
    protected SimpleWrapper wrapper;

    public SimpleWrapperValve(SimpleWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public String getInfo() {
        return "SimpleWrapper's basic valve, the simpleWrapperValve";
    }

    @Override
    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {
        ServletRequest sreq = request.getRequest();
        ServletResponse sres = response.getResponse();
        Servlet servlet = null;
        HttpServletRequest hreq = null;
        if (sreq instanceof HttpServletRequest)
            hreq = (HttpServletRequest) sreq;
        HttpServletResponse hres = null;
        if (sres instanceof HttpServletResponse)
            hres = (HttpServletResponse) sres;

        try {
            servlet = wrapper.allocate();
            servlet.service(hreq, hres);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        catch (Throwable e) {
            System.out.println(e.toString());
        }
    }
}
