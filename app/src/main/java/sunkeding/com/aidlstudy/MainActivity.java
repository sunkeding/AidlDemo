package sunkeding.com.aidlstudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import broadcast.BroadcastManager;
import sunkeding.com.aidlstudy.app.ServerApp;

public class MainActivity extends AppCompatActivity {
    private String BROADCAST_ACTION = "skd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerApp.isLogin = true;
                sendBroadcastToClientApp();
            }
        });
        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerApp.isLogin = false;
                sendBroadcastToClientApp();

            }
        });
    }

    private void sendBroadcastToClientApp() {

        Bundle bundle = new Bundle();
        bundle.putBoolean("isLogin", ServerApp.isLogin);
        BroadcastManager.getInstance(MainActivity.this).sendBroadcast(BROADCAST_ACTION, bundle);
    }
}
