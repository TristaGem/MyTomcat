package org.trista;

import org.trista.core.loader.Loader;
import org.trista.connector.http.HttpConnector;
import org.trista.core.SimpleContainer;
import org.trista.core.loader.SimpleLoader;
import org.trista.core.wrapper.SimpleWrapper;
import org.trista.core.wrapper.Wrapper;
import org.trista.logger.SystemOutLogger;
import org.trista.pipeline.Pipeline;
import org.trista.pipeline.Valve;
import org.trista.pipeline.valve.ClientIPLoggerValve;
import org.trista.pipeline.valve.HeaderLoggerValve;

import java.io.IOException;

/**
 * P110-P111
 */
public final class Bootstrap1 {
    public static void main(String[] args) throws IOException {
        HttpConnector connector = new HttpConnector();

        Wrapper wrapper = new SimpleWrapper();
        wrapper.setServletClass("ModernServlet");

        Loader loader = new SimpleLoader();
        wrapper.setLoader(loader);

        Valve valve1  = new HeaderLoggerValve();
        Valve valve2  = new ClientIPLoggerValve();

        ((Pipeline) wrapper).addValve(valve1);
        ((Pipeline) wrapper).addValve(valve2);

        wrapper.setLogger(new SystemOutLogger());
        connector.setContainer(wrapper);

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
