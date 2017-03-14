
package cuexpo.cuexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AreaDao {

    @SerializedName("centerPoint")
    @Expose
    private PointDao pointDao;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nameEn")
    @Expose
    private String nameEn;
    @SerializedName("nameTh")
    @Expose
    private String nameTh;

    public PointDao getPointDao() {
        return pointDao;
    }

    public void setPointDao(PointDao pointDao) {
        this.pointDao = pointDao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameTh() {
        return nameTh;
    }

    public void setNameTh(String nameTh) {
        this.nameTh = nameTh;
    }
}
