package net;

import com.fengnanyue.secretchat.Configure;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fernando on 15/9/16.
 */
public class Publish {

    public Publish(String phone_md5,String token,String msg,final SuccessCallBack successCallBack,final FailCallBack failCallBack){
        new NetConnection(Configure.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Configure.KEY_STATUS)){
                        case Configure.RESULT_STATUS_SUCCESS:
                            if(successCallBack!=null){
                                successCallBack.onSuccess();
                            }
                            break;
                        case Configure.RESULT_STATUS_INVALID_TOKEN:
                            if(failCallBack!=null){
                                failCallBack.onFail(Configure.RESULT_STATUS_INVALID_TOKEN);
                            }
                            break;

                        default:
                            if(failCallBack!=null){
                                failCallBack.onFail(Configure.RESULT_STATUS_FAIL);
                            }
                            break;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                    if(failCallBack!=null){
                        failCallBack.onFail(Configure.RESULT_STATUS_FAIL);
                    }
                }
            }
        }, new NetConnection.FailCallBack() {
            @Override
            public void onFail() {
                if(failCallBack!=null){
                    failCallBack.onFail(Configure.RESULT_STATUS_FAIL);
                }
            }
        },Configure.KEY_ACTION,Configure.ACTION_PUBLISH,
                Configure.KEY_PHONE_MD5,phone_md5,
                Configure.KEY_TOKEN,token,
                Configure.KEY_MSG,msg);
    }

    public static interface SuccessCallBack{
        void onSuccess();
    }
    public static interface FailCallBack{
        void onFail(int errorCode);
    }
}
