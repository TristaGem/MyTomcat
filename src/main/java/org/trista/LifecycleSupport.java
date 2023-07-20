package org.trista;

public final class LifecycleSupport {

    // ----------------------------------------------------- Instance Variables


    /**
     * The source component for lifecycle events that we will fire.
     */
    private Lifecycle lifecycle = null;


    /**
     * The set of registered LifecycleListeners for event notifications.
     */
    private LifecycleListener[] listeners = new LifecycleListener[0];


    // ----------------------------------------------------- Constructors
    public LifecycleSupport(Lifecycle lifecycle) {
        super();
        this.lifecycle = lifecycle;
    }

    // ----------------------------------------------------- Public Methods
    /**
     * Add a lifecycle event listener to this component
     */

    public void addLifecycleListener(LifecycleListener listener) {
        synchronized (listeners) {
            LifecycleListener[] results = new LifecycleListener[listeners.length+1];

            for(int i = 0; i < listeners.length; i++) {
                LifecycleListener lifecycleListener = listeners[i];
                results[i] = lifecycleListener;
            }
            results[listeners.length] = listener;
            listeners = results;
        }
    }

    public LifecycleListener[] findLifecycleListeners() {
        return listeners;
    }

    public void fireLifecycleEvent(String type, Object data) {

        LifecycleEvent event = new LifecycleEvent(lifecycle, type, data);
        for(LifecycleListener listener: listeners) {
            listener.lifecycleEvent(event);
        }
    }


    public void removeLifecycleListener(LifecycleListener listener) {
        synchronized (listeners) {
            LifecycleListener[] results = new LifecycleListener[listeners.length - 1];
            for(int i = 0, j = 0; i < listeners.length; i++, j++) {
                LifecycleListener lifecycleListener = listeners[i];
                if(lifecycleListener == listener) {
                    j -= 1;
                    continue;
                }
                results[i] = lifecycleListener;
            }
        }

    }
}
