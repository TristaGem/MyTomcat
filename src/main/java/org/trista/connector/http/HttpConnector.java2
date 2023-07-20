package org.trista.connector.http;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConnector implements Runnable {

    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

    // the shutdown command received
    private boolean shutdown = false;

    public HttpConnector() {

    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        ServerSocket serverSocket = null;
        int port = 8080;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Loop waiting for a request
        while (!shutdown) {
            Socket socket;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            // Hand this socket off to HttpProcessor
            HttpProcessor httpProcessor = new HttpProcessor(this);
            httpProcessor.process(socket);

        }
    }
}
