package org.trista.core.wrapper;


import org.trista.container.Container;
import org.trista.core.loader.Loader;
import org.trista.pipeline.Pipeline;

/**
 * The Wrapper interface is a container representing an individual servlet
 * definition. The Wrapper interface extends Container and adds a number of
 * methods. Implementations of Wrapper are responsible for managing servlet
 * lifecycle for their underlying servlet class, like init, service nad destroy methods
 * of the servlet.
 * Lowest level of container, can't add child to it, will throw IlleagalArgumentException.
 */
public interface Wrapper extends Container {
    /**
     * allocates an initialized instance of the servlet the wrapper represents
     * must take into account whether or not the servlet implements the javax.servlet.SingleThreadModel
     * interface, but to be discussed in Chap 11.
     */

    public javax.servlet.Servlet allocate() throws javax.servlet.ServletException;

    public void load() throws javax.servlet.ServletException;

    void setServletClass(String modernServlet);

    void setLoader(Loader loader);
}

