package org.trista.core;

import java.awt.event.ContainerListener;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;
import java.io.File;
import java.io.IOException;
import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.trista.logger.Logger;
import org.trista.Request;
import org.trista.Response;
import org.trista.container.Container;

public class SimpleContainer implements Container {

  private Logger logger;
  public static final String WEB_ROOT =
    System.getProperty("user.dir") + File.separator  + "webroot";

  public SimpleContainer() {
  }

  public String getInfo() {
    return null;
  }


  public Logger getLogger() {
    return this.logger;
  }

  @Override
  public void setLogger(Logger logger) {
      this.logger = logger;
  }


  public String getName() {
    return null;
  }

  public void setName(String name) {
  }

  public Container getParent() {
    return null;
  }

  public void setParent(Container container) {
  }

  public ClassLoader getParentClassLoader() {
    return null;
  }

  public void setParentClassLoader(ClassLoader parent) {
  }


  public DirContext getResources() {
    return null;
  }

  public void setResources(DirContext resources) {
  }

  public void addChild(Container child) {
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
  }

  public Container findChild(String name) {
    return null;
  }

  public Container[] findChildren() {
    return null;
  }

  @Override
  public void invoke(Request request, Response response) throws IOException, ServletException {
    String servletName = ( (HttpServletRequest) request).getRequestURI();
    servletName = servletName.substring(servletName.lastIndexOf("/") + 1);
    URLClassLoader loader = null;
    try {
      URL[] urls = new URL[1];
      URLStreamHandler streamHandler = null;
      File classPath = new File(WEB_ROOT);
      String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
      urls[0] = new URL(null, repository, streamHandler);
      loader = new URLClassLoader(urls);
    }
    catch (IOException e) {
      System.out.println(e.toString() );
    }
    Class myClass = null;
    try {
      myClass = loader.loadClass(servletName);
    }
    catch (ClassNotFoundException e) {
      System.out.println(e.toString());
    }

    Servlet servlet = null;

    try {
      servlet = (Servlet) myClass.newInstance();
      servlet.service((HttpServletRequest) request, (HttpServletResponse) response);
    }
    catch (Exception e) {
      System.out.println(e.toString());
    }
    catch (Throwable e) {
      System.out.println(e.toString());
    }

  }

  public Container map(Request request, boolean update) {
    return null;
  }

  public void removeChild(Container child) {
  }

  public void removeContainerListener(ContainerListener listener) {
  }

  public void removePropertyChangeListener(PropertyChangeListener listener) {
  }

}