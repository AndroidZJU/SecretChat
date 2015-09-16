package atys;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fengnanyue.secretchat.Configure;
import com.fengnanyue.secretchat.R;
import com.fengnanyue.secretchat.tools.MD5Tool;

import net.Comment;
import net.GetComment;
import net.PubComment;
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
        etComment = (EditText) findViewById(R.id.etComment);
        Intent data = getIntent();
        token = data.getStringExtra(Configure.KEY_TOKEN);
        phone_md5 = data.getStringExtra(Configure.KEY_PHONE_MD5);
        msg = data.getStringExtra(Configure.KEY_MSG);
        msgId = data.getStringExtra(Configure.KEY_MSG_ID);

        tvMessage.setText(msg);

        getComments();
        findViewById(R.id.btnSengComment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etComment.getText())){
                    Toast.makeText(AtyMessage.this,getString(R.string.comment_content_can_not_be_empty),Toast.LENGTH_LONG).show();
                    return;
                }

                final ProgressDialog pd = ProgressDialog.show(AtyMessage.this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_server));
                new PubComment(MD5Tool.md5(Configure.getCachedPhoneNumber(AtyMessage.this)), token, etComment.getText().toString(), msgId, new PubComment.SuccessCallback() {
                    @Override
                    public void onSuccess() {
                        pd.dismiss();
                        etComment.setText("");
                        getComments();

                    }
                }, new PubComment.FailCallback() {
                    @Override
                    public void onFail(int errorCode) {
                        pd.dismiss();
                        if(errorCode==Configure.RESULT_STATUS_INVALID_TOKEN){
                            startActivity(new Intent(AtyMessage.this,AtyLogin.class));
                            finish();
                        }else{
                            Toast.makeText(AtyMessage.this,getString(R.string.fail_to_pub_comment),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

    private void getComments() {
        final ProgressDialog pd = ProgressDialog.show(this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_server));
        new GetComment(phone_md5, token, msgId, 1, 20, new GetComment.SuccessCallBack() {
            @Override
            public void onSuccess(String msgId, int page, int perpage, List<Comment> comments) {
                pd.dismiss();
                adapter.clear();
                adapter.addAll(comments);

            }
        }, new Timeline.FailCallBack() {
            @Override
            public void onFail(int errorCode) {
                pd.dismiss();
                if(errorCode== Configure.RESULT_STATUS_INVALID_TOKEN){
                    startActivity(new Intent(AtyMessage.this, AtyLogin.class));
                    finish();
                }else{
                    Toast.makeText(AtyMessage.this, getString(R.string.fail_to_get_comment), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private EditText etComment;
    private TextView tvMessage;
    private String phone_md5,msg,msgId,token;
    private AtyMessageCommentListAdapter adapter;
}
