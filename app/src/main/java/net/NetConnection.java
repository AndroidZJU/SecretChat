package net;

import android.os.AsyncTask;

import com.fengnanyue.secretchat.Configure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Fernando on 15/9/13.
 */
public class NetConnection {
    public NetConnection(final String url,final HttpMethod method,final SuccessCallBack successCallBack, final FailCallBack failCallBack,final String ...kvs){

        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {

                StringBuffer paramsStr = new StringBuffer();
                for(int i =0;i<kvs.length;i+=2){//kv
                    paramsStr.append(kvs[i]).append("=").append(kvs[i+1]).append("&");
                }

                try {
                    URLConnection uc;

                    switch (method){
                        case POST:
                            uc = new URL(url).openConnection();
                            uc.setDoOutput(true);
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(uc.getOutputStream(), Configure.CHARSET));
                            bw.write(paramsStr.toString());
                            break;
                        default://GET
                            uc = new URL(url+"?"+paramsStr.toString()).openConnection();
                            break;
                    }
                    System.out.println("Request url:"+uc.getURL());
                    System.out.println("Request data:"+paramsStr);

                    BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(),Configure.CHARSET));
                    String line = null;
                    StringBuffer result = new StringBuffer();
                    while((line=br.readLine())!=null){
                        result.append(line);
                    }

                    System.out.println("Result:"+result);
                    return result.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if(s!=null){
                    if(successCallBack!=null){
                        successCallBack.onSuccess(s);
                    }
                }else{
                    if(failCallBack!=null){
                        failCallBack.onFail();
                    }
                }
                super.onPostExecute(s);
            }
        };

    }
    public static interface SuccessCallBack{
        void onSuccess(String result);
    }
    public static interface FailCallBack{
        void onFail();
    }
}
