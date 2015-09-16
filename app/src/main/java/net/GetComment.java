package net;

import com.fengnanyue.secretchat.Configure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando on 15/9/16.
 */
public class GetComment {
    public GetComment(final String phone_md5,String token,String msgId,int page, int perpage,final SuccessCallBack successCallBack,final Timeline.FailCallBack failCallBack){

        new NetConnection(Configure.SERVER_URL, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    switch(jsonObject.getInt(Configure.KEY_STATUS)){
                        case Configure.RESULT_STATUS_SUCCESS:

                            if(successCallBack!=null){

                                List<Comment> comments = new ArrayList<Comment>();
                                JSONArray commentsJsonArray = jsonObject.getJSONArray(Configure.KEY_COMMENTS);
                                JSONObject commentObj;
                                for(int i=0;i<commentsJsonArray.length();i++){
                                    commentObj = commentsJsonArray.getJSONObject(i);
                                    comments.add(new Comment(commentObj.getString(Configure.KEY_CONTENT),commentObj.getString(Configure.KEY_PHONE_MD5)));
                                }
                                successCallBack.onSuccess(jsonObject.getString(Configure.KEY_MSG_ID),jsonObject.getInt(Configure.KEY_PAGE),jsonObject.getInt(Configure.KEY_PERPAGE),comments);
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
        },Configure.KEY_ACTION,Configure.ACTION_GET_COMMENT,
                Configure.KEY_TOKEN,token,
                Configure.KEY_MSG_ID,msgId,
                Configure.KEY_PAGE,page+"",
                Configure.KEY_PERPAGE,perpage+"");
    }

    public static interface SuccessCallBack{
        void onSuccess(String msgId,int page, int perpage,List<Comment> comments);
    }
    public static interface FailCallBack{
        void onFail(int errorCode);
    }
}
