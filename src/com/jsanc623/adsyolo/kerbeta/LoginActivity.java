package com.jsanc623.adsyolo.kerbeta;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.appglu.AsyncCallback;
import com.appglu.Row;
import com.appglu.Rows;
import com.appglu.android.AppGlu;
import com.appglu.android.AppGluSettings;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class LoginActivity extends Activity {
	Button loginButton;
	EditText uname_tmp;
	EditText passw_tmp;
	String username;
	String password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Log.d("itsokall", "post setContentView");
  
	    Button loginButton = (Button) findViewById(R.id.login_button);
	    loginButton.setOnClickListener(onClickListener);
	    
		Log.d("itsokall", "post .setOnClickListener");
		
		AppGluSettings settings = new AppGluSettings("fS3jywqBB3Iy27P", "LUbdT5GHTDmn14JYvUtzksFMttGJCU", "staging");
		AppGlu.initialize(this, settings);
		 
		Log.d("itsokall", "post AppGlu init");
	}
	
	public OnClickListener onClickListener = new OnClickListener() {
	    public void onClick(final View v) {
			 Log.d("itsokall", "in onClick pre switch");
             switch(v.getId()){
                 case R.id.login_button: {
             		Log.d("itsokall", "in switch, getting username and password");
					uname_tmp = (EditText)findViewById(R.id.username);
					passw_tmp = (EditText)findViewById(R.id.password);
					
					username = uname_tmp.getText().toString();
					password = passw_tmp.getText().toString();

					Log.d("itsokall", username);
					Log.d("itsokall", password);

					Log.d("itsokall", "in switch,  building urlParameters");
					
					String urlParameters = null;
					try {
						urlParameters = "username=" + URLEncoder.encode(username, "UTF-8") +
						                "&password=" + URLEncoder.encode(password, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}

					/*Log.d("itsokall", "AppGlu CrudAPI"); 
					
					Rows result = AppGlu.crudApi().readAll("Offers");
					Integer totalRows = result.getTotalRows();
					List<Row> rows = result.getRows();
					
					// error here
					AppGlu.crudApi().read("Offers", "1");
					
					AppGlu.crudApi().readInBackground("Offers", "1", new AsyncCallback<Row>() {
						public void onResult(Row row) {
						  Log.d("itsokall", "in onResult!");
						  Log.d("itsokall", row.toString());
						}
					});*/
					
					Log.d("itsokall", "init Retriever Retriever = new Retriever. Then executePost.");
					
					Retriever Retriever = new Retriever();
					String userLoggedIn = Retriever.executePost("http://pixls.me/adsyolo/login.php", username, password);
					
					Log.d("itsokall", "userLoggedIn" + userLoggedIn);
					Log.d("itsokall", "entering userloggedin comparison");
					if(userLoggedIn != null && !userLoggedIn.isEmpty()){
						if(userLoggedIn.equals("fail")){
							Log.d("itsokall", "in userloggedin comparaison, ok!");
							Intent myIntent = new Intent(LoginActivity.this, SignupActivity.class);
							LoginActivity.this.startActivity(myIntent);
						} else {
							Log.d("itsokall", "in userloggedin comparison, fail");
							Intent myIntent = new Intent(LoginActivity.this, OffersActivity.class);
							LoginActivity.this.startActivity(myIntent);
						}
						Log.d("itsokall", "post everything in switch onCreate()");
					} else {
						Log.d("itsokall", "empty!"); 
					}
                 }
             }
	    }
	};
}


class Retriever extends AsyncTask<String, String, String> {
	
	HttpResponse response;

	public String executePost(String targetURL, String username, String password){

		Log.d("itsokall", "in executePost " + targetURL);
		
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(targetURL);

		try {
			Log.d("itsokall", "inTryBlock");
		    // Add your data
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		    nameValuePairs.add(new BasicNameValuePair("username", username));
		    nameValuePairs.add(new BasicNameValuePair("password", password));
		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		    
		    Log.d("itsokall", "executing!");
		    
		    // Execute HTTP Post Request
		    response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
		    // TODO Auto-generated catch block
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		}

		return response.toString();
		
	    /*URL url;
	    HttpURLConnection connection = null;  
	    try {
	      Log.d("itsokall", "in executePost try-catch statement");
	      
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				
	      connection.setRequestProperty("Content-Length", "" + 
	               Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
				
	      connection.setUseCaches(false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);
	      

	      Log.d("itsokall", "engaging connection");
	      
	      //Send request
	      DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
	      
	      Log.d("itsokall", "connection engaged, writing to output stream");
	      
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();
	      
	      Log.d("itsokall", "dataoutputstream closed, getting response");

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      Log.d("itsokall", "returning response: " + response.toString());

			Log.d("itsokall", "Disengaging connection");
			connection.disconnect(); 
			
	      return response.toString();
	    } catch (Exception e) {
	      e.printStackTrace();
	      return null;
	    }*/
	  }

    protected void onPostExecute() {
    	Log.d("itsokall", "IN ONPOSTEXECUTE");
        // TODO: check this.exception
        // TODO: do something with the feed
    }

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
 }
