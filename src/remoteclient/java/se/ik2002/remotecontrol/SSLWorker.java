package se.ik2002.remotecontrol;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;

/**
 * Created by lorenzocorneo on 10/03/15.
 */
public class SSLWorker extends AsyncTask<ParamsList, Void, String> {
    private SSLSocket socket = null;
    private BufferedWriter out = null;
    private BufferedReader in = null;
    private final String STOP_MESSAGE = "#Stop#";

    public SSLWorker(SSLSocket socket) {
        this.socket = socket;
    }



    @Override
    protected String doInBackground(ParamsList... params) {
        for(int i = 0; i < params.length; i++) {
            try{
                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                return executeRemoteCommand(params[i].getMessage());
            } catch (UnknownHostException e) {
                return e.toString();
            } catch  (IOException e) {
                return e.toString();
            }
        }

        return null;
    }

    public String executeRemoteCommand(String command){
        String line = "";
        String result = "";

        try {
            out.write(command + "\n");
            out.flush();
        } catch (IOException e) {
            System.exit(1);
        }

        try {
            while(true) {
                line = in.readLine();
                Log.i("ASD", line);
                if(line.equals(STOP_MESSAGE)) {
                    break;
                }

                result += line + "\n";
            }
        } catch (IOException e) {
            System.exit(1);
        }
        Log.i("ASD", "RESULT = " + result);
        return result;
    }
}
