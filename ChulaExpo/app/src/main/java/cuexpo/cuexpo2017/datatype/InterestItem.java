package cuexpo.cuexpo2017.datatype;

/**
 * Created by APTX-4869 (LOCAL) on 1/9/2017.
 */

public class InterestItem {
    private int id;
    private String name;
    private String nameEng;
    private String iconUrl;
    private int imageSrc;
    private int iconSrc;
    private boolean interest;

    public InterestItem(int id, String name, String nameEng) {
        this.id = id;
        this.name = name;
        this.nameEng = nameEng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InterestItem(String name, String nameEng, int imageSrc, int iconSrc, boolean interest) {
        this.name = name;
        this.imageSrc = imageSrc;
        this.interest = interest;

        this.nameEng = nameEng;
        this.iconSrc = iconSrc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInterest() {
        return interest;
    }

    public void setInterest(boolean interest) {
        this.interest = interest;
    }

    public String getNameEng() { return nameEng;}

    public void setNameEng(String nameEng) { this.nameEng = nameEng;}

    public String getIconUrl() { return iconUrl;}

    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl;}

    public int getImageSrc() {return imageSrc;}

    public void setImageSrc(int imageSrc) {this.imageSrc = imageSrc;}

    public int getIconSrc() {return iconSrc;}

    public void setIconSrc(int iconSrc) {this.iconSrc = iconSrc;}


}
