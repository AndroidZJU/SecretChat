package atys;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.fengnanyue.secretchat.Configure;
import com.fengnanyue.secretchat.R;

import net.Comment;
import net.GetComment;
import net.Timeline;

import java.util.List;

/**
 * Created by Fernando on 15/9/14.
 */
public class AtyMessage extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_message);

        adapter = new AtyMessageCommentListAdapter(this);
        setListAdapter(adapter);

        tvMessage = (TextView) findViewById(R.id.tvMessage);
        Intent data = getIntent();
        token = data.getStringExtra(Configure.KEY_TOKEN);
        phone_md5 = data.getStringExtra(Configure.KEY_PHONE_MD5);
        msg = data.getStringExtra(Configure.KEY_MSG);
        msgId = data.getStringExtra(Configure.KEY_MSG_ID);

        tvMessage.setText(msg);

        final ProgressDialog pd = ProgressDialog.show(this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_server));
        new GetComment(phone_md5, token, msgId, 1, 20, new GetComment.SuccessCallBack() {
            @Override
            public void onSuccess(String msgId, int page, int perpage, List<Comment> comments) {
                pd.dismiss();

                adapter.addAll(comments);

            }
        }, new Timeline.FailCallBack() {
            @Override
            public void onFail(int errorCode) {
                pd.dismiss();
                if(errorCode==Configure.RESULT_STATUS_INVALID_TOKEN){
                    startActivity(new Intent(AtyMessage.this,AtyLogin.class));
                    finish();
                }else{
                    Toast.makeText(AtyMessage.this,getString(R.string.fail_to_get_comment),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private TextView tvMessage;
    private String phone_md5,msg,msgId,token;
    private AtyMessageCommentListAdapter adapter;
}
