
package cuexpo.cuexpo2017.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ActivitySearchItemCollectionDao {

    @SerializedName("activities")
    @Expose
    private List<ActivityItemResultDao> activities = null;
    @SerializedName("status")
    @Expose
    private StatusDao status;

    public StatusDao getStatus() {
        return status;
    }

    public void setStatus(StatusDao status) {
        this.status = status;
    }

    public List<ActivityItemResultDao> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivityItemResultDao> results) {
        this.activities = results;
    }

    public void addActivities(ActivityItemResultDao resultDao) {
        this.activities.add(resultDao);
    }

}
