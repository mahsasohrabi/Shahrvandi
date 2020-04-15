package com.shahrvandi.shahrvandi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahrvandi.shahrvandi.IAddAttachment;
import com.shahrvandi.shahrvandi.ISubjectSelected;
import com.shahrvandi.shahrvandi.Model.Attach;
import com.shahrvandi.shahrvandi.Model.Subject;
import com.shahrvandi.shahrvandi.R;

import java.util.List;

public class AttachFileAdapter extends RecyclerView.Adapter<AttachFileAdapter.Holder> {

    Context context;
    List<Attach> attaches;
//    IAddAttachment addAttachment;
    public AttachFileAdapter(Context context, List<Attach> attaches) {
        this.context = context;
        this.attaches = attaches;
//        this.addAttachment= addAttachment;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.cell_delete_sub,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
