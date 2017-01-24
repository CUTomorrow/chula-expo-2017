package cuexpo.chulaexpo.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.HashMap;
import java.util.List;

import cuexpo.chulaexpo.manager.StageManager;
import cuexpo.chulaexpo.view.StageInsideListItem;
import cuexpo.chulaexpo.view.StageListItem;

/**
 * Created by TEST on 1/14/2017.
 */

public class StageListAdapter extends BaseExpandableListAdapter {

    List<StageListItem> listDataHeader;
    HashMap<StageListItem, StageInsideListItem> listDataChild;

    public StageListAdapter(List<StageListItem> listDataHeader,
                                 HashMap<StageListItem, StageInsideListItem> listChildData) {
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return listDataChild.get(getGroup(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        StageInsideListItem item;

        if(convertView!=null){
            item = (StageInsideListItem) convertView;
        }else{
            item = new StageInsideListItem(parent.getContext());
        }

        if(groupPosition!=getGroupCount()-1){
            item.setLineStatus(1);
        }else{
            item.setLineStatus(0);
        }

        /*if(groupPosition!=getGroupCount()-1){
            StageListItem item2= (StageListItem) getGroup(groupPosition+1);
            item.setLineStatus(manager.setLine(groupPosition,item2.getTime(),getGroupCount()));
        }else{
            item.setLineStatus(manager.setLine(groupPosition,new int[2],getGroupCount()));
        }*/

        return item;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        StageListItem item = (StageListItem) getGroup(groupPosition);
        //StageListItem itemDown;
        StageManager manager = StageManager.getInstance();

        item.setStatus(manager.setCircle(item.getStartTime(),item.getEndTime()));

        if(groupPosition==0) {
            item.setLineMode(2);
        } else if(groupPosition==getGroupCount()-1){
            item.setLineMode(3);
        }else{
            item.setLineMode(1);
        }

        /*if(groupPosition==getGroupCount()-1) {
            //item.setLineMode(manager.setGroupLine(groupPosition, item.getTime(),getGroupCount()));
        }else if(groupPosition==0){
            itemDown = (StageListItem) getGroup(groupPosition +1);
            //item.setLineMode(manager.setGroupLine(groupPosition, itemDown.getTime(), getGroupCount()));
            item.setStatus(manager.setCircle(itemDown.getTime(),item.getTime()));
        }else{
            itemDown = (StageListItem) getGroup(groupPosition +1);
            //item.setLineMode(manager.setGroupLine(itemDown.getTime(),item.getTime()));
            item.setStatus(manager.setCircle(itemDown.getTime(),item.getTime()));
        }*/

        if (isExpanded) {
            item.setDrop(2);
        } else {
            item.setDrop(1);
        }

        return item;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
