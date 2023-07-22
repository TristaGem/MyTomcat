package org.trista.connector.http;

/** this class copies methods from org.trista.connector.HttpRequestBase
 *  and org.trista.connector.http.HttpRequestImpl.
 *  The HttpRequestImpl class employs a pool of HttpHeader objects for performance
 *  These two classes will be explained in Chapter 4.
 */

import org.trista.connector.RequestStream;
import org.trista.util.Enumerator;
import org.trista.util.ParameterMap;
import org.trista.util.RequestUtil;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public interface HttpRequest extends HttpServletRequest {

    InputStream getStream();

    void setRequestedSessionId(String value);

    void setRequestedSessionCookie(boolean b);

    void setRequestedSessionURL(boolean b);

    void addCookie(Cookie cookie);

    void addHeader(String name, String value);

    void setContentLength(int n);

    void setContentType(String value);

    void setQueryString(String s);

    void setMethod(String method);

    void setProtocol(String protocol);

    void setRequestURI(String normalizedUri);

    String getDecodedRequestURI();
}
