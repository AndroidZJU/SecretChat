package atys;

import android.app.ListActivity;
import android.os.Bundle;

import com.fengnanyue.secretchat.R;

/**
 * Created by Fernando on 15/9/14.
 */
public class AtyMessage extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_message);
    }
}
