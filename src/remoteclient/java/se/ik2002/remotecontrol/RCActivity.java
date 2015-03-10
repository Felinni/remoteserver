package se.ik2002.remotecontrol;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.net.Socket;
import java.security.KeyStore;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLSocket;


public class RCActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rc);

        new Thread(new Runnable() {
            Button btnGetData, btnGetProcesses, btnPwd, btnQuit;
            TextView txtOutput;
            private char pass[] = "foobar".toCharArray();
            private SSLSocket socket = null;
            private BufferedWriter out = null;
            private BufferedReader in = null;
            private final String getDateCmd = "date";
            private final String getProcsCmd = "cmd procs";
            private final String getGetAbsPath = "cmd pwd";
            private final String quitCommand = "quit";

            @Override
            public void run() {
                Intent intent = getIntent();

                Log.i("LOG", intent.getStringExtra("ip"));

                try {
                    KeyStore ks = KeyStore.getInstance("BKS");
                    ks.load(getWindow().getDecorView().getResources().openRawResource(R.raw.foobarandroid), pass);
                    SSLSocketFactory socketFactory = new SSLSocketFactory(ks);
                    socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                    socket = (SSLSocket)
                            socketFactory.createSocket(new Socket(intent.getStringExtra("ip"), Integer.parseInt(intent.getStringExtra("port"))),
                                    intent.getStringExtra("ip"), Integer.parseInt(intent.getStringExtra("port")), false);
                    socket.startHandshake();

                } catch(Exception e) {
                    Log.i("ERR", e.toString());
                }

                txtOutput = (TextView) findViewById(R.id.output);

                btnGetData = (Button) findViewById(R.id.btnDate);
                btnGetData.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ParamsList paramsList = new ParamsList(socket, getDateCmd,
                                getWindow().getDecorView().getResources().openRawResource(R.raw.foobarandroid),
                                pass, pass);

                        try {
                            String result = new SSLWorker(socket).execute(paramsList).get();
                            txtOutput.setText(result);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });

                btnGetProcesses = (Button) findViewById(R.id.btnProcesses);
                btnGetProcesses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ParamsList paramsList = new ParamsList(socket, getProcsCmd,
                                getWindow().getDecorView().getResources().openRawResource(R.raw.foobarandroid),
                                pass, pass);

                        try {
                            String result = new SSLWorker(socket).execute(paramsList).get();
                            txtOutput.setText(result);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });

                btnPwd = (Button) findViewById(R.id.btnPwd);
                btnPwd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ParamsList paramsList = new ParamsList(socket, getGetAbsPath,
                                getWindow().getDecorView().getResources().openRawResource(R.raw.foobarandroid),
                                pass, pass);

                        try {
                            String result = new SSLWorker(socket).execute(paramsList).get();
                            txtOutput.setText(result);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });

                btnQuit = (Button) findViewById(R.id.btnQuit);
                btnQuit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ParamsList paramsList = new ParamsList(socket, getGetAbsPath,
                                getWindow().getDecorView().getResources().openRawResource(R.raw.foobarandroid),
                                pass, pass);

                        try {
                            String result = new SSLWorker(socket).execute(paramsList).get();
                            System.exit(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }
}
