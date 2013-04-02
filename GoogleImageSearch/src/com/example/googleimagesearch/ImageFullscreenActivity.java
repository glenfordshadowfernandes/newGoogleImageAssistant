/*
 * Author: Glenford Fernandes
 * Github Rep: https://github.com/glenfordshadowfernandes/newGoogleImageAssistant.git
 * 
 */

package com.example.googleimagesearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ImageFullscreenActivity extends Activity {

	String _LOGTAG = "GoogleImageSearch";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */
		setContentView(R.layout.image_fullscreen_view);

		Intent imgIntent = getIntent();
		Bitmap fetchedImage = (Bitmap) imgIntent.getExtras().get("imageparsed");
		//Log.i(_LOGTAG, "Fetched Image Attrs = "+fetchedImage.describeContents());
		ImageView imageToDisplay = (ImageView) findViewById(R.id.fullscreenView);
		imageToDisplay.setLayoutParams(new RelativeLayout.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT));
		imageToDisplay.setImageBitmap(fetchedImage);
		imageToDisplay.setScaleType(ImageView.ScaleType.FIT_XY);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
