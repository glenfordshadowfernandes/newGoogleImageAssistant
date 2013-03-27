package com.example.googleimagesearch;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText _search_query_box;
	String _search_query;
	String _APIKEY = "AIzaSyBYHDjXAWUoeeIHraRvdenfpUUXQL3c56w";
	String _cx = "002151139267678842292:vfsfogsw2da";
	 
	
	//SUBCLASSING FOR HTTP ATASK SYNC
	
	protected class httpRequest extends AsyncTask<String, Void, Bitmap>{
		
		
		protected void onPostExecute(Bitmap placePhoto) {
			
			
	    }

		@Override
		protected Bitmap doInBackground(String... URL) {
			// TODO Auto-generated method stub
			
			
			
			return null;
		}
		
		
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_search_query_box = (EditText)findViewById(R.id._SEARCH_QUERY);
		_search_query = _search_query_box.getText().toString(); 
		
		String URL = "https://www.googleapis.com/customsearch/v1?q="+_search_query+"&cx="+_cx+"&fileType=png&key="+_APIKEY+"&alt=json";
		
		//new httpRequest().execute(URL);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
