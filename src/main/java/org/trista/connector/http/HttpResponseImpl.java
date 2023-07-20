package org.trista.connector.http;

import org.trista.connector.http.HttpResponseBase;


/**
 * Implementation of <b>HttpResponse</b> specific to the HTTP connector.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.4 $ $Date: 2002/03/18 07:15:40 $
 */

final class HttpResponseImpl
        extends HttpResponseBase {


    // ----------------------------------------------------- Instance Variables


    /**
     * Descriptive information about this Response implementation.
     */
    protected static final String info =
            "org.apache.catalina.connector.http10.HttpResponseImpl/1.0";

    protected Boolean allowChunking = false;


    // ------------------------------------------------------------- Properties


    /**
     * Return descriptive information about this Response implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo() {

        return (info);

    }


    // ------------------------------------------------------ Protected Methods

    /**
     * Return the HTTP protocol version implemented by this response
     * object.
     *
     * @return The &quot;HTTP/1.0&quot; string.
     */
    protected String getProtocol() {
        return("HTTP/1.0");
    }


    // --------------------------------------------------------- Public Methods

    /**
     * Release all object references, and initialize instance variables, in
     * preparation for reuse of this object.
     */
    public void recycle() {

        super.recycle();

    }

    /**
     * Set the chunking flag.
     */
    void setAllowChunking(boolean allowChunking) {
        this.allowChunking = allowChunking;
    }


}