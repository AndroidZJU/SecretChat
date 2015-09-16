package net;

import com.fengnanyue.secretchat.Configure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fernando on 15/9/15.
 */
public class Timeline {
    public Timeline(String phone_md5,String token, final int page, int perpage,final SuccessCallBack successCallBack,final FailCallBack failCallBack){

        new NetConnection(Configure.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);

                    switch (obj.getInt(Configure.KEY_STATUS)){
                        case Configure.RESULT_STATUS_SUCCESS:
                            if(successCallBack!=null){
                                successCallBack.onSuccess(obj.getInt(Configure.KEY_PAGE),obj.getInt(Configure.KEY_PERPAGE),obj.getJSONArray(Configure.KEY_TIMELINE));
                            }
                            break;
                        default:
                            if(failCallBack!=null){
                                failCallBack.onFail();
                            }
                            break;
                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                    if(failCallBack!=null){
                        failCallBack.onFail();
                    }
                }
            }
        }, new NetConnection.FailCallBack() {
            @Override
            public void onFail() {
                if(failCallBack!=null){
                    failCallBack.onFail();
                }
            }
        },Configure.KEY_ACTION,Configure.ACTION_TIMELINE,
                Configure.KEY_PHONE_MD5,phone_md5,Configure.KEY_TOKEN,token,
                Configure.KEY_PAGE,page+"",Configure.KEY_PERPAGE,perpage+"");
    }
    public static interface SuccessCallBack{
        void onSuccess(int page, int perpage,JSONArray timeline);
    }

    public static interface FailCallBack{
        void onFail();
    }
}
