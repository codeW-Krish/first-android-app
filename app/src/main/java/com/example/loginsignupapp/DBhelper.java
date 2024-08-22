package com.example.loginsignupapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Login.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "Userinfo";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE_NO = "phone_no";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_COF_PASSWORD = "cof_password";

    private static final String CREATE_QUERY =
            "CREATE TABLE "+TABLE_NAME+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    COLUMN_NAME+" TEXT,"+
                    COLUMN_EMAIL+" TEXT UNIQUE,"+
                    COLUMN_PHONE_NO+" TEXT UNIQUE,"+
                    COLUMN_PASSWORD+" TEXT ,"+
                    COLUMN_COF_PASSWORD+" TEXT);";

    //private static final String Query = "DROP TABLE IF EXISTS"+TABLE_NAME;

    public DBhelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB,int i,int i1) {
        DB.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(DB);
    }

    public Boolean insert_user_to_db(String name,String email,String phone_no,String password,String cof_password){
//        SQLiteDatabase MyDB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name",name);
//        contentValues.put("email",email);
//        contentValues.put("phone_no",phone_no);
//        contentValues.put("password",password);
//        contentValues.put("cof_password",cof_password);
//
//        long result = MyDB.insert("Userinfo",null,contentValues);
//
//        return result != -1; // will return true if the user info inserted successfully and if there is any error than the result value is = -1 thus it will return false

        SQLiteDatabase MyDB = null;
        boolean isInserted = false;

        try {
            MyDB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME, name);
            contentValues.put(COLUMN_EMAIL, email);
            contentValues.put(COLUMN_PHONE_NO, phone_no);
            contentValues.put(COLUMN_PASSWORD, password);
            contentValues.put(COLUMN_COF_PASSWORD, cof_password);

            long result = MyDB.insert(TABLE_NAME, null, contentValues);
            if (result != -1) {
                isInserted = true;
            } else {
                // Log the error or notify the user
                Log.e("DBHelper", "Failed to insert user into database.");
            }
        } catch (Exception e) {
            // Handle or log exception
            Log.e("DBHelper", "Error during user insertion: " + e.getMessage());
        } finally {
            if (MyDB != null && MyDB.isOpen()) {
                MyDB.close();
            }
        }
        return isInserted;
    }

    public Boolean check_user_exists(String email){
        SQLiteDatabase MyDb = this.getWritableDatabase();
        Cursor cursor = null;
        boolean userExists = false;

        try{
            cursor = MyDb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE email = ?",new String[] {email});
            if(cursor!=null && cursor.getCount() > 0){
                userExists = true; // User Exists in the DB
            }
        }finally {
            if(cursor!=null){ // This condition will work because if there is not matching user exists in the DB, The rawQuery return 0 and the cursor get the value 0 so we can use cursor!=null here
                cursor.close();
            }
        }
        return userExists; // if the user with the provided email address exists than this return statement will return ture otherwise false
    }

    public boolean check_username_and_password(String email,String password){
        SQLiteDatabase MyDB = getWritableDatabase();
        Cursor cursor = null;
        boolean verified = false;

        try{
            cursor = MyDB.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE email = ? and password = ?",new String[] {email,password});
            if(cursor!=null && cursor.getCount()>0){
                verified = true;
            }
        }finally {
            if(cursor!=null){
                cursor.close();
            }
        }

        return verified;
    }

    public String get_username(String email){
        SQLiteDatabase MyDb = this.getReadableDatabase();
        String username = null;
        Cursor cursor = null;

        try{
            String Query = "SELECT "+ COLUMN_NAME +" FROM "+TABLE_NAME+" WHERE "+COLUMN_EMAIL+" = ?";
            cursor = MyDb.rawQuery(Query,new String[] {email});

            if(cursor!=null && cursor.moveToFirst()){
                int usernameIndex = cursor.getColumnIndex(COLUMN_NAME);

                if(usernameIndex != -1){
                    username = cursor.getString(usernameIndex);
                }else{
                    Log.e("DatabaseHelper", "Column not found: " + COLUMN_NAME);
                }
            }
        }catch (Exception e){
            Log.e("DatabaseHelper", "Error retrieving username", e);
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            MyDb.close();
        }

        return username;
    }

    public boolean update_password(String phone,String email,String newpassword){
        SQLiteDatabase MyDB = null;
        boolean isUpdate = false;

        try{
            MyDB = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_PASSWORD,newpassword);
            contentValues.put(COLUMN_COF_PASSWORD,newpassword);
            int rowsAffected = MyDB.update(TABLE_NAME,contentValues,COLUMN_EMAIL+" = ? AND "+COLUMN_PHONE_NO+" = ?",new String[] {email,phone});
            isUpdate = rowsAffected > 0;
        }catch (Exception e){
            Log.e("DarabaseHelper","Error while updating the password",e);
        }finally {
           if(MyDB!=null && MyDB.isOpen()){
               MyDB.close();
           }
        }

        return isUpdate;
    }
}
