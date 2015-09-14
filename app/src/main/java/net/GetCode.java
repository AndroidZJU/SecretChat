package net;

import com.fengnanyue.secretchat.Configure;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fernando on 15/9/14.
 */
public class GetCode {
    public GetCode(String phone,final SuccessCallBack successCallBack,final FailCallBack failCallBack){
        new NetConnection(Configure.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObj = new JSONObject(result);
                    switch (jsonObj.getInt(Configure.KEY_STATUS)){
                        case Configure.RESULT_STATUS_SUCCESS:
                            if(successCallBack!=null){
                                successCallBack.onSuccess();
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
        },Configure.KEY_ACTION,Configure.ACTION_GET_CODE,Configure.KEY_PHONE_NUM,phone);

    }

    public static interface SuccessCallBack{
        void onSuccess();
    }
    public static interface FailCallBack{
        void onFail();
    }
}
