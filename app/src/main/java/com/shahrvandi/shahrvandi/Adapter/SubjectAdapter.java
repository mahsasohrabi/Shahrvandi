package com.shahrvandi.shahrvandi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.shahrvandi.shahrvandi.Fragment.AddSubjectFragment;
import com.shahrvandi.shahrvandi.ISubjectSelected;
import com.shahrvandi.shahrvandi.Model.Subject;
import com.shahrvandi.shahrvandi.R;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    ISubjectSelected iSubjectSelected;


    Context context;

    List<Subject> subjects;

    public SubjectAdapter(Context context, List<Subject> subjects,ISubjectSelected iSubjectSelected) {
        this.context = context;
        this.subjects = subjects;
        this.iSubjectSelected=iSubjectSelected;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.cell_subject,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind();
    }
    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.cell_subject);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iSubjectSelected!=null) iSubjectSelected.onSubjectSelected();
//                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                    AddSubjectFragment addSubjectFragment=new AddSubjectFragment();
//                    addSubjectFragment.show(activity.getSupportFragmentManager(),addSubjectFragment.getTag());
                }
            });
        }
        public void bind(){

        }
    }
}
