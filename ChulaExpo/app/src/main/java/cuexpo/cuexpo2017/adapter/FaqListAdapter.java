package cuexpo.cuexpo2017.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.view.FaqListItem;

/**
 * Created by dragonnight on 26/12/2559.
 */

public class FaqListAdapter extends BaseAdapter{

    private String[] question;
    private String[] answer;
    private Context context;

    public FaqListAdapter (Context ctx){
        this.context = ctx;
        question = context.getResources().getStringArray(R.array.question);
        answer = context.getResources().getStringArray(R.array.answer);
    }

    @Override
    public int getCount() {
        return question.length;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FaqListItem item;

        if(convertView!=null)
            item = (FaqListItem) convertView;
        else
            item = new FaqListItem(parent.getContext());

        item.setQuestion(question[position]);
        item.setAnswer(answer[position]);

        return item;
    }
}
