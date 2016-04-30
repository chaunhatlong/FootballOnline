package com.example.chaunhatlong.footballonline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

public class register_account extends Activity {

    EditText usernameEt, passEt, confirmPassEt;
    Button btnRegister;
    DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register_account);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDatabase();

        final TextView textureView = (TextView)findViewById(R.id.login_activity);
        textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(register_account.this, MainActivity.class);
                startActivity(itent);

            }
        });

        try{
            usernameEt = (EditText)findViewById(R.id.username);
            passEt = (EditText) findViewById(R.id.password);
            confirmPassEt = (EditText) findViewById(R.id.confirmpassword);

            btnRegister = (Button)findViewById(R.id.btnCreate);
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userName = usernameEt.getText().toString();
                    String password = passEt.getText().toString();
                    String confirmPass = confirmPassEt.getText().toString();

                    if("".equals(userName) || "".equals(password) || "".equals(confirmPass)){
                        Toast.makeText(register_account.this, "Please fill Validate", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(!password.equals(confirmPass)){
                        Toast.makeText(register_account.this, "Password does not match", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        databaseHelper.addAccount(userName, password);
                        Toast.makeText(register_account.this, "Account Successfully Created", Toast.LENGTH_SHORT).show();
                        Intent itent = new Intent(register_account.this, MainActivity.class);
                        startActivity(itent);
                        finish();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
