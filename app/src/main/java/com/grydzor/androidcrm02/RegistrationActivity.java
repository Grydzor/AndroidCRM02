package com.grydzor.androidcrm02;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.grydzor.androidcrm02.controller.PostController;
import com.grydzor.androidcrm02.form.RegisterData;
import com.grydzor.androidcrm02.settings.Settings;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private EditText name;
    private EditText surname;
    private EditText phone;
    private EditText adress;
    private TextView alert;
    private ProgressBar progressBar;
    private Button regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = (EditText) findViewById(R.id.email_reg);
        password = (EditText) findViewById(R.id.pass_reg);
        confirmPassword = (EditText) findViewById(R.id.pass_conf_reg);
        name = (EditText) findViewById(R.id.name_reg);
        surname = (EditText) findViewById(R.id.surname_reg);
        phone = (EditText) findViewById(R.id.phone_reg);
        adress = (EditText) findViewById(R.id.adress_reg);
        alert = (TextView) findViewById(R.id.alert_reg);
        progressBar = (ProgressBar) findViewById(R.id.progress_reg);
        regButton = (Button) findViewById(R.id.button_reg);
    }

    public void regButton(View view){
        if (email.getText().toString().equals("")
                || password.getText().toString().equals("")
                || confirmPassword.getText().toString().equals("")
                || name.getText().toString().equals("")
                || surname.getText().toString().equals("")
                || phone.getText().toString().equals("")
                || adress.getText().toString().equals("")){
            alert.setVisibility(View.VISIBLE);
            alert.setText("Please fill in the fields!");
            return;
        }
        if(!password.getText().toString().equals(confirmPassword.getText().toString())){
            alert.setVisibility(View.VISIBLE);
            alert.setText("Passwords do not match!");
        }

        RegisterData data = checkFields();
        UserRegTask regTask = new UserRegTask(data);
        regTask.execute();

    }

    private RegisterData checkFields(){
        RegisterData data = new RegisterData(email.getText().toString(),
                password.getText().toString(),
                confirmPassword.getText().toString(),
                name.getText().toString(),
                surname.getText().toString(),
                phone.getText().toString(),
                adress.getText().toString());
        return data;
    }

    private class UserRegTask extends AsyncTask<Void, Void, String> {

        private final RegisterData data;

        UserRegTask(RegisterData data) {
            this.data = data;
        }

        @Override
        protected void onPreExecute(){
            alert.setVisibility(View.INVISIBLE);
            alert.setText("");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            Gson gson = new Gson();
            PostController controller = new PostController();
            String request = null;


            try {
                request = controller.post(Settings.fullUrl + "jsonreg", gson.toJson(data));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return request;
        }

        @Override
        protected void onPostExecute(String str){
            progressBar.setVisibility(View.INVISIBLE);
            if(str == null){
                alert.setVisibility(View.VISIBLE);
                alert.setText("Cannot conect to server!");
                return;
            }

            JSONObject json = null;
            try {
                json = new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
                alert.setVisibility(View.VISIBLE);
                alert.setText("Server Error");
                return;
            }
            boolean b = false;
            try {
                b = json.getBoolean("answer");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(b){
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Registration completed successfully", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(intent);
            } else {
                alert.setVisibility(View.VISIBLE);
                alert.setText("Error fields");
            }
        }

    }
}
