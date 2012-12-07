package com.ikehiroki.p2t.activity;


import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class PhotoActivity extends Activity {

	private CameraPreview mPreview;
    Camera mCamera;
    int numberOfCameras;
    int cameraCurrentlyLocked;
    int defaultCameraId;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mPreview = new CameraPreview(this);        
        setContentView(mPreview);
         
        numberOfCameras = Camera.getNumberOfCameras();
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                defaultCameraId = i;
            }
        }
    }
     
    @Override
    protected void onResume() {
        super.onResume();
        mCamera = Camera.open();
        cameraCurrentlyLocked = defaultCameraId;
        mPreview.setCamera(mCamera);
    }
     
    @Override
    protected void onPause() {
        super.onPause();
        if (mCamera != null) {
            mPreview.setCamera(null); 
            mCamera.release();
            mCamera = null;
        }   
    } 

}
