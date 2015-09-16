package atys;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.fengnanyue.secretchat.Configure;
import com.fengnanyue.secretchat.R;
import com.fengnanyue.secretchat.tools.MD5Tool;

import net.Message;
import net.Timeline;
import net.UploadContacts;

import java.util.List;

import ld.MyContacts;

/**
 * Created by Fernando on 15/9/13.
 */
public class AtyTimeline extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);


        adapter = new AtyTimelineMessageListAdapter(this);
        setListAdapter(adapter);

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
            public void onSuccess(int page, int perpage, List<Message> timeline) {
                pd.dismiss();

                adapter.addAll(timeline);
            }
        }, new Timeline.FailCallBack() {
            @Override
            public void onFail(int errorCode) {
                pd.dismiss();
                if(errorCode==Configure.RESULT_STATUS_INVALID_TOKEN){
                    startActivity(new Intent(AtyTimeline.this,AtyLogin.class));
                    finish();
                }else{
                    Toast.makeText(AtyTimeline.this,getString(R.string.fail_to_load_timeline_data),Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_aty_timeline,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuShowAtyPublish:
                startActivity(new Intent(AtyTimeline.this,AtyPublish.class));
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Message msg = adapter.getItem(position);
        Intent i = new Intent(this,AtyMessage.class);
        i.putExtra(Configure.KEY_MSG,msg.getMsg());
        i.putExtra(Configure.KEY_MSG_ID,msg.getMsgId());
        i.putExtra(Configure.KEY_PHONE_MD5,msg.getPhone_md5());
        i.putExtra(Configure.KEY_TOKEN,token);
        startActivity(i);
    }

    private String phone_num,token,phone_md5;
    private AtyTimelineMessageListAdapter adapter=null;
}
