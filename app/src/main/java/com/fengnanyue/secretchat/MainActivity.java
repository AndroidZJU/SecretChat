package com.fengnanyue.secretchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import atys.AtyLogin;
import atys.AtyTimeline;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String token = Configure.getCachedToken(this);
        if(token!=null){
            Intent i = new Intent(this, AtyTimeline.class);
            i.putExtra(Configure.KEY_TOKEN,token);
            startActivity(i);
        }else{
            startActivity(new Intent(this, AtyLogin.class));
        }
    }


}
