package com.nvk.appenglisha.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nvk.appenglisha.R;
import com.nvk.appenglisha.adapter.CodeAdapter;
import com.nvk.appenglisha.controller.CodeController;
import com.nvk.appenglisha.database.ConnectDB;
import com.nvk.appenglisha.model.Code;

import java.util.ArrayList;
import java.util.List;

public class ShowCodeActivity extends AppCompatActivity{
    private RecyclerView rcvCode;
    private ConnectDB db;
    private List<Code> codes;
    private CodeController codeController;
    private CodeAdapter codeAdapter;
    private FloatingActionButton fabAdd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_code);

        radiation();
        createDB();
        dataSource();
        addCode();
    }

    private void addCode() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddCode();
            }
        });
    }

    private void restartAdapter(){
        codes.clear();
        codes.addAll(codeController.getAllCode());
        codeAdapter.notifyDataSetChanged();
    }

    private void showDialogAddCode() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog_code_add,null,false);
        dialog.setView(view);
        final EditText edtCodeName = view.findViewById(R.id.edtCodeName);
        Button btnDone = view.findViewById(R.id.btnDone);
        final AlertDialog alertDialog = dialog.create();
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String code_name = edtCodeName.getText().toString().trim();
                 if (code_name != ""){
                     Code code = new Code();
                     code.setCode_name(code_name);
                     codeController.insertCode(code);
                     restartAdapter();
                     alertDialog.dismiss();
                 }else{
                     Toast.makeText(getApplicationContext(),"Đừng nên để trắng không thế chứ",Toast.LENGTH_SHORT).show();
                 }
            }
        });
        alertDialog.show();
    }

    private void dataSource() {
       codes  = new ArrayList<>();
       codes.addAll(codeController.getAllCode());
       codeAdapter = new CodeAdapter(this,codes,codeController);
       rcvCode.setLayoutManager(new LinearLayoutManager(this));
       rcvCode.setHasFixedSize(true);
       rcvCode.setAdapter(codeAdapter);
    }

    private void createDB() {
        new ConnectDB(this);
        codeController = new CodeController(this);
    }

    private void radiation() {
        rcvCode = findViewById(R.id.rcvCode);
        fabAdd = findViewById(R.id.fabAdd);
    }

}
