package org.trista.pipeline;

import org.trista.Request;
import org.trista.Response;

import javax.servlet.ServletException;
import java.io.IOException;

public interface ValveContext {


    public String getInfo();

    public void invokeNext(Request request, Response response) throws IOException, ServletException;
}
