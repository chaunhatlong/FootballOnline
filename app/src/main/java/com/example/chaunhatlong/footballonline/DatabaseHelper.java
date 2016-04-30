package com.example.chaunhatlong.footballonline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;


public class DatabaseHelper extends SQLiteAssetHelper {

    //static final String DB_PATH = "/data/data/com.example.chaunhatlong.footballonline/databases/";
    static String DB_PATH;
    static final String DB_NAME = "database.sqlite";
    SQLiteDatabase mDatabase;
    Context mContext;
    static double lati, longi;
    static String field_name, header_name, header_email;
    int userID;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);
        mContext = context;
        DB_PATH = context.getFilesDir().getPath();
        createDatabase();
    }

    public void createDatabase() {
        if(checkDatabase()){
            openDatabase();
        }else{
            copyDatabase();
        }
    }

    public void copyDatabase() {
        try{
            InputStream is = mContext.getAssets().open(DB_NAME );
            String outName = DB_PATH + DB_NAME;
            OutputStream os = new FileOutputStream(outName);
            byte buffer[] = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0 ){
                os.write(buffer, 0 , length);

            }
            os.flush();
            os.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void openDatabase(){

        mDatabase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }


    public void closeDatabase(){
        if(mDatabase != null)
            mDatabase.close();
    }

    //check username
    public String getSinlgeEntry(String userName) {
        Cursor cursor = mDatabase.query("user_profiles", null, " username=?",
                new String[]{userName}, null, null, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("password"));
        cursor.close();
        return password;
    }

    //get location google map
    public void getLocation(GoogleMap mleMap){
        LatLng field;
        Cursor cursor = mDatabase.rawQuery("SELECT field_name, latitude, longitude FROM fields", null);

        if(cursor != null){
            if (cursor.moveToFirst()){
                do{
                    lati    = Double.parseDouble((cursor.getString(cursor.getColumnIndex("latitude"))));
                    longi   = Double.parseDouble((cursor.getString(cursor.getColumnIndex("longitude"))));
                    field_name = cursor.getString(cursor.getColumnIndex("field_name"));

                    field = new LatLng(lati, longi);
                    mleMap.addMarker(new MarkerOptions().position(field).title(field_name));
                    mleMap.moveCamera(CameraUpdateFactory.newLatLng(field));
                    mleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                }while (cursor.moveToNext());
            }
        }
        closeDatabase();
    }

    //Get name user_profiles
    public void getName(String username){
        Cursor cursor = mDatabase.rawQuery("SELECT name FROM user_profiles WHERE username='"+username+"'",null);
        if (cursor != null){
            if(cursor.moveToFirst()){
                do{
                    header_name = cursor.getString(cursor.getColumnIndex("name"));
                }while (cursor.moveToNext());
            }
        }
        closeDatabase();
    }

    //Get mail user_profiles
    public void getEmail(String username){
        Cursor cursor = mDatabase.rawQuery("SELECT email FROM user_profiles WHERE username='"+username+"'",null);
        if (cursor != null){
            if(cursor.moveToFirst()){
                do{
                    header_email = cursor.getString(cursor.getColumnIndex("email"));
                }while (cursor.moveToNext());
            }
        }
        closeDatabase();
    }

    //Register Account
    public void addAccount(String userName,String password)
    {
        openDatabase();
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("username", userName);
        newValues.put("password",password);
        newValues.put("email","Null");
        newValues.put("phone_number","Null");
        newValues.put("name","Null");
        newValues.put("district_id",10);

        // Insert the row into your table
        mDatabase.insert("user_profiles", null, newValues);

//        String sql = "INSERT INTO u" +
//                "ser_profiles(username, password, email, phone_number, name) VALUES (?, ?, NULL, NULL, NULL)";
//        Log.d("SQL: ", sql);
//        openDatabase();
//
//        SQLiteStatement statement = mDatabase.compileStatement(sql);
//        statement.bindString(1, userName);
//        statement.bindString(2, password);
//        statement.executeInsert();

        closeDatabase();

    }

    //Register Field
    public void addField(String username, String fieldname, String timeStart, String timeEnd, String daymatch){
        Cursor c1 = mDatabase.query("user_profiles", null, " username=?",
                new String[]{username}, null, null, null);
        String userName = c1.getColumnName(c1.getColumnIndex("username"));

        Cursor c2 = mDatabase.query("fields", null, " field_name=?",
                new String[]{fieldname}, null, null, null);
        ContentValues contentValues = new ContentValues();

    }

    //get fields
    public String[] getField_name(){
        Cursor cursor = mDatabase.rawQuery("SELECT field_name FROM fields", null);
        String[] field_name = new String[cursor.getCount()];
        int i=0;
        while (cursor.moveToNext()){
            String field = cursor.getString((cursor.getColumnIndex("field_name")));
            field_name[i] = field;
            i++;
        }
        cursor.close();
        return  field_name;
    }

    //Get user ID
    public int getMaxUserID() {
        int id;
        Cursor cursor = mDatabase.rawQuery("SELECT MAX(user_id) FROM user_profiles",null);
        id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("user_id")));
        cursor.close();
        return id;
    }


    public boolean checkDatabase() {
        File f = mContext.getDatabasePath(DB_NAME);
        return f.exists();
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public DatabaseHelper(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, storageDirectory, factory, version);
    }
}
