package atys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fengnanyue.secretchat.R;

import net.GetCode;

/**
 * Created by Fernando on 15/9/13.
 */
public class AtyLogin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
        etPhone = (EditText) findViewById(R.id.etPhoneNum);
        findViewById(R.id.btnGetCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etPhone.getText())){
                    Toast.makeText(AtyLogin.this, R.string.phone_num_can_not_empty,Toast.LENGTH_LONG).show();
                    return;

                }

                final ProgressDialog pd = ProgressDialog.show(AtyLogin.this,getResources().getString(R.string.connecting),getResources().getString(R.string.connecting_to_server));
                new GetCode(etPhone.getText().toString(), new GetCode.SuccessCallBack() {
                    @Override
                    public void onSuccess() {
                        pd.dismiss();
                        Toast.makeText(AtyLogin.this, R.string.suc_to_get_code,Toast.LENGTH_LONG).show();

                    }
                }, new GetCode.FailCallBack() {
                    @Override
                    public void onFail() {
                        pd.dismiss();
                        Toast.makeText(AtyLogin.this, R.string.fail_to_get_code,Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }
    private EditText etPhone = null;
}
