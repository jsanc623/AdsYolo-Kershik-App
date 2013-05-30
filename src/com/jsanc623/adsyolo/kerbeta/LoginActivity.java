package com.jsanc623.adsyolo.kerbeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.appglu.android.AppGlu;
import com.appglu.android.AppGluSettings;

import android.util.Log;
import android.widget.EditText;
import android.content.Intent;

public class LoginActivity extends Activity {
	Button loginButton;
	EditText uname_tmp;
	EditText passw_tmp;
	String username;
	String password;
    TextView outputText;
	
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
	        GetXMLTask task = new GetXMLTask();
             switch(v.getId()){
                 case R.id.login_button: {
					uname_tmp = (EditText)findViewById(R.id.username);
					passw_tmp = (EditText)findViewById(R.id.password);
					
					username = uname_tmp.getText().toString();
					password = passw_tmp.getText().toString();

					String URL = "http://pixls.me/adsyolo/login.php?username=" + username + "&password=" + password;
					
					AsyncTask<String, String, String> userLoggedInResult = task.execute(new String[] { URL });
					
					String userLoggedIn = "";
					try {
						userLoggedIn = userLoggedInResult.get();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
					
					Log.d("itsokall", userLoggedIn.toString());
					
					if(userLoggedIn != null && !userLoggedIn.toString().isEmpty()){
						if(userLoggedIn.equals("fail")){
							Intent myIntent = new Intent(LoginActivity.this, SignupActivity.class);
							LoginActivity.this.startActivity(myIntent);
						} else {
							Log.d("itsokall", "in userloggedin comparison, ok");
							Intent myIntent = new Intent(LoginActivity.this, OffersActivity.class);
							LoginActivity.this.startActivity(myIntent);
						}
					}
                 }
             }
	    }
	};
	
	private class GetXMLTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
                output = getOutputFromUrl(url);
            }
            return output;
        }
 
        private String getOutputFromUrl(String url) {
            StringBuffer output = new StringBuffer("");
            try {
                InputStream stream = getHttpConnection(url);
                BufferedReader buffer = new BufferedReader(
                        new InputStreamReader(stream));
                String s = "";
                while ((s = buffer.readLine()) != null)
                    output.append(s);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return output.toString();
        }
 
        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString) throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
 
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();
 
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
 
        @Override
        protected void onPostExecute(String output) {
			outputText.setText(output);
        }
	}
}