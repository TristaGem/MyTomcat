package org.trista.core.context;

import org.trista.Request;
import org.trista.Response;
import org.trista.container.Contained;
import org.trista.container.Container;
import org.trista.core.loader.Loader;
import org.trista.core.wrapper.Wrapper;
import org.trista.core.wrapper.mapper.Mapper;
import org.trista.exception.LifecycleException;
import org.trista.lifecycle.Lifecycle;
import org.trista.lifecycle.LifecycleListener;
import org.trista.lifecycle.LifecycleSupport;
import org.trista.logger.Logger;
import org.trista.pipeline.Pipeline;
import org.trista.pipeline.SimplePipeline;
import org.trista.pipeline.Valve;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * It uses the SimpleContextMapper as its mapper and SimpleContextValve as its basic valve.
 * The context application uses the same loader and the two
 */
public class SimpleContext implements Context, Lifecycle, Pipeline {

    private Pipeline pipeline = new SimplePipeline(this);

    private Loader loader;

    private Logger logger;

    private String name;

    private Container[] children;

    private Map<String, String> servletMapping = new HashMap<>();

    private Mapper mapper;

    private LifecycleSupport lifecycle = new LifecycleSupport(this);

    private Container parent;

    private boolean started;

    private boolean stopped;



    public SimpleContext() {
        this.children = new Container[0];
        pipeline.setBasic(new SimpleContextValve(this));
    }

    @Override
    public void addWrapper(Wrapper wrapper) {
        synchronized (children) {
            Container[] results = new Container[children.length+1];

            for(int i = 0; i < children.length; i++) {
                Container container = children[i];
                results[i] = container;
            }
            results[children.length] = wrapper;
            children = results;
        }
    }

    @Override
    public void addMapper(Mapper mapper) {
        this.mapper = mapper;
        this.mapper.setContainer(this);
    }

    @Override
    public Wrapper createWrapper() {
        return null;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Loader getLoader() {
        return this.loader;
    }

    @Override
    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Container getParent() {
        return parent;
    }

    @Override
    public void setParent(Container container) {

    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader parent) {

    }

    @Override
    public void addChild(Container child) {
        synchronized (children) {
            Container[] results = new Container[children.length+1];

            for(int i = 0; i < children.length; i++) {
                Container container = children[i];
                results[i] = container;
            }
            results[children.length] = child;
            child.setParent(this);
            children = results;
        }
    }

    @Override
    public Container findChild(String name) {
        synchronized (children) {
            for(Container container: children) {
                if(container.getName().equals(name)) {
                    return container;
                }
            }
        }
        return null;
    }

    @Override
    public Container[] findChildren() {
        return children;
    }

    @Override
    public Valve getBasic() {
        return pipeline.getBasic();
    }

    @Override
    public void setBasic(Valve valve) {
        pipeline.setBasic(valve);
        ((Contained) valve).setContainer(this);
    }

    @Override
    public void addValve(Valve valve) {
        pipeline.addValve(valve);
    }

    @Override
    public Valve[] getValves() {
        return pipeline.getValves();
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        pipeline.invoke(request, response);
    }

    @Override
    public void removeValve(Valve valve) {
        pipeline.removeValve(valve);
    }

    @Override
    public void removeChild(Container child) {

    }

    public void addServletMapping(String path, String servletName) {
        synchronized (servletMapping) {
            servletMapping.put(path, servletName);
        }
    }

    @Override
    public String findServletMapping(String relativeURI) {
        synchronized (servletMapping) {
            return ((String) servletMapping.get(relativeURI));
        }
    }

    @Override
    public Container map(Request request, boolean update) {
        //this method is taken from the map method in org.apache.cataline.core.ContainerBase
        //the findMapper method always returns the default mapper, if any, regardless the
        //request's protocol
//        Mapper mapper = findMapper(request.getRequest().getProtocol());
//        if (mapper == null)
//            return (null);

        // Use this Mapper to perform this mapping
        return (mapper.map(request, update));
    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {
        lifecycle.addLifecycleListener(listener);
    }

    @Override
    public LifecycleListener[] findLifecycleListeners() {
        return lifecycle.findLifecycleListeners();
    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {
        lifecycle.removeLifecycleListener(listener);
    }

    @Override
    public void start() throws LifecycleException {
        if (started) {
            throw new LifecycleException("Simple Context already started");

        }
        // notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
        started = true;

        try {
            // Start our subordinate components, if necessary
            if((loader != null) && (loader instanceof Lifecycle))
                ((Lifecycle) loader).start();

            // Start our child containers
            Container children[] = findChildren();
            for(int i = 0; i < children.length; i++) {
                if(children[i] instanceof  Lifecycle) {
                    ((Lifecycle) children[i]).start();
                }
            }

            // start the valves in our pipeline(including the basic)
            if (pipeline instanceof Lifecycle) {
                ((Lifecycle) pipeline).start();
            }
            lifecycle.fireLifecycleEvent(START_EVENT, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //notify our interested lifecycle listeners about the after start event
        lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);

    }

    @Override
    public void stop() throws LifecycleException {
        if (!started) {
            throw new LifecycleException("Simple Context not started yet");

        }
        // notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(BEFORE_STOP_EVENT, null);
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);
        started = false;

        try {
            // stop the valves in our pipeline(including the basic)
            if (pipeline instanceof Lifecycle) {
                ((Lifecycle) pipeline).stop();
            }

            // stop our child containers
            Container children[] = findChildren();
            for(int i = 0; i < children.length; i++) {
                if(children[i] instanceof  Lifecycle) {
                    ((Lifecycle) children[i]).stop();
                }
            }

            // stop our subordinate components, if necessary
            if((loader != null) && (loader instanceof Lifecycle))
                ((Lifecycle) loader).stop();


        } catch (Exception e) {
            e.printStackTrace();
        }

        //notify our interested lifecycle listeners about the after start event
        lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);

    }
}
