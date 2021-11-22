package com.miniproject.projectc2s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {

    Context context;
    private DBHandler dbHandler;

    private ListView contactsList;
    private EditText searchText;
    private Button add;

    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        context = this;
        dbHandler = new DBHandler(this);

        Log.d("count_c", "Oncreate" + dbHandler.getContactCount());


        contactsList = (ListView) findViewById(R.id.contact_List);
//        searchText = (EditText)findViewById(R.id.search_text);
//        search = (Button)findViewById(R.id.search);
        add = (Button) findViewById(R.id.add);

        contacts = new ArrayList<>();
        contacts = dbHandler.getAllContacts();

        String[] namesArray = new String[contacts.size()];

        dbHandler.getContactCount();
        for (int x = 0; x < contacts.size(); x++) {
            namesArray[x] = contacts.get(x).getName();
            Log.d("array_names", "onCreate:" + namesArray[x] + contacts.size());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, namesArray);
        contactsList.setAdapter(adapter);


        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> AdapterView, View view, int i, long id) {
                Contact contact = contacts.get(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(contact.getName())
                        .setMessage(
                                contact.getNumber())
                        .show();
            }
        });

//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
//
//            }
//        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddContact.class);
                startActivity(intent);
            }

        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        return true;

                    case R.id.law:
                        startActivity(new Intent(getApplicationContext(),
                                laws.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.map:
                        startActivity(new Intent(getApplicationContext(),
                                maps.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.video:
                        startActivity(new Intent(getApplicationContext(),
                                video.class));
                        overridePendingTransition(0, 0);
                        return true;


                    case R.id.helpline:
                        startActivity(new Intent(getApplicationContext(),
                                helpline.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });


    }


}