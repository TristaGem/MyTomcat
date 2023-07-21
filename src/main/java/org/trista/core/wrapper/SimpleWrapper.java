package org.trista.core.wrapper;

import org.trista.Request;
import org.trista.Response;
import org.trista.container.Container;
import org.trista.core.loader.Loader;
import org.trista.logger.Logger;
import org.trista.pipeline.Pipeline;
import org.trista.pipeline.SimplePipeline;
import org.trista.pipeline.Valve;
import org.trista.util.StringManager;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

public class SimpleWrapper implements Wrapper, Pipeline {
    protected Logger logger;

    protected Loader loader;

    protected Container parent = null;
    private String name;

    private Pipeline pipeline = new SimplePipeline(this);

    private Servlet servlet;

    private String servletClass;

//    private StringManager sm = StringManager.getManager("org.trista.core");

    public SimpleWrapper() {
        pipeline.setBasic(new SimpleWrapperValve(this));
    }


    @Override
    public String getInfo() {
        return null;
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
        this.parent = container;
    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader parent) {
        this.loader = loader;
    }

    @Override
    public void addChild(Container child) {
//        throw new IllegalStateException
//                (sm.getString("standardWrapper.notChild"));
    }

    @Override
    public Container findChild(String name) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return new Container[0];
    }

    @Override
    public Valve getBasic() {
        return pipeline.getBasic();
    }

    @Override
    public void setBasic(Valve valve) {
        pipeline.setBasic(valve);
    }

    @Override
    public synchronized void addValve(Valve valve) {
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

    @Override
    public Servlet allocate() throws ServletException {
        // Load and initialize our instance if necessary
        if (servlet ==null) {
            try {
                servlet = loadServlet();
            }
            catch (ServletException e) {
                throw e;
            }
            catch (Throwable e) {
                throw new ServletException("Cannot allocate a servlet instance", e);
            }
        }
        return servlet;
    }

    public Loader getLoader() {
        if(loader != null) {
            return loader;
        }
//        if (parent != null)
//            return (parent.getLoader());
        return (null);
    }

    public Servlet loadServlet() throws ServletException {
        Class clazz = null;
        try {
            loader.loadClass(servletClass);
        } catch(ClassNotFoundException e) {
            throw new ServletException(e);
        }
        // Instantiate and initialize an instance of the servlet class itself
        try {
            servlet = (Servlet) clazz.newInstance();
        } catch (Throwable e) {
            throw new ServletException("Failed to instantiate servlet");
        }

        // Call the initialization method of this servlet
        try {
            servlet.init(null);
        } catch (Throwable f) {
            throw new ServletException("Failed initialize servlet.");
        }
        return servlet;

    }

    @Override
    public void load() throws ServletException {
        servlet = loadServlet();
    }

    @Override
    public void setServletClass(String modernServlet) {
        this.servletClass = modernServlet;
    }

    @Override
    public void setLoader(Loader loader) {
        this.loader = loader;
    }
}
