package atys;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.fengnanyue.secretchat.Configure;
import com.fengnanyue.secretchat.R;
import com.fengnanyue.secretchat.tools.MD5Tool;

import net.UploadContacts;

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

        new UploadContacts(phone_md5, token, MyContacts.getContactsJSONString(this), new UploadContacts.SuccessCallBack() {
            @Override
            public void onSuccess() {
                loadMessage();
            }
        }, new UploadContacts.FailCallBack() {
            @Override
            public void onFail(int errorCode) {
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
        System.out.println(">>>>>>>>>>>>>>>>>>>>LoadMessage");
    }
    private String phone_num,token,phone_md5;
}
