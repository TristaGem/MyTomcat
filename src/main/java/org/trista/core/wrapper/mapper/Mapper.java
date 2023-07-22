package org.trista.core.wrapper.mapper;

import org.trista.Request;
import org.trista.Response;
import org.trista.container.Container;


/**
 * Mapper interface is only used in Tomcat 4. Tomcat 5 used a different approach.
 * A container can use multiple mappers to support multiple protocols. In this case, one mapper supports one request protocol.
 * For example, a container may have a mapper for the HTTP protocol and another mapper for the HTTPS protocol.
 */
public interface Mapper {

    public Container getContainer();
    public void setContainer(Container container);
    public String getProtocol();
    public void setProtocol(String protocol);

    /**
     * map method returns a child container that will process a particular request.
     * @param request
     * @param update
     * @return
     */
    public Container map(Request request, boolean update);
}
