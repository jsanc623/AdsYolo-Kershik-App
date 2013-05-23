package com.jsanc623.adsyolo.kerbeta;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.appglu.android.AppGlu;
import com.appglu.android.AppGluSettings;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

@SuppressWarnings("unused")
public class LoginActivity extends Activity {
	Button loginButton;
	EditText username;
	EditText password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
  
	    Button loginButton = (Button) findViewById(R.id.login_button);
	    loginButton.setOnClickListener(onClickListener);
		
		AppGluSettings settings = new AppGluSettings("fS3jywqBB3Iy27P", "LUbdT5GHTDmn14JYvUtzksFMttGJCU", "staging");
		AppGlu.initialize(this, settings);
	}
	
	public OnClickListener onClickListener = new OnClickListener() {
	    public void onClick(final View v) {
             switch(v.getId()){
                 case R.id.login_button: {
					username = (EditText)findViewById(R.id.username);
					password = (EditText)findViewById(R.id.password);
					
					if(username.toString() == "jsanc623" && password.toString() == "lonewolf89"){
						Intent myIntent = new Intent(LoginActivity.this, OffersActivity.class);
						LoginActivity.this.startActivity(myIntent);
					} else {
						Intent myIntent = new Intent(LoginActivity.this, SignupActivity.class);
						LoginActivity.this.startActivity(myIntent);
					}
					
					/*String urlParameters = null;
					try {
						urlParameters = "username=" + URLEncoder.encode(username.toString(), "UTF-8") +
						                "&password=" + URLEncoder.encode(password.toString(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					
					Retriever Retriever = new Retriever();
					String userLoggedIn = Retriever.executePost("http://pixls.me/adsyolo/login.php", urlParameters);
					
					if(userLoggedIn == "fail"){
						Intent myIntent = new Intent(LoginActivity.this, SignupActivity.class);
						LoginActivity.this.startActivity(myIntent);
					} else {
						Intent myIntent = new Intent(LoginActivity.this, OffersActivity.class);
						LoginActivity.this.startActivity(myIntent);
					}*/
                 }
             }
	    }
	};
}


class Retriever extends AsyncTask<String, String, String> {

	public String executePost(String targetURL, String urlParameters){
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				
	      connection.setRequestProperty("Content-Length", "" + 
	               Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
				
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();

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
	      return response.toString();
	    } catch (Exception e) {
	      e.printStackTrace();
	      return null;
	    } finally {
	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }

    protected void onPostExecute() {
        // TODO: check this.exception 
        // TODO: do something with the feed
    }

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
 }
