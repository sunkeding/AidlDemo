package sunkeding.com.aidlstudy.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import sunkeding.com.aidlstudy.ISimpleAidlInterface;
import sunkeding.com.aidlstudy.bean.StudentBean;

/**
 * @author: skd
 * @date 2018/10/7
 * @Desc IRemoteService
 */
public class IRemoteService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new ISimpleAidlInterface.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.d("IRemoteService", "num1:" + num1 + "    num2:" + num2);
            return num1 + num2;
        }

        @Override
        public int addAge(StudentBean bean) throws RemoteException {
            return 0;
        }
    };
}
