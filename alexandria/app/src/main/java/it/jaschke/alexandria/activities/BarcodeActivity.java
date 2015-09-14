package it.jaschke.alexandria.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.FrameLayout;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import it.jaschke.alexandria.CameraPreview.CameraPreview;
import it.jaschke.alexandria.R;

/**
 * Created by clerks on 9/13/15.
 */
public class BarcodeActivity extends ActionBarActivity {
    private static final String TAG = BarcodeActivity.class.getSimpleName();
    public static final String NO_DATA = "NO_DATA";
    public static final String EXTRA_DATA_BARCODE = "BARCODE";

    static {
        System.loadLibrary("iconv");
    }

    private Camera camera;
    private CameraPreview cameraPreview;
    private Handler autoFocusHandler;
    private ImageScanner imageScanner;

    private boolean barCodeScanned;
    private boolean previewing;

    /**
     * Executed the auto focus every 1 second
     */
    private android.hardware.Camera.AutoFocusCallback autofocusCb = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, final Camera camera) {
            autoFocusHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (previewing) {
                        Log.d(TAG, "autofocus ");
                        camera.autoFocus(autofocusCb);
                    }
                }
            }, 1000);
        }
    };

    private android.hardware.Camera.PreviewCallback previousCb = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();
            Image barcode = new Image(size.width, size.height, "Y800");
            barcode.setData(data);
            int result = imageScanner.scanImage(barcode);

            if (result != 0) {
                previewing = false;
                camera.setPreviewCallback(null);
                camera.stopPreview();
                SymbolSet symbols = imageScanner.getResults();

                for (Symbol symbol : symbols) {
                    barCodeScanned = true;
                    Intent returnIntent = new Intent();
                    String isbn = symbol.getData();
                    returnIntent.putExtra(EXTRA_DATA_BARCODE, isbn);
                    setResult(1, returnIntent);
                    releaseCamera();
                    finish();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        autoFocusHandler = new Handler();
        camera = getCameraInstance();

        imageScanner = new ImageScanner();
        imageScanner.setConfig(0, Config.X_DENSITY, 400);
        imageScanner.setConfig(0, Config.Y_DENSITY, 400);
        imageScanner.setConfig(0, Config.ENABLE, 0);
        imageScanner.setConfig(Symbol.EAN13, Config.ENABLE, 1);
        imageScanner.setConfig(Symbol.UPCA, Config.ENABLE, 1);
        imageScanner.setConfig(Symbol.UPCE, Config.ENABLE, 1);
        imageScanner.setConfig(Symbol.EAN8, Config.ENABLE, 1);

        FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreviewFrameLayout);
        cameraPreview = new CameraPreview(this.getApplicationContext(), camera, previousCb, autofocusCb);
        preview.addView(cameraPreview);

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    public void onBackPressed() {
        releaseCamera();
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_DATA_BARCODE, NO_DATA);
        setResult(RESULT_OK, returnIntent);
        super.onBackPressed();
    }

    private Camera getCameraInstance() {
        Camera c = null;

        try {
            c = Camera.open();
        } catch (Exception exception) {
            Log.e(TAG, exception.getMessage(), exception);
        }
        return c;
    }

    private void releaseCamera() {
        if (camera != null) {
            previewing = false;
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }
}
