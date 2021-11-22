package com.miniproject.projectc2s;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.content.Context;
import android.os.Bundle;

public class AddContact extends AppCompatActivity {
    Context context;
    DBHandler dbHandler;
    EditText et_name, et_number;
    Button add;
    //  private Object DBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        context = this;
        dbHandler = new DBHandler(context);

        et_name = (EditText) findViewById(R.id.name);
        et_number = (EditText) findViewById(R.id.number);

        add = (Button) findViewById(R.id.add);

        String name = et_name.getText().toString();
        String number = et_number.getText().toString();

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String number = et_number.getText().toString();

                if (name.trim().length()>0 &&  number.trim().length()>0 ) {
                    Contact contact = new Contact(name,number);
                    dbHandler.addContact(contact);

                    startActivity(new Intent(context, NavigationActivity.class));
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Please fill all the fields")
                            .setNegativeButton("OK", null)
                            .show();
                }
            }
            });

    }

}



