package se.ik2002.remotecontrol;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ConnectionActivity extends ActionBarActivity implements TextWatcher {
    private EditText txtIP, txtPort;
    private Button btnConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);

        txtIP = (EditText) findViewById(R.id.ip);
        txtPort = (EditText) findViewById(R.id.port);
        btnConnect = (Button) findViewById(R.id.btnConnect);

        txtIP.addTextChangedListener(this);
        txtPort.addTextChangedListener(this);

        btnConnect.setClickable(false);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RCActivity.class);
                intent.putExtra("ip", txtIP.getText().toString());
                intent.putExtra("port", txtPort.getText().toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(txtIP.getText().length() > 0 && txtPort.getText().length() > 0) {
            btnConnect.setClickable(true);
        } else {
            btnConnect.setClickable(false);
        }
    }
}
