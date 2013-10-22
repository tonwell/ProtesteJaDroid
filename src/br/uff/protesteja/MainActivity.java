package br.uff.protesteja;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	@Override
	protected void onStart() {
		super.onStart();
	    ConnectivityManager connMgr = (ConnectivityManager) 
	            getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	        if (networkInfo == null || !networkInfo.isConnected()) {
	        	Toast.makeText(getApplicationContext(), "Seu aparelho não está on the line!", Toast.LENGTH_LONG).show();
	        	finish();
	        }
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void postar(View v){
		Intent i = new Intent(MainActivity.this, SendPostActivity.class);
		startActivity(i);
	}
	
	public void ver(View v){
		Intent i = new Intent(MainActivity.this, ShowPostsActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

}
