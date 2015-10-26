package com.qiniu.www.android_qiniu_tools;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.ProgressDialog;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class MainActivity extends AppCompatActivity {

    private Button btn;

    private EditText editText;

    private EditText resultView;

    private ProgressDialog progressDialog;

    private final ToolSrv srv = new ToolSrvImpl();


    public  final static  StringBuffer result = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        srv.addObserver(observer);

        //设置监听
        resultView = (EditText) findViewById(R.id.editText2);
        editText = (EditText) findViewById(R.id.editText);
        btn  =(Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
               //   progressDialog = ProgressDialog.show(MainActivity.this, "", "加载中，请稍后……");
               // srv.ip();//srv.ping(editText.getText().toString());
               // srv.traceroute(editText.getText().toString());
               // LDNetTraceRoute traceRoute=new LDNetTraceRoute();
               // traceRoute.startTraceRoute(editText.getText().toString());
                Intent intent = new Intent(MainActivity.this,
                        tracert.class);
                startActivity(intent);
                Log.i("ping", "开始执行ping方法的调用");
            }
        });


}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);


    }
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case 0:
                    resultView.setText(result.toString());
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    break;
                case 1:
                    result.delete(0, result.length());
                    break;
                default:
                    break;
            }
        }
    };
    private final Observer observer = new Observer() {
        @Override
        public void pingPageChange(String str, int status) {
            result.append(str).append("\r\n");
            MainActivity.this.handler.sendEmptyMessage(0);
            Log.i("ping", "添加显示");
        };

        @Override
        public void cleanScreen() {
            MainActivity.this.handler.sendEmptyMessage(1);
        }
    };




}
