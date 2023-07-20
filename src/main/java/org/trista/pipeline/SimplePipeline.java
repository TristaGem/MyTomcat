package org.trista.pipeline;

import org.trista.Request;
import org.trista.Response;
import org.trista.container.Contained;
import org.trista.container.Container;
import org.trista.core.loader.SimpleLoader;
import org.trista.util.StringManager;

import javax.servlet.ServletException;
import java.io.IOException;

public class SimplePipeline implements Pipeline{

    private Valve[] children;

    private Valve basic;

    private Container container;

    private ValveContext context;

    public SimplePipeline(Container container) {
        this.container = container;
        this.children = new Valve[0];
        this.context = new SimplePipelineValveContext();
    }

    /**
     * p99 Listing 5.1
     * inner class used to fac
     */
    protected class SimplePipelineValveContext implements ValveContext{
        protected int stage = 0;
        private String info;


        @Override
        public String getInfo() {
            return info;
        }

        @Override
        public void invokeNext(Request request, Response response) throws IOException, ServletException {
            int subscript = stage;
            stage += 1;
            if(subscript < children.length) {
                children[subscript].invoke(request, response, this);
            }else if((subscript == children.length) && basic != null){
                basic.invoke(request, response, this);
            } else {

                //TODO: the response Roses not showing, and after 2nd request,
                /*
                javax.servlet.ServletException: No more Valves in the Pipeline processing this request
	at org.trista.pipeline.SimplePipeline$SimplePipelineValveContext.invokeNext(SimplePipeline.java:52)
	at org.trista.pipeline.SimplePipeline.invoke(SimplePipeline.java:91)
	at org.trista.core.wrapper.SimpleWrapper.invoke(SimpleWrapper.java:121)
	at org.trista.connector.http.HttpProcessor.process(HttpProcessor.java:975)
	at org.trista.connector.http.HttpProcessor.run(HttpProcessor.java:1092)
	at java.lang.Thread.run(Thread.java:750)
                 */
                throw new ServletException("No more Valves in the Pipeline processing this request");
            }
        }
    }

    @Override
    public Valve getBasic() {
        return basic;
    }

    @Override
    public void setBasic(Valve valve) {
        if (valve instanceof Contained)
            ((Contained) valve).setContainer(this.container);
        basic = valve;
    }

    @Override
    public void addValve(Valve valve) {
        if(valve instanceof Container) {
            ((Contained) valve).setContainer(this.container);
        }
        synchronized (children) {
            Valve[] results = new Valve[children.length+1];
            for(int i = 0; i < children.length; i++) {
                results[i] = children[i];
            }
            results[children.length] = valve;
            children = results;
        }
    }

    @Override
    public Valve[] getValves() {
        return children;
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        context.invokeNext(request, response);
    }

    @Override
    public void removeValve(Valve valve) {
        if(children.length > 0) {
            synchronized (children) {
                if(children.length > 0) {
                    Valve[] results = new Valve[children.length - 1];
                    int j = 0;
                    for (int i = 0; i < children.length; i++) {
                        if (valve != children[i]) {
                            results[j] = children[i];
                            j++;
                        }

                    }
                    children = results;
                }
            }
        }

    }
}
