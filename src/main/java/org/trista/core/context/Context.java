package org.trista.core.context;

import org.trista.Request;
import org.trista.container.Container;
import org.trista.core.wrapper.Wrapper;
import org.trista.core.wrapper.mapper.Mapper;

public interface Context extends Container {

    public void addWrapper(Wrapper wrapper);

    public void addMapper(Mapper mapper);

    public Wrapper createWrapper();

    public void addServletMapping(String path, String servletName);

    String findServletMapping(String relativeURI);

    /**
     * Return the child Container that should be used to process this Request,
     * based upon its characteristics.  If no such child Container can be
     * identified, return <code>null</code> instead.
     *
     * @param request Request being processed
     * @param update Update the Request to reflect the mapping selection?
     */
    public Container map(Request request, boolean update);

}
