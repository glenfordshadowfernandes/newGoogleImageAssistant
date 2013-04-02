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

	String _LOGTAG = "GoogleImageSearch";
	int imageID;
	Boolean Favflag = false;
	Bitmap bmp;
	ArrayList<Bitmap> _thumbnailImages;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * requestWindowFeature(Window.FEATURE_NO_TITLE);
		 * getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		 * WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */
		setContentView(R.layout.image_fullscreen_view);

		ImageView imageToDisplay = (ImageView) findViewById(R.id.fullscreenView);
		Intent imgIntent = getIntent();
		Bitmap fetchedImage = (Bitmap) imgIntent
				.getParcelableExtra("imageparsed");
		imageID = imgIntent.getIntExtra("ImageID", 0);
		_thumbnailImages = getIntent().getParcelableArrayListExtra("bitmapArray");
		Log.i(_LOGTAG, "Recieved Image ID = " + imageID);
		Log.i(_LOGTAG, "Recieved Image height = " + fetchedImage.getHeight());
		Log.i(_LOGTAG, "Recieved Image width = " + fetchedImage.getWidth());
		imageToDisplay.setImageBitmap(fetchedImage);

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
			Toast.makeText(getBaseContext(), "You selected Fav",
					Toast.LENGTH_SHORT).show();
			if (!Favflag) {

				item.setIcon(android.R.drawable.star_on);
				this.Favflag = true;
				
				bmp = _thumbnailImages.get(imageID);
				
				try {
					// write the bytes in file
					FileOutputStream fo;
					ByteArrayOutputStream bytes = new ByteArrayOutputStream();
					bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

					// you can create a new file name "test.jpg" in sdcard
					// folder.
					File f = new File(Environment.getExternalStorageDirectory()
							+ File.separator + "test.jpg");
					f.createNewFile();
					fo = new FileOutputStream(f);
					fo.write(bytes.toByteArray());
					// remember close the FileOutput
					fo.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				item.setIcon(android.R.drawable.star_off);
				this.Favflag = false;
			}
			break;

		/*
		 * case R.id.visited: Toast.makeText(getBaseContext(),
		 * "You selected Visited", Toast.LENGTH_SHORT).show(); break;
		 */

		}

		return true;

	}

}
