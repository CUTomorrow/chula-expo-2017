package cuexpo.cuexpo2017.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    String TAG = "QRScannerFragment";
    private ZBarScannerView mScannerView;
    private boolean mFlash = false;
    FrameLayout currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        currentView = new FrameLayout(this);
        currentView.setBackgroundColor(Color.BLACK);
        setContentView(currentView);

        this.setTitle("Event Detail - Scan QR Code");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menuItem;
        menuItem = menu.add(Menu.NONE, 0, 0, "FLASH");
        MenuItemCompat.setShowAsAction(menuItem, MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                mFlash = !mFlash;

                if (mScannerView != null) {
                    try {
                        mScannerView.setFlash(mFlash);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView = new ZBarScannerView(ScannerActivity.this);
                mScannerView.setResultHandler(ScannerActivity.this); // Register ourselves as a handler for scan results.
                mScannerView.startCamera();          // Start camera on resume
                mScannerView.setFlash(mFlash);
                setContentView(mScannerView);
            }
        }, 50);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScannerView != null) {
            mScannerView.stopCamera();           // Stop camera on pause
        }
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            // Do nothing
        }
        try {
            Log.v(TAG, rawResult.getContents()); // Prints scan results
            Log.v(TAG, rawResult.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        Intent returnIntent = new Intent();
        returnIntent.putExtra("QR_VALUE", rawResult.getContents());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
