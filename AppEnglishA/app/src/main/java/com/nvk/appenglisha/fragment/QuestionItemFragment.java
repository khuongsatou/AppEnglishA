package com.nvk.appenglisha.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nvk.appenglisha.R;
import com.nvk.appenglisha.activity.ShowQuestionActivity;
import com.nvk.appenglisha.adapter.QuestionAdapter;
import com.nvk.appenglisha.controller.QuestionController;
import com.nvk.appenglisha.model.Question;

import org.w3c.dom.Text;

import java.util.List;

import static com.nvk.appenglisha.adapter.CodeAdapter.KEY_CODE_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionItemFragment extends Fragment {
    private Context context;
    private List<Question> questions;
    private QuestionAdapter adapter;
    private int position;
    private TextView tvNumber,tvQuestionName,tvExplain;
    private Button[] btnSchemes;
    private ShowQuestionActivity showQuestionActivity;
    private QuestionController controller;
    private Question question;
    private boolean isChecked = false;
    private String[] schemesSpn = {"A","B","C","D"};
    private int code_id = 0;

    public QuestionItemFragment() {
    }

    public QuestionItemFragment(Context context, List<Question> questions,int position,QuestionAdapter adapter) {
        this.context = context;
        this.questions = questions;
        this.position = position;
        this.adapter = adapter;
        this.showQuestionActivity = (ShowQuestionActivity) context;
        this.controller = new QuestionController(context);
    }


    private Question getItem(int position){
        return questions.get(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_item, container, false);

        tvNumber = view.findViewById(R.id.tvNumber);
        tvQuestionName = view.findViewById(R.id.tvQuestionName);
        tvExplain = view.findViewById(R.id.tvExplain);

        btnSchemes = new Button[4];
        btnSchemes[0] = view.findViewById(R.id.btnSchemes_A);
        btnSchemes[1] = view.findViewById(R.id.btnSchemes_B);
        btnSchemes[2] = view.findViewById(R.id.btnSchemes_C);
        btnSchemes[3] = view.findViewById(R.id.btnSchemes_D);


        return view;
    }

    private void restartQuestion() {
        questions.clear();
        questions.addAll(controller.getQuestionByCode_ID(this.code_id));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.code_id = getItem(position).getCode_id();
        this.question = controller.getQuestionByIDAndCode_ID(getItem(position).getId(),this.code_id);
        tvNumber.setText((this.position+1)+"");
        tvQuestionName.setText(question.getQuestion_name());
        tvExplain.setText(question.getExplain());
        btnSchemes[0].setText(question.getSchemes_a());
        btnSchemes[1].setText(question.getSchemes_b());
        btnSchemes[2].setText(question.getSchemes_c());
        btnSchemes[3].setText(question.getSchemes_d());

        listenerEventSchemes();
        listenerEventNumber();

    }

    private void listenerEventNumber() {
        tvNumber.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialog();
                return true;
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog_question_update_delete,null,false);
        dialog.setView(view);
        final Question question = getItem(position);

        final AlertDialog alertDialog = dialog.create();
        final EditText edtQuestionName = view.findViewById(R.id.edtQuestionName);
        final EditText[] edtSchemes = new EditText[4];
        edtSchemes[0] = view.findViewById(R.id.edtSchemes_A);
        edtSchemes[1] = view.findViewById(R.id.edtSchemes_B);
        edtSchemes[2] = view.findViewById(R.id.edtSchemes_C);
        edtSchemes[3] = view.findViewById(R.id.edtSchemes_D);
        final EditText edtExplain = view.findViewById(R.id.edtExplain);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnDelete = view.findViewById(R.id.btnDelete);
        final Spinner spnAnswer = view.findViewById(R.id.spnAnsert);
        ArrayAdapter<String> adapterSpn = new ArrayAdapter<>(context,android.R.layout.simple_spinner_item,schemesSpn);
        adapterSpn.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnAnswer.setAdapter(adapterSpn);
        int result = 0;
        switch (getItem(position).getAnswer()){
            case "A":
                result = 0;
                break;
            case "B":
                result = 1;
                break;
            case "C":
                result = 2;
                break;
            default:
                result = 3;
        }

        edtQuestionName.setText(question.getQuestion_name());
        edtSchemes[0].setText(question.getSchemes_a());
        edtSchemes[1].setText(question.getSchemes_b());
        edtSchemes[2].setText(question.getSchemes_c());
        edtSchemes[3].setText(question.getSchemes_d());
        edtExplain.setText(question.getExplain());

        spnAnswer.setSelection(result);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String questionName = edtQuestionName.getText().toString().trim();
                String[] schemes = new String[4];
                String explain = edtExplain.getText().toString().trim();
                boolean checkField = false;
                for (int i = 0; i <schemes.length ; i++) {
                    schemes[i] = edtSchemes[i].getText().toString().trim();
                    if (schemes[i].equals("")){
                        checkField = true;
                        break;
                    }
                }
                if (questionName.equals("") || explain.equals("") || checkField){
                    Toast.makeText(context,"Chưa nhập giá trị",Toast.LENGTH_SHORT).show();
                }else{
                    question.setQuestion_name(questionName);
                    question.setSchemes_a(schemes[0]);
                    question.setSchemes_b(schemes[1]);
                    question.setSchemes_c(schemes[2]);
                    question.setSchemes_d(schemes[3]);
                    question.setAnswer(spnAnswer.getSelectedItem().toString());
                    question.setExplain(explain);
                    controller.updateQuestion(question);
                    restartQuestion();
                }
                alertDialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question.setStatus(1);
                controller.deleteQuestion(question);
                restartQuestion();
                alertDialog.dismiss();
            }
        });


        alertDialog.show();


    }

    private void listenerEventSchemes() {
        for (int i = 0; i < btnSchemes.length; i++) {
            getEventSchemes(i);
        }

    }

    private void getEventSchemes(final int i){
        btnSchemes[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnsert(v);
            }
        });
    }

    private void checkAnsert(View v){
        if (!isChecked){
            if (v.getTag().equals(question.getAnswer())){
                v.setBackgroundColor(Color.RED);
            }else{
                v.setBackgroundColor(Color.GREEN);
            }
            for (int i = 0; i < btnSchemes.length ; i++) {
                if (question.getAnswer().equals(btnSchemes[i].getTag())){
                    btnSchemes[i].setBackgroundColor(Color.RED);
                    break;
                }
            }
            tvExplain.setVisibility(View.VISIBLE);
            isChecked = true;
        }
    }




}
