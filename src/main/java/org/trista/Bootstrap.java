package org.trista;

import org.trista.connector.http.HttpConnector;

import java.io.IOException;



public final class Bootstrap {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }
}
