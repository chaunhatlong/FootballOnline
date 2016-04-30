package com.example.chaunhatlong.footballonline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;


public class MainActivity extends Activity {

    EditText usernameText;
    EditText passwordText;
    Button loginBtn;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameText = (EditText)findViewById(R.id.username);
        passwordText = (EditText)findViewById(R.id.password);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();
        addListener();

        final TextView textureView = (TextView)findViewById(R.id.createAccount);
        textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(MainActivity.this, register_account.class);
                startActivity(itent);

            }
        });


    }


    private void addListener(){
//        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(usernameText.getWindowToken(), 0);
//        imm.hideSoftInputFromWindow(passwordText.getWindowToken(), 0);

        loginBtn = (Button)findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                String sqlPass = databaseHelper.getSinlgeEntry(username);
                if(password.equals(sqlPass)){
                    onLoginSuccess();
                    Intent itent = new Intent(MainActivity.this, TrangChu.class);
                    startActivity(itent);
                }else{
                    onLoginFailed();
                    usernameText.setText("");
                    passwordText.setText("");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
    }

    //Kiem tra Validate Email vs Password
    public boolean checkValidate(){
        boolean valid = true;

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (username.isEmpty() || username.length() < 4 || username.length() > 20) {
            usernameText.setError("Nhap Username lai cho dung!");
        } else {
            usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("Mat khau bat buoc dai tu 4 den 10 ky tu");
            valid = false;
        }

        return valid;
    }

    public void onLoginFailed(){
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginBtn.setEnabled(true);
    }

    public void onLoginSuccess(){
        Toast.makeText(getBaseContext(), "Login successed", Toast.LENGTH_LONG).show();
        loginBtn.setEnabled(true);
        finish();
    }



}
