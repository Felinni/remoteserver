package se.ik2002.remotecontrol;

import java.io.InputStream;

import javax.net.ssl.SSLSocket;

/**
 * Created by lorenzocorneo on 10/03/15.
 */
public class ParamsList {
    private SSLSocket socket;
    protected String message;
    private InputStream keyIn;
    private final String TAG = "TAG";
    private char keystorepass[];
    private char keypassword[];

    public ParamsList(SSLSocket socket, String message, InputStream keyIn, char[] keystorepass, char[] keypassword) {
        this.socket = socket;
        this.message = message;
        this.keyIn = keyIn;
        this.keystorepass = keystorepass;
        this.keypassword = keypassword;
    }

    public SSLSocket getSocket() {
        return socket;
    }

    public String getMessage() {
        return message;
    }

    public char[] getKeystorepass() {
        return keystorepass;
    }

    public char[] getKeypassword() {
        return keypassword;
    }

    public InputStream getKeyIn() {
        return keyIn;
    }
}
