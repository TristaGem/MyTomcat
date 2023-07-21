package org.trista.core.loader;

public interface Loader {
    public Class loadClass(String servletName) throws ClassNotFoundException;
}
