package atys;

import android.app.ListActivity;
import android.os.Bundle;

import com.fengnanyue.secretchat.R;

/**
 * Created by Fernando on 15/9/13.
 */
public class AtyTimeline extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_timeline);
    }
}
