package org.trista.connector.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.UnrecoverableKeyException;
import java.security.KeyManagementException;


/**
 * Interface that describes the common characteristics of factory classes
 * that create server sockets which may be required by a Connector.  A concrete
 * implementation of this interface will be assigned to a Connector
 * via the <code>setFactory()</code> method.
 *
 * @author db@eng.sun.com
 * @author Harish Prabandham
 * @author Craig R. McClanahan
 */
public interface ServerSocketFactory {


    // --------------------------------------------------------- Public Methods


    /**
     * Returns a server socket which uses all network interfaces on
     * the host, and is bound to a the specified port.  The socket is
     * configured with the socket options (such as accept timeout)
     * given to this factory.
     *
     * @param port the port to listen to
     *
     * @exception java.io.IOException                input/output or network error
     * @exception java.security.KeyStoreException          error instantiating the
     *                                       KeyStore from file (SSL only)
     * @exception java.security.NoSuchAlgorithmException   KeyStore algorithm unsupported
     *                                       by current provider (SSL only)
     * @exception java.security.cert.CertificateException       general certificate error (SSL only)
     * @exception java.security.UnrecoverableKeyException  internal KeyStore problem with
     *                                       the certificate (SSL only)
     * @exception java.security.KeyManagementException     problem in the key management
     *                                       layer (SSL only)
     */
    public ServerSocket createSocket(int port)
            throws IOException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException,
            KeyManagementException;


    /**
     * Returns a server socket which uses all network interfaces on
     * the host, is bound to a the specified port, and uses the
     * specified connection backlog.  The socket is configured with
     * the socket options (such as accept timeout) given to this factory.
     *
     * @param port the port to listen to
     * @param backlog how many connections are queued
     *
     * @exception java.io.IOException                input/output or network error
     * @exception java.security.KeyStoreException          error instantiating the
     *                                       KeyStore from file (SSL only)
     * @exception java.security.NoSuchAlgorithmException   KeyStore algorithm unsupported
     *                                       by current provider (SSL only)
     * @exception java.security.cert.CertificateException       general certificate error (SSL only)
     * @exception java.security.UnrecoverableKeyException  internal KeyStore problem with
     *                                       the certificate (SSL only)
     * @exception java.security.KeyManagementException     problem in the key management
     *                                       layer (SSL only)
     */
    public ServerSocket createSocket(int port, int backlog)
            throws IOException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException,
            KeyManagementException;


    /**
     * Returns a server socket which uses only the specified network
     * interface on the local host, is bound to a the specified port,
     * and uses the specified connection backlog.  The socket is configured
     * with the socket options (such as accept timeout) given to this factory.
     *
     * @param port the port to listen to
     * @param backlog how many connections are queued
     * @param ifAddress the network interface address to use
     *
     * @exception java.io.IOException                input/output or network error
     * @exception java.security.KeyStoreException          error instantiating the
     *                                       KeyStore from file (SSL only)
     * @exception java.security.NoSuchAlgorithmException   KeyStore algorithm unsupported
     *                                       by current provider (SSL only)
     * @exception java.security.cert.CertificateException       general certificate error (SSL only)
     * @exception java.security.UnrecoverableKeyException  internal KeyStore problem with
     *                                       the certificate (SSL only)
     * @exception java.security.KeyManagementException     problem in the key management
     *                                       layer (SSL only)
     */
    public ServerSocket createSocket(int port, int backlog,
                                     InetAddress ifAddress)
            throws IOException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException,
            KeyManagementException;


}
