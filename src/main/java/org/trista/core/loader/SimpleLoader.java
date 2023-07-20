package org.trista.core.loader;

import org.trista.container.Container;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class SimpleLoader implements Loader{
    public static final String WEB_ROOT =
            System.getProperty("user.dir") + File.separator  + "webroot";
    public ClassLoader classLoader = null;
    public Container container = null;

    public SimpleLoader() {
        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(WEB_ROOT);
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString() ;
            urls[0] = new URL(null, repository, streamHandler);
            classLoader = new URLClassLoader(urls);
        }
        catch (IOException e) {
            System.out.println(e.toString() );
        }
    }

    public Class loadClass(String servletName) throws ClassNotFoundException {
        return classLoader.loadClass(servletName);

    }
}
