package cuexpo.chulaexpo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cuexpo.chulaexpo.R;
import cuexpo.chulaexpo.view.EventListItem;

/**
 * Created by APTX-4869 (LOCAL) on 1/25/2017.
 */

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchViewHolder> {

    private List<EventListItem> eventList;
    private boolean isSearching;

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        public TextView title, time;
        public LinearLayout tags;
        public SearchViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            tags = (LinearLayout) view.findViewById(R.id.tags);
        }
    }

    public SearchListAdapter(List<EventListItem> eventList, boolean isSearching) {
        this.eventList = eventList;
        this.isSearching = isSearching;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new SearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        if(isSearching) setEventItem(holder, position);
        else {
            switch (position){
                case 0:
                    setEventItem(holder, position);
                    break;
                case 1:
                    setEventItem(holder, position);
                    break;
                case 2:
                    setEventItem(holder, position);
                    break;
                default:
                    setEventItem(holder, position-3);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(isSearching) return eventList.size();
        return eventList.size()+3;
    }

    private void setEventItem(SearchViewHolder holder, int eventPosition) {
        EventListItem movie = eventList.get(eventPosition);
        holder.title.setText(movie.getTitle());
        holder.time.setText(movie.getTime());
    }

}
