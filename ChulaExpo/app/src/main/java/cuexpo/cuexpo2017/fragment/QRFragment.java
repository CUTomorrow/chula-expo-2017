package cuexpo.cuexpo2017.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import net.glxn.qrgen.android.QRCode;

import java.util.HashMap;
import java.util.Map;

import cuexpo.cuexpo2017.MainApplication;
import cuexpo.cuexpo2017.R;
import cuexpo.cuexpo2017.activity.MainActivity;
import cuexpo.cuexpo2017.adapter.ScannerActivity;
import cuexpo.cuexpo2017.utility.CameraPermissionUtils;
import cuexpo.cuexpo2017.utility.CameraPermissionUtils;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by James on 20/01/2017.
 */

public class QRFragment extends Fragment implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    ImageView ivQRProfile,ivQR, ivClear;
    TextView tvQRName, tvQRPersonalInfo;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String uid,name,year,school,level,workerJob,type, profile;
    private final static int REQUEST_QR = 0;

    private static Bitmap QRCache = null;

    public QRFragment() {
        super();
    }

    private void enableCamera() {
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            CameraPermissionUtils.requestPermission((AppCompatActivity) this.getActivity(), 1,
                    Manifest.permission.CAMERA, true);
        } else {
            Intent intent = new Intent(getActivity(), ScannerActivity.class);
            startActivityForResult(intent, REQUEST_QR);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != 1) {
            return;
        }

        if (CameraPermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.CAMERA)) {
            Intent intent = new Intent(getActivity(), ScannerActivity.class);
            startActivityForResult(intent, REQUEST_QR);
//            enableCamera();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            CameraPermissionUtils.PermissionDeniedDialog
                    .newInstance(true).show(this.getFragmentManager(), "dialog");
        }
    }

    @SuppressWarnings("unused")
    public static QRFragment newInstance() {
        QRFragment fragment = new QRFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_qrcode, container, false);
        initInstances(rootView, savedInstanceState);
        rootView.findViewById(R.id.scan_qr).setOnClickListener(scanQrOCL);
        return rootView;
    }

    private View.OnClickListener scanQrOCL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            enableCamera();
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (requestCode == REQUEST_QR && resultCode == MainActivity.RESULT_OK) {
            String qrValue = data.getExtras().getString("QR_VALUE", "something went wrong");
            if (qrValue.contains("chulaexpo.com/api/activities/")) {
                int startIndex = qrValue.indexOf("activities/") + 11;
                int endIndex = qrValue.indexOf('/', startIndex);
                String eventId = qrValue.substring(startIndex, endIndex);

                SharedPreferences activitySharedPref = getActivity().getSharedPreferences("Event", Context.MODE_PRIVATE);
                activitySharedPref.edit().putString("EventID", eventId).apply();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.qr_container, new EventDetailFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle("Message")
                        .setMessage(qrValue)
                        .setNeutralButton("ตกลง", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .show();
            }
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }

    private void init() {
        // Init Fragment level's variable(s) here
        //get SharedPref
        sharedPref = this.getActivity().getSharedPreferences("FacebookInfo", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        uid = sharedPref.getString("uid","");
        //Log.e("QR","uid: "+uid);
        profile = sharedPref.getString("profile","");
        name = sharedPref.getString("name","");
        type = sharedPref.getString("type","");
        if(type.equals("Academic")){
            year = sharedPref.getString("academicYear","");
            school = sharedPref.getString("academicSchool","");
            level = sharedPref.getString("academicLevel","");

        } else if(type.equals("Worker")) {
            workerJob = sharedPref.getString("workerJob","");
        }
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        // Init 'View' instance(s) with rootView.findViewById here
        ivQRProfile = (ImageView) rootView.findViewById(R.id.ivQRProfile);
        ivQR = (ImageView) rootView.findViewById(R.id.ivQR);
        tvQRName = (TextView) rootView.findViewById(R.id.tvQrName);
        tvQRPersonalInfo = (TextView) rootView.findViewById(R.id.tvQRPersonalInfo);
        ivClear = (ImageView) rootView.findViewById(R.id.ivClear);
        ivClear.setOnClickListener(this);

        Glide.with(getContext())
                .load(profile)
                .placeholder(R.drawable.iv_profile_temp)
                .error(R.drawable.iv_profile_temp)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(ivQRProfile);

        tvQRName.setText(name);
        //if(type.equals("Academic")) tvQRPersonalInfo.setText("Year"+year+" • "+school);
        if(type.equals("Academic")) tvQRPersonalInfo.setText(level+" "+year+" "+school);
        else if(type.equals("Worker")) tvQRPersonalInfo.setText(workerJob);
        else if(type.equals("Staff")) tvQRPersonalInfo.setText("Staff");
        else tvQRPersonalInfo.setText("");

        try {
            //Bitmap qrBm = QRCode.from((String) tvQRName.getText()).bitmap();
            //ivQR.setImageBitmap(qrBm);

            String charset = "UTF-8";
            Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            String qrCodeData = uid;
            int width =300;
            int height = 300;
            int smallestDimension = width < height ? width : height;
            CreateQRCode(qrCodeData, charset, hintMap, smallestDimension, smallestDimension);
        } catch (Exception e){

        }
    }

    @Override
    public void onClick(View v) {
        if(v==ivClear){
            getFragmentManager().popBackStack();
        }
    }

    public void CreateQRCode(String qrCodeData, String charset, Map hintMap, int qrCodeheight, int qrCodewidth){

        try {
            //generating qr code in bitmatrix type

            if(QRCache != null) {
                //Log.e("QR","QRCache");
                ivQR.setImageBitmap(QRCache);
                return ;
            }

            BitMatrix matrix = new MultiFormatWriter().encode(
                new String(qrCodeData.getBytes(charset), charset),
                BarcodeFormat.QR_CODE, qrCodewidth, qrCodeheight, hintMap
            );
            //converting bitmatrix to bitmap

            int width = matrix.getWidth();
            int height = matrix.getHeight();

            int[] pixels = new int[width * height];
            // All are 0, or black, by default
            Resources resources = getResources();
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    //pixels[offset + x] = matrix.get(x, y) ? BLACK : WHITE;
                    pixels[offset + x] = matrix.get(x, y) ?
                            ResourcesCompat.getColor(resources,R.color.black,null) :  ResourcesCompat.getColor(resources,R.color.white,null);
                }
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            //setting bitmap to image view

            Bitmap overlay = BitmapFactory.decodeResource(getResources(), R.drawable.iv_qr_icon);
            overlay = Bitmap.createScaledBitmap(overlay, 40, 40, true);
            QRCache = mergeBitmaps(overlay, bitmap);
            ivQR.setImageBitmap(QRCache);

        }catch (Exception er){
            Log.e("QrGenerate",er.getMessage());
        }
    }



    public Bitmap mergeBitmaps(Bitmap overlay, Bitmap bitmap) {

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        Bitmap combined = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bitmap, new Matrix(), null);

        int centreX = (canvasWidth  - overlay.getWidth()) /2;
        int centreY = (canvasHeight - overlay.getHeight()) /2 ;
        canvas.drawBitmap(overlay, centreX, centreY, null);

        return combined;
    }
}
