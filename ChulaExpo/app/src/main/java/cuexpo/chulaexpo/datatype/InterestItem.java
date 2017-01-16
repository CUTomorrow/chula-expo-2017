package cuexpo.chulaexpo.datatype;

/**
 * Created by APTX-4869 (LOCAL) on 1/9/2017.
 */

public class InterestItem {
    private String title;
    private String imageUrl;
    private boolean interest;

    public InterestItem(String title, String imageUrl, boolean interest) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.interest = interest;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isInterest() {
        return interest;
    }

    public void setInterest(boolean interest) {
        this.interest = interest;
    }
}
