package org.trista;

import org.trista.connector.http.HttpConnector;
import org.trista.core.SimpleContainer;



public final class Bootstrap {
    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        SimpleContainer simpleContainer = new SimpleContainer();
        connector.setContainer(simpleContainer);

        try{

            // create server socket through initialize
            connector.initialize();
            connector.start();

            // make the application wait until we press any key.
            System.in.read();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
