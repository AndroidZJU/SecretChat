package atys;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.fengnanyue.secretchat.Configure;
import com.fengnanyue.secretchat.R;
import com.fengnanyue.secretchat.tools.MD5Tool;

import net.Timeline;
import net.UploadContacts;

import org.json.JSONArray;

import ld.MyContacts;

/**
 * Created by Fernando on 15/9/13.
 */
public class AtyTimeline extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);

        phone_num = getIntent().getStringExtra(Configure.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Configure.KEY_TOKEN);
        phone_md5 = MD5Tool.md5(phone_num);

        final ProgressDialog pd = ProgressDialog.show(this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_server));
        new UploadContacts(phone_md5, token, MyContacts.getContactsJSONString(this), new UploadContacts.SuccessCallBack() {
            @Override
            public void onSuccess() {
                loadMessage();

                pd.dismiss();
            }
        }, new UploadContacts.FailCallBack() {
            @Override
            public void onFail(int errorCode) {
                pd.dismiss();
                if(errorCode==Configure.RESULT_STATUS_INVALID_TOKEN){
                    startActivity(new Intent(AtyTimeline.this,AtyLogin.class));
                    finish();
                }else{
                    loadMessage();
                }
            }
        });
    }
    private void loadMessage(){

        final ProgressDialog pd = ProgressDialog.show(this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_server));

        new Timeline(phone_md5, token, 1, 20, new Timeline.SuccessCallBack() {
            @Override
            public void onSuccess(int page, int perpage, JSONArray timeline) {
                pd.dismiss();


            }
        }, new Timeline.FailCallBack() {
            @Override
            public void onFail() {
                pd.dismiss();
                Toast.makeText(AtyTimeline.this,getString(R.string.fail_to_load_timeline_data),Toast.LENGTH_LONG).show();
            }
        });
    }
    private String phone_num,token,phone_md5;

}
