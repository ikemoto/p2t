package com.ikehiroki.p2t.activity;

import java.io.FileOutputStream;

import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PhotoActivity extends Activity {

	protected Camera camera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		SurfaceView surfaceView = (SurfaceView)findViewById(R.id.surface_view);
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(surfaceListener);

	
	}
	
	private SurfaceHolder.Callback surfaceListener = new SurfaceHolder.Callback() {

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			camera.stopPreview();
	        Camera.Parameters params = camera.getParameters();
	        params.setPreviewFormat(format);
	        params.setPreviewSize(width, height);
	        camera.setParameters(params);
	        camera.startPreview();
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
	        camera = Camera.open();
	        try {
	        	camera.setPreviewDisplay(holder);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			if (camera != null) {
				camera.stopPreview();
				camera.release();
				camera = null;
	        }
		}

	};
	
	// シャッターが押されたときに呼ばれるコールバック
	private Camera.ShutterCallback mShutterListener =
	    new Camera.ShutterCallback() {
	        public void onShutter() {
	            // TODO Auto-generated method stub
	        }
	    };
	 
	// JPEGイメージ生成後に呼ばれるコールバック
	private Camera.PictureCallback mPictureListener =
	    new Camera.PictureCallback() {
	        public void onPictureTaken(byte[] data, Camera camera) {
	            // SDカードにJPEGデータを保存する
	            if (data != null) {
	                FileOutputStream stream = null;
	                try {
	                	stream = new FileOutputStream("/sdcard/camera_test.jpg");
	                	stream.write(data);
	                	stream.close();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	 
	                camera.startPreview();
	            }
	        }
	    };
	 
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        if (camera != null) {
	            camera.takePicture(mShutterListener, null, mPictureListener);
	        }
	    }
	    return true;
	}


}
