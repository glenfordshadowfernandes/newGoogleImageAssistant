
package com.example.googleimagesearch;

import java.util.ArrayList;

import android.app.Application;
import android.graphics.Bitmap;

public class GlobalResource extends Application {

	ArrayList<Bitmap>	Images	= new ArrayList<Bitmap>();

	public ArrayList<Bitmap> getImages() {
		return Images;
	}

	public void setImages(ArrayList<Bitmap> images) {
		this.Images = images;
	}

}
