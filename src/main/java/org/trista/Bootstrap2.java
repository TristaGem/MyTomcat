package org.trista;

import org.trista.connector.http.HttpConnector;
import org.trista.core.context.Context;
import org.trista.core.context.SimpleContext;
import org.trista.core.loader.Loader;
import org.trista.core.loader.SimpleLoader;
import org.trista.core.wrapper.SimpleWrapper;
import org.trista.core.wrapper.Wrapper;
import org.trista.core.wrapper.mapper.Mapper;
import org.trista.core.wrapper.mapper.SimpleContextMapper;
import org.trista.logger.SystemOutLogger;
import org.trista.pipeline.Pipeline;
import org.trista.pipeline.Valve;
import org.trista.pipeline.valve.ClientIPLoggerValve;
import org.trista.pipeline.valve.HeaderLoggerValve;

public class Bootstrap2 {
    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();

        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setServletClass("ModernServlet");
        Loader loader = new SimpleLoader();
        wrapper1.setLoader(loader);

        Valve valve1  = new HeaderLoggerValve();
        Valve valve2  = new ClientIPLoggerValve();

        ((Pipeline) wrapper1).addValve(valve1);
        ((Pipeline) wrapper1).addValve(valve2);

        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setServletClass("PrimitiveServlet");
        wrapper2.setLoader(loader);

        Context context = new SimpleContext();
        context.addChild(wrapper1);
        context.addChild(wrapper2);

        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("http");

        context.addMapper(mapper);
        context.setLoader(loader);
        context.addServletMapping("/servlet/PrimitiveServlet", "PrimitiveServlet");
        context.addServletMapping("/servlet/ModernServlet", "ModernServlet");
        context.setLogger(new SystemOutLogger());

        connector.setContainer(context);


        try{

            // create server socket through initialize
            connector.initialize();
            connector.start();

            // make the application wait until we press any key.
            System.in.read();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
