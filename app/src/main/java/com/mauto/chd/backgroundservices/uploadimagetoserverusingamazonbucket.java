package com.mauto.chd.backgroundservices;


import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferService;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.util.IOUtils;
import com.mauto.chd.event_bus_connection.IntentServiceResult;
import com.mauto.chd.SessionManagerPackage.SessionManager;
import com.mauto.chd.R;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class uploadimagetoserverusingamazonbucket extends IntentService{


    TransferObserver uploadObserver;
    String uploaded_file_name = "";
    String File_names = "";
    String Extension = "";
    SessionManager mSessionManager;
    private String s3_bucket_access_key = "";
    private String s3_bucket_secret_key = "";
    private String s3_bucket_name = "";
    private AmazonS3Client s3Client;
    private BasicAWSCredentials credentials;
    String statsusofimage = "failed";

    String driversPicture="driversPicture/";
    String vehicle_images="vehicleImage/";
    String documentsFiles="documentsFiles/";

    public uploadimagetoserverusingamazonbucket() {
        super("amazon image upload IntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.hasExtra("api_name"))
        {
            mSessionManager = new SessionManager(getApplicationContext());
            s3_bucket_name = mSessionManager.getKeyS3BucketName();
            s3_bucket_access_key = mSessionManager.getKeyS3AccessKey();
            s3_bucket_secret_key = mSessionManager.getKeyS3SecretKey();


            if(!mSessionManager.getxmppprofile_picture().equals(""))
            {
                driversPicture = mSessionManager.getxmppprofile_picture();
            }
            if(!mSessionManager.getxmppvehicle_image().equals(""))
            {
                vehicle_images = mSessionManager.getxmppvehicle_image();
            }
            if(!mSessionManager.getxmppdocuments().equals(""))
            {
                documentsFiles = mSessionManager.getxmppdocuments();
            }




            getApplicationContext().startService(new Intent(getApplicationContext(), TransferService.class));
            credentials = new BasicAWSCredentials(s3_bucket_access_key, s3_bucket_secret_key);
            s3Client = new AmazonS3Client(credentials, Region.getRegion(Regions.US_EAST_1));


            String filename = intent.getStringExtra("api_name");
            String state = intent.getStringExtra("state");
            File file = new File(filename);
            if(file.exists())
            {
                if(isOnline() == true)
                {
                    uploadToS3Bucket(Uri.fromFile(file),state);
                }
                else
                {
                    EventBus.getDefault().post(new IntentServiceResult(Activity.RESULT_OK, statsusofimage, getString(R.string.amazonimageuploaded)));
                }
            }
        }
    }

    private void uploadToS3Bucket(Uri fileUri,String state) {
        String[] path_split;
        if (fileUri != null)
        {
            String path = String.valueOf(fileUri);
            String file_name_full = path.substring(path.lastIndexOf("/") + 1);
            path_split = file_name_full.split(".");
            if (path_split.length == 2) {
                File_names = path_split[0];
                Extension = path_split[1];

            } else {
                path_split = file_name_full.split(".jpg");
                if (path_split.length == 1) {
                    File_names = path_split[0];
                }
            }

            String file_name_upload = File_names;
            //current time stamp
            Long tsLong = System.currentTimeMillis() / 1000;
            String str_current_ts = tsLong.toString();
            String concat_image_name = file_name_upload + "" + str_current_ts;
            String fileName = md5Encryption(concat_image_name) + "." + "jpg";

            final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "/" + file_name_full);
            createFile(getApplicationContext(), fileUri, file);
            TransferUtility transferUtility =
                    TransferUtility.builder()
                            .context(getApplicationContext())
                            .defaultBucket(s3_bucket_name)
                            .s3Client(s3Client)
                            .build();
            uploaded_file_name = fileName;

            if(state.equals("1"))
            {
                uploadObserver = transferUtility.upload(driversPicture + fileName, file, CannedAccessControlList.PublicRead);
            }
            else if(state.equals("2"))
            {
                uploadObserver = transferUtility.upload(vehicle_images + fileName, file, CannedAccessControlList.PublicRead);
            }
            else  if(state.equals("3"))
            {
                uploadObserver = transferUtility.upload(documentsFiles + fileName, file, CannedAccessControlList.PublicRead);
            }


            // Attach a listener to the observer to get state update and progress notifications
            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    // Handle a completed upload.
                    if (TransferState.COMPLETED == state)
                    {
                        Log.d("YourActivity", "image completd");
                        statsusofimage = uploaded_file_name;
                        EventBus.getDefault().post(new IntentServiceResult(Activity.RESULT_OK, statsusofimage, getString(R.string.amazonimageuploaded)));
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;
                    Log.d("YourActivity", "ID:" + id + " bytesCurrent: " + bytesCurrent + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
                }

                @Override
                public void onError(int id, Exception ex) {
                    // Handle errors
                    ex.printStackTrace();
                    EventBus.getDefault().post(new IntentServiceResult(Activity.RESULT_OK, statsusofimage, getString(R.string.amazonimageuploaded)));
                    Log.d("d35ffhf",statsusofimage);
                }
            });
            if (TransferState.COMPLETED == uploadObserver.getState()) {
                // Handle a completed upload.
                Log.d("completed upload", "completed upload");
                EventBus.getDefault().post(new IntentServiceResult(Activity.RESULT_OK, statsusofimage, getString(R.string.amazonimageuploaded)));
            }

        }
    }


    public String md5Encryption(String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void createFile(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
