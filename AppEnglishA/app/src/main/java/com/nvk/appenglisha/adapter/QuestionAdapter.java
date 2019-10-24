package com.nvk.appenglisha.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.nvk.appenglisha.fragment.QuestionItemFragment;
import com.nvk.appenglisha.model.Question;

import java.util.List;

public class QuestionAdapter extends FragmentStatePagerAdapter {
    private Context context;
    private List<Question> questions;

    public QuestionAdapter(@NonNull FragmentManager fm,Context context,List<Question> questions) {
        super(fm);
        this.context = context;
        this.questions = questions;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new QuestionItemFragment(context,questions,position,this);
    }

    @Override
    public int getCount() {
        return questions.size();
    }
}
