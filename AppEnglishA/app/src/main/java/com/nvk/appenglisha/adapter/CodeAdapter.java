package com.nvk.appenglisha.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.nvk.appenglisha.R;
import com.nvk.appenglisha.activity.ShowCodeActivity;
import com.nvk.appenglisha.activity.ShowQuestionActivity;
import com.nvk.appenglisha.controller.CodeController;
import com.nvk.appenglisha.model.Code;

import java.util.List;

public class CodeAdapter extends RecyclerView.Adapter<CodeAdapter.CodeHolder> {
    public static final String KEY_CODE_ID = "code_id";
    private Context context;
    private List<Code> codes;
    private CodeController codeController;

    public CodeAdapter(Context context, List<Code> codes,CodeController codeController) {
        this.context = context;
        this.codes = codes;
        this.codeController = codeController;
    }

    @NonNull
    @Override
    public CodeAdapter.CodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_code,parent,false);
        return new CodeHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull CodeAdapter.CodeHolder holder, int position) {
        Code code = codes.get(position);
        holder.tvItemCode.setText(code.getCode_name());
    }


    private Code getItem(int position){
        return codes.get(position);
    }

    @Override
    public int getItemCount() {
        return codes.size();
    }

    public class CodeHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        private TextView tvItemCode;
        private CodeAdapter adapter;
        public CodeHolder(@NonNull View itemView,CodeAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            tvItemCode = itemView.findViewById(R.id.tvCodeName);
            tvItemCode.setOnClickListener(this);
            tvItemCode.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ShowQuestionActivity.class);
            Code code = getItem(getLayoutPosition());
            intent.putExtra(KEY_CODE_ID,code.getId());
            context.startActivity(intent);
        }

        private void restartAdapter(){
            codes.clear();
            codes.addAll(codeController.getAllCode());
            adapter.notifyDataSetChanged();
        }


        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.custom_dialog_code_update_delete,null,false);
            dialog.setView(view);
            final AlertDialog alertDialog = dialog.create();
            final EditText edtCodeName = view.findViewById(R.id.edtCodeName);
            Button btnDelete = view.findViewById(R.id.btnDelete);
            Button btnUpdate = view.findViewById(R.id.btnUpdate);
            final Code code = getItem(getLayoutPosition());
            edtCodeName.setText(code.getCode_name());
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    code.setStatus(1);
                    codeController.deleteCode(code);
                    restartAdapter();
                    alertDialog.dismiss();
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String itemCode = edtCodeName.getText().toString().trim();
                    if (!itemCode.equals("")){
                        code.setCode_name(itemCode);
                        codeController.updateCode(code);
                        restartAdapter();
                        alertDialog.dismiss();
                    }
                }
            });
            alertDialog.show();
            return true;
        }
    }
}
