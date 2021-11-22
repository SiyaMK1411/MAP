package com.miniproject.projectc2s;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String DB_NAME = "ContactsDB";

    private static final String CONTACTS_TABLE = "contacts";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String NUMBER = "number";


    public DBHandler(Context context) {
        super(context, NAME, null, VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE =
                "CREATE TABLE " + CONTACTS_TABLE
                        +"(" + ID + "int PRIMARY KEY autoincrement,"
                        + NAME + "TEXT,"
                        + NUMBER+ "TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE "+ CONTACTS_TABLE;
        db.execSQL(sql);
        onCreate(db);
    }


    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME, contact.getName());
        values.put(NUMBER, contact.getNumber());
      //  db.insert(CONTACTS_TABLE,null,values);

        long success=db.insert(CONTACTS_TABLE,null,values);
        Log.d("cou nt_insert","addContact"+success);
        db.close();
    }

    public  Contact getContact(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                CONTACTS_TABLE,
                new String[]{ID,NAME,NUMBER},
                ID + "=?",
                new String[]{String.valueOf(id)},
                null,null,null,null
        );

        Contact contact;

        if (cursor != null){
            cursor.moveToFirst();
            contact = new Contact(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            return contact;
        }else{
            return  null;
        }
    }

    public List<Contact> getAllContacts(){
        SQLiteDatabase db = getReadableDatabase();
        List<Contact> contacts = new ArrayList<>();
        String query = "SELECT * FROM " + CONTACTS_TABLE;

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                Contact contact = new Contact();

                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setNumber(cursor.getString(2));
                contacts.add(contact);
            }
            while (cursor.moveToNext());
        }
        return contacts;
    }

    public int updateContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME,contact.getName());
        values.put(NUMBER,contact.getNumber());

        return  db.update(CONTACTS_TABLE,values,
                ID+" = ?",
                new String[] {String.valueOf(contact.getId())}
        );
    }

    public void deleteContact(Contact contact){
        SQLiteDatabase db = getWritableDatabase();

        db.delete(
                CONTACTS_TABLE,
                ID+" = ?",
                new String[] {String.valueOf(contact.getId())}
        );
        db.close();
    }

    public Cursor getContactCount(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + CONTACTS_TABLE;
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }


}

