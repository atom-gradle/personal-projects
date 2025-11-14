package com.qian.feather.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.qian.feather.Item.Msg;
import com.qian.feather.NioClient;
import com.qian.feather.PersonalInfoActivity;
import com.qian.feather.R;
import com.qian.feather.User;

import java.util.concurrent.CompletableFuture;

public class AddNewFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_friend);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        SearchView searchForUser = findViewById(R.id.SearchView_searchForUser);
        searchForUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                /*
                CompletableFuture<Msg> future = CompletableFuture.supplyAsync(() -> {
                    Msg msg = new Msg(User.currentUser.getName(),"SERVER","QUERY:SearchForUser:Account:"+query,Msg.SYS_INFO);
                    NioClient.getInstance().sendMsg(msg);
                });
                 */
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
}