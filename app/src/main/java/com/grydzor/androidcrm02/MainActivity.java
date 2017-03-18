package com.grydzor.androidcrm02;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.grydzor.androidcrm02.controller.PostController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private TextView alert;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = (EditText) findViewById(R.id.email_login);
        password = (EditText) findViewById(R.id.password_login);
        alert = (TextView) findViewById(R.id.alert_login);
        signIn = (Button) findViewById(R.id.sign_in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void signIn(View view){
        if ((email == null)||(password == null)){
            alert.setVisibility(View.VISIBLE);
            alert.setText("Please fill in the fields!");
            return;
        }
        UserLoginTask loginTask = new UserLoginTask(email.getText().toString(), password.getText().toString());
        loginTask.execute();


    }

    private class UserLoginTask extends AsyncTask<Void, Void, String> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected void onPreExecute(){
            alert.setVisibility(View.INVISIBLE);
            alert.setText("");
        }

        @Override
        protected String doInBackground(Void... params) {
            JSONObject jsonObject = new JSONObject();
            PostController controller = new PostController();
            String request = null;

            try {
                jsonObject.accumulate("email", mEmail);
                jsonObject.accumulate("password", mPassword);
            } catch (JSONException e) {e.printStackTrace();}

            try {
                request = controller.post(PostController.url,jsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return request;
        }

        @Override
        protected void onPostExecute(String str){
            JSONObject json = new JSONObject();
            boolean b = false;
            try {
                b = json.getBoolean("answer");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(b){
                Intent intent = new Intent(MainActivity.this, BackOfficeActivity.class);
                startActivity(intent);
            } else {
                alert.setVisibility(View.VISIBLE);
                alert.setText("Wrong email or password!");
            }
        }

    }
}
