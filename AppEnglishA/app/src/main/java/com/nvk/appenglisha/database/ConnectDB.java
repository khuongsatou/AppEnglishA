package com.nvk.appenglisha.database;

import android.content.Context;

import com.nvk.appenglisha.utils.DBHelper;

import java.io.IOException;

public class ConnectDB extends DBHelper {
    private DBHelper db;

    public ConnectDB(Context context) {
        super(context);
        db = new DBHelper(context);
        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
