package atys;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.fengnanyue.secretchat.Configure;
import com.fengnanyue.secretchat.R;

/**
 * Created by Fernando on 15/9/14.
 */
public class AtyMessage extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_message);

        tvMessage = (TextView) findViewById(R.id.tvMessage);
        Intent data = getIntent();
        phone_md5 = data.getStringExtra(Configure.KEY_PHONE_MD5);
        msg = data.getStringExtra(Configure.KEY_MSG);
        msgId = data.getStringExtra(Configure.KEY_MSG_ID);

        tvMessage.setText(msg);

    }

    private TextView tvMessage;
    private String phone_md5,msg,msgId;
}
