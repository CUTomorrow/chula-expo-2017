package cuexpo.cuexpo2017.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.dao.ReserveDao;
import cuexpo.cuexpo2017.dao.RoundDao;
import cuexpo.cuexpo2017.manager.DateConversionManager;
import cuexpo.cuexpo2017.manager.HttpManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
@SuppressWarnings("unused")
public class ReservedCheckFragment extends Fragment implements View.OnClickListener, Spinner.OnItemSelectedListener {

    private ImageView ivClose;
    private LinearLayout btnSave;
    private Spinner spnSelectTime;
    private TextView tvName;
    private RoundDao dao;
    private ReserveDao dao2;
    private int selectedPos;
    private String aid;
    private String aName;

    public ReservedCheckFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ReservedCheckFragment newInstance(String aid, String aName) {
        ReservedCheckFragment fragment = new ReservedCheckFragment();
        Bundle args = new Bundle();
        args.putString("aid", aid);
        args.putString("aName", aName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        aid = getArguments().getString("aid", "");
        aName = getArguments().getString("aName", "");

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

        JSONObject range = new JSONObject();
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            Date startDate = new Date();
            range.put("gte", startDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<RoundDao> callRound = HttpManager.getInstance().getService().loadRoundsById(aid, range, "start");
        callRound.enqueue(callbackRound);

    }

    Callback<RoundDao> callbackRound = new Callback<RoundDao>() {
        @Override
        public void onResponse(Call<RoundDao> call, Response<RoundDao> response) {
            if (response.isSuccessful()) {
                dao = response.body();

                tvName.setText(aName);

                String[] items = new String[dao.getResults().size()];

                for (int i = 0; i < dao.getResults().size(); i++) {
                    items[i] = DateConversionManager.getInstance()
                            .ConvertDate(dao.getResults().get(i).getStart()
                                    , dao.getResults().get(i).getEnd());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>
                        (getContext(), R.layout.spinner_reserved_check_item, R.id.reserved_check_spinner_text, items);
                adapter.setDropDownViewResource(R.layout.spinner_reserved_check_drop_item);
                spnSelectTime.setAdapter(adapter);
                selectedPos = 0;
            } else {
                try {
                    Log.e("Reserved Check Fragment", "Call Round Not Success " + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<RoundDao> call, Throwable t) {
            Log.e("Reserved Check Fragment", "Call Round Fail");
        }
    };

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
            String aid = dao.getResults().get(selectedPos).getActivityId();
            String rid = dao.getResults().get(selectedPos).getId();

            Call<ReserveDao> callReserve = HttpManager.getInstance().getService().reserveSelectedRound(aid, rid);
            callReserve.enqueue(callbackReserve);
            getFragmentManager().popBackStack();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.event_detail_overlay, new EventDetailFragment());
            fragmentTransaction.commit();
        } else if (v == ivClose) {
            getFragmentManager().popBackStack();
        }
    }

    Callback<ReserveDao> callbackReserve = new Callback<ReserveDao>() {
        @Override
        public void onResponse(Call<ReserveDao> call, Response<ReserveDao> response) {
            if (response.isSuccessful()) {
                dao2 = response.body();
                Toast.makeText(Contextor.getInstance().getContext(), dao2.getSuccess() ? "จองสำเร็จ" : "จองไม่สำเร็จ "
                        + dao2.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Reserved Check Fragment", "Reserve Round " + dao2.getSuccess() + " " + dao2.getMessage());
            } else {
                try {
                    Toast.makeText(Contextor.getInstance().getContext(), "จองไม่สำเร็จ", Toast.LENGTH_LONG).show();
                    Log.e("Reserved Check Fragment", "Reserve Round Not Success " + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<ReserveDao> call, Throwable t) {
            Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_LONG).show();
            Log.e("Reserved Check Fragment", "Call Round Fail");
        }
    };


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedPos = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
