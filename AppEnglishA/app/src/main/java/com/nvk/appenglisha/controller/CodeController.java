package com.nvk.appenglisha.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nvk.appenglisha.model.Code;
import com.nvk.appenglisha.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class CodeController {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private static final String TABLE_NAME ="tbCode";
    private static final String COLUMN_ID ="id";
    private static final String COLUMN_CODE_NAME ="code_name";
    private static final String COLUMN_STATUS ="status";
    private static final String SELECT_ALL = "SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_STATUS +" = 0";
    private static final String permissionRead ="Read";
    private static final String permissionWrite ="Write";

    public CodeController(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }

    private void openDB(String permission){
        if (permission.equals("Read")){
            this.db = dbHelper.getReadableDatabase();
        }else{
            this.db = dbHelper.getWritableDatabase();
        }
    }
    private void closeDB(){
        this.db.close();
    }

    public List<Code>  getAllCode(){
        openDB(permissionRead);
        List<Code> codes = new ArrayList<>();
        Cursor cursor = db.rawQuery(SELECT_ALL,null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name_code = cursor.getString(cursor.getColumnIndex(COLUMN_CODE_NAME));
                int status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));
                Code code = new Code(id,name_code,status);
                codes.add(code);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return codes;
    }

    public Code getCodeByID(int id){
        openDB(permissionRead);
        List<Code> codes = new ArrayList<>();
        Code code = new Code();
        Cursor cursor = db.rawQuery(SELECT_ALL,null);
        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String code_name = cursor.getString(cursor.getColumnIndex(COLUMN_CODE_NAME));
                int status = cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));
                code.setId(id);
                code.setCode_name(code_name);
                code.setStatus(status);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return code;
    }

    public void insertCode(Code code){
        openDB(permissionWrite);
        ContentValues values = new ContentValues();
        values.put(COLUMN_CODE_NAME,code.getCode_name());
        db.insert(TABLE_NAME,null,values);
        closeDB();
    }

    public void updateCode(Code code){
        openDB(permissionWrite);
        ContentValues values = new ContentValues();
        values.put(COLUMN_CODE_NAME,code.getCode_name());
        db.update(TABLE_NAME,values,COLUMN_ID +" = ? ",new String[]{String.valueOf(code.getId())});
        closeDB();
    }

    public void deleteCode(Code code){
        openDB(permissionWrite);
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS,code.getStatus());
        db.update(TABLE_NAME,values,COLUMN_ID +" = ? ",new String[]{String.valueOf(code.getId())});
        closeDB();
    }

}
