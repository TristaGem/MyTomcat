package org.trista.connector.http;

import org.trista.Response;

public interface HttpResponse extends Response {

    void setHeader(String server, String pyrmont_servlet_container);

}
