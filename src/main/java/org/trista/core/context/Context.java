package org.trista.core.context;

import org.trista.core.wrapper.Wrapper;

public interface Context {

    public void addWrapper(Wrapper wrapper);

    public Wrapper createWrapper();
}
