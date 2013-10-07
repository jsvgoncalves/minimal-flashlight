package com.znig.flashlight;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.WindowManager;

public class MainActivity extends Activity {
	Camera cam;
	boolean camOpen = false;
	boolean hasFlash = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		WindowManager.LayoutParams layout = getWindow().getAttributes();
		hasFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
		
		if(!camOpen && hasFlash) {
			System.out.println("##############buh.. opening camera");
			try {
				layout.screenBrightness = 0F;
				cam = Camera.open();
				camOpen = true;
				System.out.println("Cam open: " + camOpen);
				flashLightOn();
			} catch (Exception e) {
				System.out.println("OH NOEZ!");
			}
		} else {
			// Change brightness
			layout.screenBrightness = 1F;
			getWindow().setAttributes(layout); 
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}
	
	public void flashLightOn() {
		try {
			Parameters p = cam.getParameters();
			p.setFlashMode(Parameters.FLASH_MODE_TORCH);
			cam.setParameters(p);
			cam.startPreview();
		} catch (Exception e) {
			
		}
	}
	public void flashLightOff() {
	    try {
	        if (hasFlash) {
	            cam.stopPreview();
	            cam.release();
	            cam = null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		flashLightOff();
	}
	
}
