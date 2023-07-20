package org.trista.container;

/**
 * A valve class can optionally implement the Contained interface.
 * This interface specifies that the implementing class is associated with at most
 * one container instance.
 */
public interface Contained {
    public Container getContainer();
    public void setContainer(Container container);
}
