package com.qian.feather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import com.qian.feather.Adapter.FileDisplayRecyclerViewAdapter;
import com.qian.feather.server.Reactor;

public class FileDisplayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FileDisplayRecyclerViewAdapter fileDisplayRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_display);

        //recyclerView = findViewById(R.id.RecyclerView_displayFiles);
        //fileDisplayRecyclerViewAdapter = new FileDisplayRecyclerViewAdapter(context,new ArrayList<>());
        //recyclerView.setAdapter(fileDisplayRecyclerViewAdapter);
    }
}