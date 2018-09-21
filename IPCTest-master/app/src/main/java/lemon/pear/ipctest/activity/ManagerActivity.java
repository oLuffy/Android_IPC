package lemon.pear.ipctest.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import lemon.pear.ipctest.Config;
import lemon.pear.ipctest.IManager;
import lemon.pear.ipctest.R;
import lemon.pear.ipctest.entity.Book;
import lemon.pear.ipctest.service.ManagerService;

/**
 * AIDL测试
 */
public class ManagerActivity extends AppCompatActivity {
 TextView tx1;
 TextView tx2;
 TextView tx3;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IManager manager = IManager.Stub.asInterface(iBinder);
            try {
                List<Book> bookList = manager.getBookList();
                Log.i(Config.LOG_TAG, bookList.toString());

                manager.addBook(new Book(3, "化学"));
                bookList = manager.getBookList();
                Log.e(Config.LOG_TAG, ""+bookList.get(0).bookName);
                Log.e(Config.LOG_TAG, ""+bookList.size());
                tx1.setText(bookList.get(0).bookName);
                tx2.setText(bookList.get(1).bookName);
                tx3.setText(bookList.get(2).bookName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_manager);
        tx1 = findViewById(R.id.tx1);
        tx2 = findViewById(R.id.tx2);
        tx3 = findViewById(R.id.tx3);
        Intent intent = new Intent(this, ManagerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
