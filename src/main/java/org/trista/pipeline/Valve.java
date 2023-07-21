package org.trista.pipeline;

import org.trista.Request;
import org.trista.Response;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * p102
 */
public interface Valve {

    public String getInfo();

    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException;
}
