package com.example.googleimagesearch;




import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;


public class MainActivity extends Activity {

	EditText _search_query_box;
	String _search_query;
	String _APIKEY = "AIzaSyBYHDjXAWUoeeIHraRvdenfpUUXQL3c56w";
	String _cx = "002151139267678842292:vfsfogsw2da";
	Button _search_button;
	String URL;
	JSONObject jObject;
	JSONArray _IMAGE_RESULTS;
	String imageLink;
	Bitmap imagePic;
	LinearLayout _images_holder;
	ImageView testImage1,testImage2,testImage3;
	ProgressBar _search_progress_bar;
	String _NUM_OF_IMAGES = "5";
	String _IMAGE_SIZE = "large";
	ArrayList<String> _thumbNails = new ArrayList<String>();
	static int imgCount = 1;
	
	
	//SUBCLASSING FOR HTTP ATASK SYNC
	
	protected class getImage extends AsyncTask<String, Integer, Bitmap>{

		
		
		protected void onPostExecute(Bitmap imagePic) {
			
			 // Log.i("Google Image Search" ,"Thumbnail onPostExecute Link = "+ThumbnailLink);
				if(imgCount==1)
				{
					testImage1 = (ImageView)findViewById(R.id.testImage1);
					testImage1.setImageBitmap(imagePic);
				}
				if(imgCount==2){
					testImage2 = (ImageView)findViewById(R.id.testImage2);
					testImage2.setImageBitmap(imagePic);
				}
				if(imgCount==3){
					testImage3 = (ImageView)findViewById(R.id.testImage3);
					testImage3.setImageBitmap(imagePic);
					imgCount=0;
				}
				
				imgCount++;
			  
	    }
		
		@Override
		protected Bitmap doInBackground(String... imageUrl) {
			// TODO Auto-generated method stub
			
			 try {
				 
				  HttpClient httpClient = new DefaultHttpClient();
				  HttpGet httpGet = new HttpGet(imageUrl[0]);
				  HttpResponse httpResponseGet = httpClient.execute(httpGet);
				  HttpEntity resEntityGet = httpResponseGet.getEntity();
				  
				  imagePic = BitmapFactory.decodeStream(resEntityGet.getContent());	  
				  
				  Log.i("Google Image Search" ,"Thumbnail onPostExecute imageWidth = "+imagePic.getWidth());
				  Log.i("Google Image Search" ,"Thumbnail onPostExecute imageHeight = "+imagePic.getHeight());
				 
				  
				  
				  
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
	
	
	protected class httpRequest extends AsyncTask<String, Integer, ArrayList<String>>{
		
		
		protected void onPostExecute(ArrayList<String> ThumbnailLinks) {
			
			  Log.i("Google Image Search" ,"Thumbnail onPostExecute Link = "+ThumbnailLinks);
			
			  for(int i = 0; i<ThumbnailLinks.size();i++){
				  new getImage().execute(ThumbnailLinks.get(i).toString());  
			  }
			  
			  
	    }

		  protected void onProgressUpdate(Integer... progress) {
			  	
			  _search_progress_bar.setProgress(progress[0]);
		        
		  }
		  
		 
		  
		@Override
		protected ArrayList<String> doInBackground(String... URL) {
			// TODO Auto-generated method stub
			
			Log.i("Google Image Search", "URL String = "+URL[0]);
			
			 HttpClient httpClient = new DefaultHttpClient();
			    publishProgress(10);
		        try {
		        	
		        	HttpGet httpGet = new HttpGet(URL[0]);
				     HttpResponse httpResponseGet = httpClient.execute(httpGet);
				     HttpEntity resEntityGet = httpResponseGet.getEntity(); 
				     publishProgress(20);
				           if (resEntityGet != null) 
				           {
				            String responseString = EntityUtils.toString(resEntityGet);             
				                         
				            Log.i("Google Image Search" ,"Response = "+responseString);
				            publishProgress(50);
				            jObject = new JSONObject(responseString);
				            
				            _IMAGE_RESULTS = jObject.getJSONArray("items");
				            Log.i("Google Image Search" ,"_results_returned = "+_IMAGE_RESULTS.length());
				            
				            for(int i=0;i<_IMAGE_RESULTS.length();i++){
				            	
				            	JSONObject _IMAGE_NAME_OBJ =  _IMAGE_RESULTS.getJSONObject(i);
						           
					            JSONObject tempImageObj = _IMAGE_NAME_OBJ.getJSONObject("image");
					            
					            imageLink = tempImageObj.getString("thumbnailLink");
					            
					            _thumbNails.add(imageLink);
				            	
				            }
				        
				            
				            publishProgress(60);
				            Log.i("Google Image Search" ,"Thumbnail Link = "+imageLink);
				            
				            publishProgress(80);
				            
				           }
				           
				} catch (Exception e) {
					// TODO: handle exception
					Log.i("Google Image Search Error", e.toString());
					
				}
		        publishProgress(100);
			return _thumbNails;
		}
		
		
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_search_query_box = (EditText)findViewById(R.id._SEARCH_QUERY); 
		_search_button = (Button)findViewById(R.id.SearchButton);
		_search_progress_bar = (ProgressBar)findViewById(R.id._SEARCH_PROGRESS_BAR);
		
		
		
		_search_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				_search_query = _search_query_box.getText().toString();
				URL = "https://www.googleapis.com/customsearch/v1?q="+_search_query+"&num="+_NUM_OF_IMAGES+"&cx="+_cx+"&fileType=png&searchType=image&imgSize="+_IMAGE_SIZE+"&key="+_APIKEY+"&alt=json";
				
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
