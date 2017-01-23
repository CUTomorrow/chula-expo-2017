package cuexpo.chulaexpo.dao;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by dragonnight on 26/12/2559.
 */

public class ActivityItemCollectionDao implements Parcelable {
    //TODO : Change method according API

    @SerializedName("data")
    @Expose
    private List<ActivityItemDao> data = null;

    public List<ActivityItemDao> getData() {
        return data;
    }

    public void setData(List<ActivityItemDao> data) {
        this.data = data;
    }

    protected ActivityItemCollectionDao(Parcel in) {
        data = in.createTypedArrayList(ActivityItemDao.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ActivityItemCollectionDao> CREATOR = new Creator<ActivityItemCollectionDao>() {
        @Override
        public ActivityItemCollectionDao createFromParcel(Parcel in) {
            return new ActivityItemCollectionDao(in);
        }

        @Override
        public ActivityItemCollectionDao[] newArray(int size) {
            return new ActivityItemCollectionDao[size];
        }
    };
}
