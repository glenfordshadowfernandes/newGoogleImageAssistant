package com.example.googleimagesearch;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	EditText _search_query_box;
	AutoCompleteTextView _search_query_auto;
	String _search_query;
	String _APIKEY = "AIzaSyBYHDjXAWUoeeIHraRvdenfpUUXQL3c56w";
	// String _cx = "002151139267678842292:vfsfogsw2da";
	String _cx = "002151139267678842292:vfsfogsw2da";
	Button _search_button;
	String URL;
	JSONObject jObject;
	JSONArray _IMAGE_RESULTS;
	String imageLink;
	Bitmap imagePic;
	LinearLayout _images_holder;
	ImageView testImage1, testImage2, testImage3;
	ProgressBar _search_progress_bar;
	static int _NUM_OF_IMAGES = 10;
	String _IMAGE_SIZE = "large";
	ArrayList<String> _thumbNails = new ArrayList<String>();
	static ArrayList<Bitmap> _thumbNailImages = new ArrayList<Bitmap>();
	static int imgCount = 1;
	static int progressCount;
	GridView imageGrid;
	ImageAdapter imgAdapter;

	/*
	 * private static final String LOG_TAG = "Autocomplete:"; private static
	 * final String PLACES_API_BASE =
	 * "https://maps.googleapis.com/maps/api/place"; private static final String
	 * TYPE_AUTOCOMPLETE = "/autocomplete"; private static final String OUT_JSON
	 * = "/json";
	 */

	/*
	 * public class PlacesAutoCompleteAdapter extends ArrayAdapter<String>
	 * implements Filterable { private ArrayList<String> resultList;
	 * 
	 * 
	 * public PlacesAutoCompleteAdapter(Context context, int textViewResourceId)
	 * { super(context, textViewResourceId); }
	 * 
	 * @Override public int getCount() { return resultList.size(); }
	 * 
	 * @Override public String getItem(int index) { return
	 * resultList.get(index); }
	 * 
	 * @Override public Filter getFilter() { Filter filter = new Filter() {
	 * 
	 * @Override protected FilterResults performFiltering(CharSequence
	 * constraint) { FilterResults filterResults = new FilterResults(); if
	 * (constraint != null) { // Retrieve the autocomplete results. resultList =
	 * autocomplete(constraint.toString());
	 * 
	 * // Assign the data to the FilterResults filterResults.values =
	 * resultList; filterResults.count = resultList.size(); } return
	 * filterResults; }
	 * 
	 * @Override protected void publishResults(CharSequence constraint,
	 * FilterResults results) { if (results != null && results.count > 0) {
	 * notifyDataSetChanged(); } else { notifyDataSetInvalidated(); } }}; return
	 * filter; } }
	 * 
	 * 
	 * 
	 * 
	 * private ArrayList<String> autocomplete(String input) { ArrayList<String>
	 * resultList = null;
	 * 
	 * HttpURLConnection conn = null; StringBuilder jsonResults = new
	 * StringBuilder(); try { StringBuilder sb = new
	 * StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
	 * sb.append("?sensor=false&key=" + _APIKEY);
	 * sb.append("&components=country:uk"); sb.append("&input=" +
	 * URLEncoder.encode(input, "utf8"));
	 * 
	 * java.net.URL url = new java.net.URL(sb.toString()); conn =
	 * (HttpURLConnection) url.openConnection(); InputStreamReader in = new
	 * InputStreamReader(conn.getInputStream());
	 * 
	 * // Load the results into a StringBuilder int read; char[] buff = new
	 * char[1024]; while ((read = in.read(buff)) != -1) {
	 * jsonResults.append(buff, 0, read); } } catch (MalformedURLException e) {
	 * Log.e(LOG_TAG, "Error processing Places API URL", e); return resultList;
	 * } catch (IOException e) { Log.e(LOG_TAG,
	 * "Error connecting to Places API", e); return resultList; } finally { if
	 * (conn != null) { conn.disconnect(); } }
	 * 
	 * try { // Create a JSON object hierarchy from the results JSONObject
	 * jsonObj = new JSONObject(jsonResults.toString()); JSONArray
	 * predsJsonArray = jsonObj.getJSONArray("predictions");
	 * 
	 * // Extract the Place descriptions from the results resultList = new
	 * ArrayList<String>(predsJsonArray.length()); for (int i = 0; i <
	 * predsJsonArray.length(); i++) {
	 * resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
	 * } } catch (JSONException e) { Log.e(LOG_TAG,
	 * "Cannot process JSON results", e); }
	 * 
	 * return resultList; }
	 */

	// IMAGE ADAPTER CLASS
	public class ImageAdapter extends BaseAdapter implements OnLongClickListener {

		private Context mContext;
		ArrayList<Bitmap> _thumbNailImages_TEMP;

		public ImageAdapter(Context c, ArrayList<Bitmap> TEMP) {
			this.mContext = c;
			this._thumbNailImages_TEMP = TEMP;
			Log.i("Google Image Search", "Entered ImageAdapter = "
					+ _thumbNailImages_TEMP.size());
			Log.i("Google Image Search", "Context = " + mContext);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			Log.i("Google Image Search", "returned count = "
					+ _thumbNailImages_TEMP.size());
			return _thumbNailImages_TEMP.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return _thumbNailImages_TEMP.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub

			Log.i("Google Image Search", "Position = " + position);
			ImageView imageView;
			LayoutInflater _lInflate = getLayoutInflater();
			arg1 = _lInflate.inflate(R.layout.image_view, null);
			imageView = (ImageView) arg1.findViewById(R.id.imageView1);
			imageView.setImageBitmap(_thumbNailImages_TEMP.get(position));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(5, 5, 5, 5);
			//imageView.setOnTouchListener(new MyTouchListener());
			imageView.setOnLongClickListener(this);
			Log.i("Google Image Search",
					"Image View Creation = " + imageView.getHeight());
			return arg1;
		}

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			
			Bitmap shareImage = v.getDrawingCache();
			/*ByteArrayOutputStream stream = new ByteArrayOutputStream();
			shareImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();*/
			Intent sharingIntent = new Intent(Intent.ACTION_SEND);
			sharingIntent.setType("image/png");
			sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareImage);
			startActivity(Intent.createChooser(sharingIntent,"Share Image using"));
			
			return false;
		}

	}

	// SUBCLASSING FOR HTTP ATASK SYNC

	protected class getImage extends AsyncTask<String, Integer, Bitmap> {

		protected void onPostExecute(Bitmap imagePic) {

			_thumbNailImages.add(imagePic);
			Log.i("Google Image Search", "ThumbnailImagesArraySize = "
					+ _thumbNailImages.size());

			publishProgress(progressCount);

			Log.i("Google Image Search", "Progress count = " + progressCount);

			progressCount = progressCount + 10;

			if (_thumbNailImages.size() == _NUM_OF_IMAGES) {
				imgAdapter = new ImageAdapter(MainActivity.this,
						_thumbNailImages);
				imageGrid = (GridView) findViewById(R.id._IMAGE_GRIDVIEW);
				imageGrid.setAdapter(imgAdapter);

				Log.i("Google Image Search", "ImageAdapter child count = "
						+ imgAdapter.getCount());
				Log.i("Google Image Search", "ImageGrid child count = "
						+ imageGrid.getChildCount());

				/*
				 * testImage1 = (ImageView)findViewById(R.id.testImage1);
				 * testImage2 = (ImageView)findViewById(R.id.testImage2);
				 * testImage3 = (ImageView)findViewById(R.id.testImage3);
				 * testImage1.setImageBitmap(_thumbNailImages.get(0));
				 * testImage2.setImageBitmap(_thumbNailImages.get(1));
				 * testImage3.setImageBitmap(_thumbNailImages.get(2));
				 */

				// _thumbNailImages.removeAll(_thumbNailImages);
				Log.i("Google Image Search",
						"ThumbnailImagesArraySizeAfterLimit = "
								+ _thumbNailImages.size());
			}
		}

		protected void onProgressUpdate(Integer... progress) {

			_search_progress_bar.setProgress(progress[0]);

		}

		@Override
		protected Bitmap doInBackground(String... imageUrl) {
			// TODO Auto-generated method stub

			try {

				HttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(imageUrl[0]);
				HttpResponse httpResponseGet = httpClient.execute(httpGet);
				HttpEntity resEntityGet = httpResponseGet.getEntity();

				imagePic = BitmapFactory
						.decodeStream(resEntityGet.getContent());

				Log.i("Google Image Search",
						"Thumbnail onPostExecute imageWidth = "
								+ imagePic.getWidth());
				Log.i("Google Image Search",
						"Thumbnail onPostExecute imageHeight = "
								+ imagePic.getHeight());

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return imagePic;
		}

	}

	protected class httpRequest extends
			AsyncTask<String, Integer, ArrayList<String>> {

		protected void onPostExecute(ArrayList<String> ThumbnailLinks) {

			Log.i("Google Image Search", "Thumbnail onPostExecute Link = "
					+ ThumbnailLinks);

			for (int i = 0; i < ThumbnailLinks.size(); i++) {
				new getImage().execute(ThumbnailLinks.get(i).toString());
			}
			// CLEARING THUMBNAIL LINKS HOLDER ARRAY...
			ThumbnailLinks.removeAll(ThumbnailLinks);

		}

		protected void onProgressUpdate(Integer... progress) {

			_search_progress_bar.setProgress(progress[0]);

		}

		@Override
		protected ArrayList<String> doInBackground(String... URL) {
			// TODO Auto-generated method stub

			Log.i("Google Image Search", "URL String = " + URL[0]);

			HttpClient httpClient = new DefaultHttpClient();
			// publishProgress(10);
			try {

				HttpGet httpGet = new HttpGet(URL[0]);
				HttpResponse httpResponseGet = httpClient.execute(httpGet);
				HttpEntity resEntityGet = httpResponseGet.getEntity();
				// publishProgress(20);
				if (resEntityGet != null) {
					String responseString = EntityUtils.toString(resEntityGet);

					Log.i("Google Image Search", "Response = " + responseString);
					// publishProgress(50);
					jObject = new JSONObject(responseString);

					_IMAGE_RESULTS = jObject.getJSONArray("items");
					Log.i("Google Image Search", "_results_returned = "
							+ _IMAGE_RESULTS.length());

					for (int i = 0; i < _IMAGE_RESULTS.length(); i++) {

						JSONObject _IMAGE_NAME_OBJ = _IMAGE_RESULTS
								.getJSONObject(i);

						// TO GET PROPER IMAGES
						imageLink = _IMAGE_NAME_OBJ.getString("link");

						// TO GET THUMBNAILS OF IMAGES
						/*
						 * JSONObject tempImageObj = _IMAGE_NAME_OBJ
						 * .getJSONObject("image");
						 * 
						 * imageLink = tempImageObj.getString("thumbnailLink");
						 */

						_thumbNails.add(imageLink);

					}

					// publishProgress(60);
					Log.i("Google Image Search", "Thumbnail Link = "
							+ imageLink);

					// publishProgress(80);

				}

			} catch (Exception e) {
				// TODO: handle exception
				Log.i("Google Image Search Error", e.toString());

			}
			// publishProgress(10);
			return _thumbNails;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		_search_query_box = (EditText) findViewById(R.id._SEARCH_QUERY);
		// _search_query_auto =
		// (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
		_search_button = (Button) findViewById(R.id.SearchButton);
		_search_progress_bar = (ProgressBar) findViewById(R.id._SEARCH_PROGRESS_BAR);

		/*
		 * _search_query_auto.setKeyListener(new KeyListener() {
		 * 
		 * @Override public boolean onKeyUp(View arg0, Editable arg1, int arg2,
		 * KeyEvent arg3) { // TODO Auto-generated method stub
		 * 
		 * Log.i(LOG_TAG, "Entered on key up function"); Log.i(LOG_TAG,
		 * "Entered on key ="+_search_query_auto.getText().toString());
		 * autocomplete(_search_query_auto.getText().toString()); return true; }
		 * 
		 * @Override public boolean onKeyOther(View arg0, Editable arg1,
		 * KeyEvent arg2) { // TODO Auto-generated method stub return false; }
		 * 
		 * @Override public boolean onKeyDown(View arg0, Editable arg1, int
		 * arg2, KeyEvent arg3) { // TODO Auto-generated method stub return
		 * false; }
		 * 
		 * @Override public int getInputType() { // TODO Auto-generated method
		 * stub return 0; }
		 * 
		 * @Override public void clearMetaKeyState(View arg0, Editable arg1, int
		 * arg2) { // TODO Auto-generated method stub
		 * 
		 * } });
		 * 
		 * _search_query_auto.setAdapter(new PlacesAutoCompleteAdapter(this,
		 * R.layout.autolist));
		 */

		_search_button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				_search_query = _search_query_box.getText().toString();

				URL = "https://www.googleapis.com/customsearch/v1?q="
						+ _search_query + "&num=" + _NUM_OF_IMAGES + "&cx="
						+ _cx + "&fileType=png&searchType=image&imgSize="
						+ _IMAGE_SIZE + "&key=" + _APIKEY + "&alt=json";

				progressCount = 100 / _NUM_OF_IMAGES;
				_thumbNailImages.removeAll(_thumbNailImages);
				Log.i("Google Image Search",
						"ThumbnailImagesArraySizeAfterClick = "
								+ _thumbNailImages.size());

				new httpRequest().execute(URL);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	

}
