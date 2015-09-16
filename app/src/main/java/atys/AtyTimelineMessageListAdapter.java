package atys;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando on 15/9/15.
 */
public class AtyTimelineMessageListAdapter extends BaseAdapter {

    public AtyTimelineMessageListAdapter(Context context){
        this.context=context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Message getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        return null;
    }

    public Context getContext() {
        return context;
    }

    private List<Message> data = new ArrayList<Message>();
    private Context context = null;

}
