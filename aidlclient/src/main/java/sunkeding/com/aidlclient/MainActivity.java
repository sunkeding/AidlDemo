package sunkeding.com.aidlclient;

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

import sunkeding.com.aidlstudy.ISimpleAidlInterface;
import sunkeding.com.aidlstudy.bean.StudentBean;

public class MainActivity extends AppCompatActivity {

    private ISimpleAidlInterface iSimpleAidlInterface;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iSimpleAidlInterface = ISimpleAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            iSimpleAidlInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int add = iSimpleAidlInterface.add(6, 3);
                    Log.d("MainActivity", "add:" + add);
                    int i = iSimpleAidlInterface.addAge(new StudentBean());
                    Log.d("MainActivity", "i:" + i);
                } catch (RemoteException e) {
                    e.printStackTrace();
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
}
