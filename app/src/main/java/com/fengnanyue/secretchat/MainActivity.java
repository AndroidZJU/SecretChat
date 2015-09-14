package com.fengnanyue.secretchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import atys.AtyLogin;
import atys.AtyTimeline;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String token = Configure.getCachedToken(this);
//        startActivity(new Intent(this,AtyTimeline.class));
        if(token!=null){
            Intent i = new Intent(this, AtyTimeline.class);
            i.putExtra(Configure.KEY_TOKEN,token);
            startActivity(i);
        }else{
            startActivity(new Intent(this, AtyLogin.class));
        }

        finish();
    }


}
