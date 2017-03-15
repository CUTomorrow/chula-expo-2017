package cuexpo.cuexpo2017.view;

/**
 * Created by APTX-4869 (LOCAL) on 1/25/2017.
 */

public class EventListItem {
    private String id;
    private String title;
    private String time;
    private String tag;
    private String thumbnail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EventListItem(String id, String title, String time, String tag, String thumbnail) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.tag = tag;
        this.thumbnail = thumbnail;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
