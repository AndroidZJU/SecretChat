package net;

import com.fengnanyue.secretchat.Configure;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Fernando on 15/9/16.
 */
public class PubComment {

    public PubComment(String phone_md5,String token,String content,String msgId,final SuccessCallback successCallback,final FailCallback failCallback) {
        new NetConnection(Configure.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallBack() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getInt(Configure.KEY_STATUS)) {
                        case Configure.RESULT_STATUS_SUCCESS:
                            if (successCallback!=null) {
                                successCallback.onSuccess();
                            }
                            break;
                        case Configure.RESULT_STATUS_INVALID_TOKEN:
                            if (failCallback!=null) {
                                failCallback.onFail(Configure.RESULT_STATUS_INVALID_TOKEN);
                            }
                            break;
                        default:
                            if (failCallback!=null) {
                                failCallback.onFail(Configure.RESULT_STATUS_FAIL);
                            }
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    if (failCallback!=null) {
                        failCallback.onFail(Configure.RESULT_STATUS_FAIL);
                    }
                }
            }
        }, new NetConnection.FailCallBack() {

            @Override
            public void onFail() {
                if (failCallback!=null) {
                    failCallback.onFail(Configure.RESULT_STATUS_FAIL);
                }
            }
        }, Configure.KEY_ACTION,Configure.ACTION_PUB_COMMENT,
                Configure.KEY_TOKEN,token,
                Configure.KEY_PHONE_MD5,phone_md5,
                Configure.KEY_MSG_ID,msgId);
    }


    public static interface SuccessCallback{
        void onSuccess();
    }

    public static interface FailCallback{
        void onFail(int errorCode);
    }
}

