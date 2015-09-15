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

//        System.out.println(MyContacts.getContactsJSONString(this));

        String token = Configure.getCachedToken(this);
        String phone_num = Configure.getCachedPhoneNumber(this);

        if(token!=null&&phone_num!=null){
            Intent i = new Intent(this, AtyTimeline.class);
            i.putExtra(Configure.KEY_TOKEN,token);
            i.putExtra(Configure.KEY_PHONE_NUM,phone_num);
            startActivity(i);
        }else{
            startActivity(new Intent(this, AtyLogin.class));
        }

        finish();
    }


}
