package org.trista.core.context;

import org.trista.Request;
import org.trista.Response;
import org.trista.container.Container;
import org.trista.core.wrapper.Wrapper;
import org.trista.core.wrapper.mapper.Mapper;
import org.trista.pipeline.Valve;
import org.trista.pipeline.ValveContext;

import javax.servlet.ServletException;
import java.io.IOException;

public class SimpleContextValve implements Valve {
    private Context context;


    public SimpleContextValve(Context context) {
        this.context = context;

    }


    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {
        Container wrapper = context.map(request, true);
        wrapper.invoke(request, response);
    }
}
