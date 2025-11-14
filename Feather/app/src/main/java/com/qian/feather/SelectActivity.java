package com.qian.feather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qian.feather.Adapter.DeviceRecyclerViewAdapter;
import com.qian.feather.Adapter.FileDisplayRecyclerViewAdapter;
import com.qian.feather.Item.Device;
import com.qian.feather.server.Reactor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SelectActivity extends AppCompatActivity {
    private Context context;
    private RadioGroup radioGroup_selectMode;
    private TextView textview_hostIP;
    private TextView textview_serverIP;
    private Intent intent;
    private Button button_nextStep;
    private int selectId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        context = this;

        textview_hostIP = findViewById(R.id.TextView_hostIP);
        textview_serverIP = findViewById(R.id.TextView_serverIP);

        radioGroup_selectMode = findViewById(R.id.RadioGroup_selectMode);
        radioGroup_selectMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if(id == R.id.Button_launchAsServer) {
                    selectId = 1;
                    intent = new Intent(SelectActivity.this, FileChooserActivity.class);
                } else if(id == R.id.Button_launchAsClient) {
                    selectId = 2;
                    intent = new Intent(SelectActivity.this, FileDisplayActivity.class);
                } else if(id == R.id.Button_launchAsApp) {
                    selectId = 3;
                    intent = new Intent(SelectActivity.this,MainActivity.class);
                }
            }
        });

        button_nextStep = findViewById(R.id.Button_nextStep);
        button_nextStep.setOnClickListener(view -> {
            if(selectId == 2) {
                intent.putExtra("serverIP",textview_serverIP.getText().toString());
            }
            startActivity(intent);
        });
    }


    private static List<Device> scanLocalNetwork(int timeout) {
        List<Device> onlineDevices = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(20);
        String baseIP = "192.168.1.";
        int start = 1,end = 254;

        for (int i = start; i <= end; i++) {
            String ip = baseIP + i;
            executor.execute(() -> {
                try {
                    InetAddress address = InetAddress.getByName(ip);
                    if (address.isReachable(timeout)) {
                        synchronized (onlineDevices) {
                            onlineDevices.add(new Device(address.getHostName(),ip));
                        }
                    }
                } catch (UnknownHostException e) {
                    // IP地址无效，忽略
                } catch (IOException e) {
                    // 无法连接，忽略
                }
            });
        }

        executor.shutdown();
        try {
            // 等待所有任务完成或超时
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return onlineDevices;
    }
}