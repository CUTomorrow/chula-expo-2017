package cuexpo.chulaexpo.datatype;

/**
 * Created by APTX-4869 (LOCAL) on 1/9/2017.
 */

public class InterestItem {
    private String title;
    private String imageUrl;
    private boolean interest;
    private String titleEng;
    private String iconUrl;

    public InterestItem(String title, String imageUrl, boolean interest) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.interest = interest;
    }

    public InterestItem(String title, String titleEng, String imageUrl, String iconUrl, boolean interest) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.interest = interest;
        this.titleEng = titleEng;
        this.iconUrl = iconUrl;
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

    public String getTitleEng() { return titleEng;}

    public void setTitleEng(String titleEng) { this.titleEng = titleEng;}

    public String getIconUrl() { return iconUrl;}

    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl;}
}
