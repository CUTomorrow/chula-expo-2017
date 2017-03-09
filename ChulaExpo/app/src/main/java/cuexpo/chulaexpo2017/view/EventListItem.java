package cuexpo.chulaexpo2017.view;

/**
 * Created by APTX-4869 (LOCAL) on 1/25/2017.
 */

public class EventListItem {
    private int id;
    private String title;
    private String time;
    private String[] tag;

    public EventListItem(int id, String title, String time, String[] tag) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }
}
