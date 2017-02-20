package cuexpo.chulaexpo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import cuexpo.chulaexpo.R;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class ReservedCheckFragment extends Fragment implements View.OnClickListener, Spinner.OnItemSelectedListener {

    ImageView ivClose;
    LinearLayout btnSave;
    Spinner spnSelectTime;
    TextView tvName;

    public ReservedCheckFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ReservedCheckFragment newInstance() {
        ReservedCheckFragment fragment = new ReservedCheckFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reserved_check, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // Init Fragment level's variable(s) here
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        ivClose = (ImageView) rootView.findViewById(R.id.reserved_check_close);
        btnSave = (LinearLayout) rootView.findViewById(R.id.reserved_check_save);
        spnSelectTime = (Spinner) rootView.findViewById(R.id.reserved_check_spinner);
        tvName = (TextView) rootView.findViewById(R.id.reserved_check_name);

        ivClose.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        spnSelectTime.setOnItemSelectedListener(this);

        String[] items = new String[]{"16 มีนาคม 2017 : 10.00 - 11.00", "17 มีนาคม 2017 : 10.00 - 11.00", "18 มีนาคม 2017 : 10.00 - 11.00"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (getContext(), android.R.layout.simple_dropdown_item_1line, items);
        spnSelectTime.setAdapter(adapter);
        spnSelectTime.setSelection(0);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    @Override
    public void onClick(View v) {
        if (v == btnSave) {
            System.out.println(spnSelectTime.getSelectedItem().toString());
            //TODO API with Server to check availability
            getFragmentManager().popBackStack();
        } else if (v == ivClose) {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        View spinnerSelectedView = spnSelectTime.getSelectedView();
        if (spinnerSelectedView != null) {
            ((TextView) spinnerSelectedView).setTextColor(Color.WHITE);
            ((TextView) spinnerSelectedView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
