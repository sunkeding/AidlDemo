package sunkeding.com.aidlclient;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import sunkeding.com.aidlstudy.ISimpleAidlInterface;
import sunkeding.com.aidlstudy.bean.StudentBean;
import sunkeding.com.broadcast.BroadcastManager;

public class MainActivity extends AppCompatActivity {
    private String BROADCAST_ACTION = "skd";
    //
    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d("MainActivity", "远程服务端挂了。。");
            if (iSimpleAidlInterface == null) {
                return;
            }
            iSimpleAidlInterface.asBinder().unlinkToDeath(deathRecipient,0);
            iSimpleAidlInterface=null;
            // TODO: 2019-06-03  重新绑定远程服务
        }
    };
    private ISimpleAidlInterface iSimpleAidlInterface;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iSimpleAidlInterface = ISimpleAidlInterface.Stub.asInterface(iBinder);
            try {
                iBinder.linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iSimpleAidlInterface = null;
        }
    };
    private Button btnLoginState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLoginState = findViewById(R.id.btn_login_state);
        bindService();
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d("MainActivity", "iSimpleAidlInterface.asBinder().isBinderAlive():" + iSimpleAidlInterface.asBinder().isBinderAlive());
                    int add = iSimpleAidlInterface.add(6, 3);
                    Log.d("MainActivity", "add:" + add);
                    int i = iSimpleAidlInterface.addAge(new StudentBean());
                    Log.d("MainActivity", "i:" + i);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });


        BroadcastManager.getInstance(this).addAction(BROADCAST_ACTION, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    boolean isLogin = extras.getBoolean("isLogin");
                    Log.d("MainActivity", "isLogin:" + isLogin);
                    btnLoginState.setText("登录：" + isLogin);
                }
            }
        });
    }

    private void bindService() {
        Intent intent = new Intent();
        //绑定服务端App的包名和服务的完整类名（即服务的包名加类名）
        intent.setComponent(new ComponentName("sunkeding.com.aidlstudy", "sunkeding.com.aidlstudy.service.IRemoteService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BroadcastManager.getInstance(this).destroy(BROADCAST_ACTION);
    }
}
