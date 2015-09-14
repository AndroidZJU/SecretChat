package com.fengnanyue.secretchat;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**
 * Created by Fernando on 15/9/13.
 */
public class Configure {

    public static final String KEY_TOKEN = "token";
    public static final String APP_ID = "com.fengnanyue.secret";
    public static String getCachedToken(Context context){
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_TOKEN,null);
    }

    public static void cacheToken(Context context,String token){
        Editor e = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        e.putString(KEY_TOKEN,token);
        e.commit();
    }
}
