package com.shahrvandi.shahrvandi.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.shahrvandi.shahrvandi.Adapter.SubjectAdapter;
import com.shahrvandi.shahrvandi.ISubjectSelected;
import com.shahrvandi.shahrvandi.Model.Subject;
import com.shahrvandi.shahrvandi.R;


import java.util.ArrayList;
import java.util.List;


public class SubjectFragment extends BottomSheetDialogFragment {
    private RecyclerView recyclerView;
    List<Subject> subjectList = new ArrayList<>();
    SubjectAdapter subjectAdapter;
    public ISubjectSelected subjectSelected=null;
    public SubjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_subject, container, false);
        recyclerView=view.findViewById(R.id.rv_subject);
        initRecycler();
        setList();
        return view;
    }

    public void setList() {
        subjectAdapter = new SubjectAdapter(getContext(),subjectList,subjectSelected);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(subjectAdapter);
    }

    private void initRecycler() {
        subjectList.add(new Subject());
        subjectList.add(new Subject());
        subjectList.add(new Subject());
        subjectList.add(new Subject());
    }


}
