package com.nvk.appenglisha.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nvk.appenglisha.R;
import com.nvk.appenglisha.adapter.QuestionAdapter;
import com.nvk.appenglisha.controller.QuestionController;
import com.nvk.appenglisha.model.Question;

import java.util.ArrayList;
import java.util.List;

import static com.nvk.appenglisha.adapter.CodeAdapter.KEY_CODE_ID;

public class ShowQuestionActivity extends AppCompatActivity {
    private ViewPager vpQuestion;
    private QuestionAdapter adapter;
    private List<Question> questions;
    private QuestionController controller = new QuestionController(this);
    private FloatingActionButton fabAdd;
    private int code_id = -1;
    private String[] schemesSpn = {"A","B","C","D"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_question);

        radiation();
        createAdapterQuestion();
        addQuestion();
    }

    private void addQuestion() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAddQuestion();
            }
        });
    }



    private void showDialogAddQuestion() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.custom_dialog_question_add,null,false);
        dialog.setView(view);
        final AlertDialog alertDialog = dialog.create();
        final EditText edtQuestionName = view.findViewById(R.id.edtQuestionName);
        final EditText[] edtSchemes = new EditText[4];
        edtSchemes[0] = view.findViewById(R.id.edtSchemes_A);
        edtSchemes[1] = view.findViewById(R.id.edtSchemes_B);
        edtSchemes[2] = view.findViewById(R.id.edtSchemes_C);
        edtSchemes[3] = view.findViewById(R.id.edtSchemes_D);
        final Spinner spnAnswer = view.findViewById(R.id.spnAnsert);
        ArrayAdapter<String> adapterSpn = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,schemesSpn);
        adapterSpn.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnAnswer.setAdapter(adapterSpn);
        final EditText edtExplain = view.findViewById(R.id.edtExplain);
        Button btnDone = view.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionName = edtQuestionName.getText().toString().trim();
                String[] schemes = new String[4];
                String explain = edtExplain.getText().toString().trim();
                for (int i = 0; i <schemes.length ; i++) {
                    schemes[i] = edtSchemes[i].getText().toString().trim();
                    if (schemes[i].equals("")){
                        v.setVisibility(View.INVISIBLE);
                    }
                }
                if (questionName.equals("")){
                    Toast.makeText(getApplicationContext(),"Chưa nhập giá trị",Toast.LENGTH_SHORT).show();
                }else{
                    Question question = new Question();
                    question.setQuestion_name(questionName);
                    question.setSchemes_a(schemes[0]);
                    question.setSchemes_b(schemes[1]);
                    question.setSchemes_c(schemes[2]);
                    question.setSchemes_d(schemes[3]);
                    question.setAnswer(spnAnswer.getSelectedItem().toString());
                    question.setExplain(explain);
                    question.setCode_id(code_id);

                    long result = controller.insertQuestion(question);
                    if (result > 0){
                        Toast.makeText(getApplicationContext(),"Thêm Thành Công",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Thêm Thất Bại",Toast.LENGTH_SHORT).show();
                    }
                    alertDialog.dismiss();
                    restartQuestion();
                }
            }
        });
        alertDialog.show();
    }

    private void restartQuestion() {
        questions.clear();
        questions.addAll(controller.getQuestionByCode_ID(this.code_id));
        adapter.notifyDataSetChanged();
    }


    private void createAdapterQuestion() {
        questions =new ArrayList<>();
        Intent intent = getIntent();
        this.code_id = intent.getIntExtra(KEY_CODE_ID,-1);
        questions.addAll(controller.getQuestionByCode_ID(this.code_id));
        adapter = new QuestionAdapter(getSupportFragmentManager(),this,questions);
        vpQuestion.setAdapter(adapter);

    }

    private void radiation() {
        vpQuestion = findViewById(R.id.vpQuestion);
        fabAdd = findViewById(R.id.fabAdd);
    }
}
