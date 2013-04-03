/*
 * Author: Glenford Fernandes
 * Github Rep: https://github.com/glenfordshadowfernandes/newGoogleImageAssistant.git
 * 
 */

package com.example.googleimagesearch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ImageFullscreenActivity extends Activity {

	String				_LOGTAG	= "GoogleImageSearch";
	int					imageID;
	Boolean				Favflag	= false;
	Bitmap				bmp;
	ArrayList<Bitmap>	_thumbnailImages;
	Bitmap				fetchedImage;
	File				myDir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */
		setContentView(R.layout.image_fullscreen_view);
		GlobalResource globalRes = (GlobalResource) getApplication();
		_thumbnailImages = globalRes.getImages();

		ImageView imageToDisplay = (ImageView) findViewById(R.id.fullscreenView);
		Intent imgIntent = getIntent();
		fetchedImage = (Bitmap) imgIntent.getParcelableExtra("imageparsed");
		imageID = imgIntent.getIntExtra("ImageID", 0);
		// _thumbnailImages =
		// getIntent().getParcelableArrayListExtra("bitmapArray");
		Log.i(_LOGTAG, "Recieved Image ID = " + imageID);
		Log.i(_LOGTAG, "Recieved Image height = " + fetchedImage.getHeight());
		Log.i(_LOGTAG, "Recieved Image width = " + fetchedImage.getWidth());
		// imageToDisplay.setImageBitmap(fetchedImage);
		imageToDisplay.setImageBitmap(_thumbnailImages.get(imageID));

		myDir = new File("/sdcard/GoogleImageSearch");
		if (!myDir.exists()) {
			myDir.mkdirs();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.items, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
			case R.id.markFav:

				if (!Favflag) {

					item.setIcon(android.R.drawable.star_on);
					this.Favflag = true;
					bmp = _thumbnailImages.get(imageID);
					Random generator = new Random();
					int n = 10000;
					n = generator.nextInt(n);
					// bmp = fetchedImage;

					try {

						FileOutputStream fo;
						ByteArrayOutputStream bytes = new ByteArrayOutputStream();
						bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
						/*
						 * File f = new File(Environment.getExternalStorageDirectory() + File.separator + "test" +
						 * imageID + ".jpg");
						 */
						File f = new File(myDir, "test" + imageID + n + ".jpg");
						if (f.exists()) {
							f.delete();
						}
						f.createNewFile();
						Log.i(_LOGTAG, "File saved location = " + f);
						fo = new FileOutputStream(f);
						fo.write(bytes.toByteArray());
						Toast.makeText(getBaseContext(), "Image Saved as Favourite", Toast.LENGTH_LONG).show();
						fo.close();
					}
					catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				else {
					item.setIcon(android.R.drawable.star_off);
					this.Favflag = false;
				}
				break;

		/*
		 * case R.id.visited: Toast.makeText(getBaseContext(), "You selected Visited", Toast.LENGTH_SHORT).show();
		 * break;
		 */

		}

		return true;

	}

}
