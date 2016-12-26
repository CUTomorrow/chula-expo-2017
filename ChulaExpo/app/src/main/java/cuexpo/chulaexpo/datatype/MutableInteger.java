package cuexpo.chulaexpo.datatype;

import android.os.Bundle;

/**
 * Created by dragonnight on 27/12/2559.
 */

public class MutableInteger {
    private  int value;

    public MutableInteger(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Bundle onSavedInstanceState(){
        Bundle bundle = new Bundle();
        bundle.putInt("value",value);
        return  bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        value = savedInstanceState.getInt("value");
    }
}
