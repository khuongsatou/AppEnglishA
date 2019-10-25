package com.nvk.appenglisha.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nvk.appenglisha.model.Code;
import com.nvk.appenglisha.model.Question;
import com.nvk.appenglisha.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class QuestionController {
    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private static final String TABLE_NAME = "tbQuestion";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUESTION_NAME = "question_name";
    private static final String COLUMN_SCHEMES_A = "schemes_A";
    private static final String COLUMN_SCHEMES_B = "schemes_B";
    private static final String COLUMN_SCHEMES_C = "schemes_C";
    private static final String COLUMN_SCHEMES_D = "schemes_D";
    private static final String COLUMN_ANSWER = "answer";
    private static final String COLUMN_EXPLAIN = "explain";
    private static final String COLUMN_CODE_ID = "code_id";


    private static final String COLUMN_STATUS = "status";
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_STATUS + " = 0 ";
    private static final String permissionRead = "Read";
    private static final String permissionWrite = "Write";

    public QuestionController(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }

    private void openDB(String permission) {
            if (permission.equals("Read")) {
                db = dbHelper.getReadableDatabase();
            } else {
                db = dbHelper.getWritableDatabase();
            }
    }

    private void closeDB() {
        db.close();
    }

    public List<Question> getQuestionByCode_ID(int code_id) {
        openDB(permissionRead);
        List<Question> questions = new ArrayList<>();
        Cursor cursor = db.rawQuery(SELECT_ALL  + " AND "+COLUMN_CODE_ID+" = "+code_id, null);
        //Log.d("AAAA")
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Question question = new Question();
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String question_name = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_NAME));
                String schemes_a = cursor.getString(cursor.getColumnIndex(COLUMN_SCHEMES_A));
                String schemes_b = cursor.getString(cursor.getColumnIndex(COLUMN_SCHEMES_B));
                String schemes_c = cursor.getString(cursor.getColumnIndex(COLUMN_SCHEMES_C));
                String schemes_d = cursor.getString(cursor.getColumnIndex(COLUMN_SCHEMES_D));
                String answer = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER));
                String explain = cursor.getString(cursor.getColumnIndex(COLUMN_EXPLAIN));
                code_id = cursor.getInt(cursor.getColumnIndex(COLUMN_CODE_ID));
                int status =  cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));
                question = new Question(id, question_name, schemes_a, schemes_b, schemes_c, schemes_d, answer, explain, code_id, status);
                questions.add(question);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDB();
        return questions;
    }



    public Question getQuestionByIDAndCode_ID(int id,int code_id) {
        openDB(permissionRead);
        Question question = new Question();
        Cursor cursor = db.rawQuery(SELECT_ALL  + " AND "+COLUMN_ID+" = "+id +" AND "+COLUMN_CODE_ID + " = "+code_id, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String question_name = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_NAME));
            String schemes_a = cursor.getString(cursor.getColumnIndex(COLUMN_SCHEMES_A));
            String schemes_b = cursor.getString(cursor.getColumnIndex(COLUMN_SCHEMES_B));
            String schemes_c = cursor.getString(cursor.getColumnIndex(COLUMN_SCHEMES_C));
            String schemes_d = cursor.getString(cursor.getColumnIndex(COLUMN_SCHEMES_D));
            String answer = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER));
            String explain = cursor.getString(cursor.getColumnIndex(COLUMN_EXPLAIN));
            code_id = cursor.getInt(cursor.getColumnIndex(COLUMN_CODE_ID));
            int status =  cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS));
            question = new Question(id, question_name, schemes_a, schemes_b, schemes_c, schemes_d, answer, explain, code_id, status);
        }
        cursor.close();
        closeDB();
        return question;
    }

    public Long insertQuestion(Question question) {
        openDB(permissionWrite);
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_NAME, question.getQuestion_name());
        values.put(COLUMN_SCHEMES_A, question.getSchemes_a());
        values.put(COLUMN_SCHEMES_B, question.getSchemes_b());
        values.put(COLUMN_SCHEMES_C, question.getSchemes_c());
        values.put(COLUMN_SCHEMES_D, question.getSchemes_d());
        values.put(COLUMN_ANSWER, question.getAnswer());
        values.put(COLUMN_EXPLAIN, question.getExplain());
        values.put(COLUMN_CODE_ID,question.getCode_id());
        long result = db.insert(TABLE_NAME, null, values);
        closeDB();
        return result;
    }

    public long updateQuestion(Question question) {
        openDB(permissionWrite);
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION_NAME, question.getQuestion_name());
        values.put(COLUMN_SCHEMES_A, question.getSchemes_a());
        values.put(COLUMN_SCHEMES_B, question.getSchemes_b());
        values.put(COLUMN_SCHEMES_C, question.getSchemes_c());
        values.put(COLUMN_SCHEMES_D, question.getSchemes_d());
        values.put(COLUMN_ANSWER, question.getAnswer());
        values.put(COLUMN_EXPLAIN, question.getExplain());
        long result = db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(question.getId())});
        closeDB();
        return result;
    }

    public void deleteQuestion(Question question) {
        openDB(permissionWrite);
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, question.getStatus());
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(question.getId())});
        closeDB();
    }
}
