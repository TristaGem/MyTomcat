package org.trista.pipeline;

import org.trista.Request;
import org.trista.Response;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * p101
 */
public interface Pipeline {

    public Valve getBasic();

    public void setBasic(Valve valve);

    public void addValve(Valve valve);

    public Valve[] getValves();

    public void invoke(Request request, Response response) throws IOException, ServletException;

    public void removeValve(Valve valve);
}
