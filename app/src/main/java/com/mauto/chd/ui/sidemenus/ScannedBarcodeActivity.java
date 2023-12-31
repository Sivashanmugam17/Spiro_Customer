//package com.cabilyhandyforalldinedoo.chd.ui.sidemenus;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.SparseArray;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.vision.CameraSource;
//import com.google.android.gms.vision.Detector;
//import com.google.android.gms.vision.barcode.Barcode;
//import com.google.android.gms.vision.barcode.BarcodeDetector;
//
//import java.io.IOException;
//
//import cabily.handyforall.dinedoo.R;
//
//public class ScannedBarcodeActivity extends AppCompatActivity {
//
//    SurfaceView surfaceView;
//    private static final int REQUEST_CAMERA_PERMISSION = 201;
//    private BarcodeDetector barcodeDetector;
//    private CameraSource cameraSource;
//    TextView txtBarcodeValue;
//    String intentData = "",order_barcode = "",page ="delivery";
//    Button btnAction;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scanned_barcode);
//        initViews();
//    }
//
//
//
//    private void initViews() {
//        txtBarcodeValue = findViewById(R.id.txtBarcodeValue);
//        surfaceView = findViewById(R.id.surfaceView);
//        btnAction = findViewById(R.id.button);
//        Intent intent = getIntent();
//        page = intent.getStringExtra("page");
////        if (page.equalsIgnoreCase("delivery")){
////            order_barcode = intent.getStringExtra("barcode_text");
////        }
//
//
//        btnAction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (intentData.length() > 0) {
//
//                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(intentData)));
//
//                }
//            }
//        });
//    }
//
//
//
//    private void initialiseDetectorsAndSources() {
//
//        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
//        barcodeDetector = new BarcodeDetector.Builder(this)
//                .setBarcodeFormats(Barcode.ALL_FORMATS)
//
//                .build();
//
//        cameraSource = new CameraSource.Builder(this, barcodeDetector)
//                .setRequestedPreviewSize(1920, 1080)
//                .setAutoFocusEnabled(true) //you should add this feature
//                .build();
//
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                try {
//                    if (ActivityCompat.checkSelfPermission(ScannedBarcodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                        cameraSource.start(surfaceView.getHolder());
//                    } else {
////                        ActivityCompat.requestPermissions(ScannedBarcodeActivity.this, new
////                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                cameraSource.stop();
//            }
//        });
//
//
//        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
//            @Override
//            public void release() {
////                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void receiveDetections(Detector.Detections<Barcode> detections) {
//                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
//                if (barcodes.size() != 0) {
//                    txtBarcodeValue.post(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            if (barcodes.valueAt(0).rawValue.length()>0) {
//                                txtBarcodeValue.removeCallbacks(null);
//                                intentData = barcodes.valueAt(0).displayValue;
//                                Log.d("batteriy_code",intentData);
////                                if (order_barcode.equalsIgnoreCase(intentData)){
//                                    txtBarcodeValue.setText("Barcode Detected..!");
//                                    Intent intent_result = new Intent();
//                                    intent_result.putExtra("code", intentData);
//                                    setResult(Activity.RESULT_OK, intent_result);
//                                    finish();
////                                } else {
////                                    txtBarcodeValue.setText("Barcode Not Matched, Try Agin..!");
////                                }
////                                if (page.equalsIgnoreCase("delivery")){
////
////                                } else {
////                                    txtBarcodeValue.setText("Barcode Detected..!");
////                                    Intent intent_result = new Intent();
////                                    intent_result.putExtra("code", intentData);
////                                    setResult(Activity.RESULT_OK, intent_result);
////                                    finish();
////                                }
//
//                            } else {
////                                isEmail = false;
//                                btnAction.setText("LAUNCH URL");
//                                intentData = barcodes.valueAt(0).displayValue;
////                                txtBarcodeValue.setText(intentData);
//                            }
//                        }
//                    });
//                }
//            }
//        });
//    }
//
//
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        cameraSource.release();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        initialiseDetectorsAndSources();
//    }
//}
