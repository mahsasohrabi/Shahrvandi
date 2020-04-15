package com.shahrvandi.shahrvandi.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.shahrvandi.shahrvandi.ApiService.JsonPlaceHolderApi;
import com.shahrvandi.shahrvandi.Cache.SpHandler;
import com.shahrvandi.shahrvandi.CartableListRequestModel;
import com.shahrvandi.shahrvandi.CartableListResponseModel;
import com.shahrvandi.shahrvandi.CartableReportModel;
import com.shahrvandi.shahrvandi.MyActivity;
import com.shahrvandi.shahrvandi.Network.NetHandler;
import com.shahrvandi.shahrvandi.R;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowReportActivity extends MyActivity {

    private RecyclerView recyclerView;
    public static List<CartableReportModel> reportList = new ArrayList<>();
    private String roleId;
    private View v=null;
    private LinearLayoutManager layoutManager;
    private ReportAdapter reportAdapter;
    private ProgressBar progressBar;
    private ArrayList<Integer> expandedPositions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_report);
        recyclerView=findViewById(R.id.rv);
        progressBar=findViewById(R.id.pb);
        progressBar.setVisibility(View.GONE);
        layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        reportAdapter=new ReportAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(reportAdapter);
        getData();
    }
    private void getData() {

        setLoadingMode(true);

        JsonPlaceHolderApi jsonPlaceHolderApi = NetHandler.getRetrofit().create(JsonPlaceHolderApi.class);

        CartableListRequestModel requestModel = new CartableListRequestModel(roleId, SpHandler.get(this).getUserId());

        Call<CartableListResponseModel> call = jsonPlaceHolderApi.cartableList(requestModel);

        call.enqueue(new Callback<CartableListResponseModel>() {
            @Override
            public void onResponse(Call<CartableListResponseModel> call, Response<CartableListResponseModel> response) {

                setLoadingMode(false);
                CartableListResponseModel model = response.body();
//                reportList.addAll(model.getResultvalue());
                reportAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CartableListResponseModel> call, Throwable t) {

                //TODO: show error
                setLoadingMode(false);

            }
        });
    }

    private void setLoadingMode(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }


    private class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder>{


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(R.layout.cell_followreport,parent,false);
            ViewHolder h = new ViewHolder(v);
            return h;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bottomPart.setVisibility(View.GONE);
            if (isExpanded(position)){
                holder.bottomPart.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public int getItemCount() { return 3; }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            public LinearLayout lly, bottomPart;
            public RelativeLayout topPart;
            public ImageView btnExpand, btnCollapse;
            public TextView tvCode, tvStatus, tvOrgUnit, tvFunctionalArea, tvDate,tvAddress;
            public Button btnFeedback;
            public EditText etWorkFlow;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                lly = itemView.findViewById(R.id.lly);
                topPart = itemView.findViewById(R.id.topPart);
                bottomPart = itemView.findViewById(R.id.bottomPart);
                btnExpand = itemView.findViewById(R.id.btnExpand);
                btnCollapse = itemView.findViewById(R.id.btnCollapse);
                tvAddress = itemView.findViewById(R.id.tv_address);
                tvCode = itemView.findViewById(R.id.tvCode);
                tvStatus = itemView.findViewById(R.id.tvStatus);
                tvOrgUnit = itemView.findViewById(R.id.tvOrgUnit);
                tvFunctionalArea = itemView.findViewById(R.id.tvFunctionalArea);
                tvDate = itemView.findViewById(R.id.tvSendTime);
                btnFeedback = itemView.findViewById(R.id.btn_feedback);
                etWorkFlow = itemView.findViewById(R.id.etWorkFlow);

                lly.setOnClickListener(this);
                btnFeedback.setOnClickListener(this);
                tvAddress.setOnClickListener(this);
                btnExpand.setOnClickListener(this);
                btnCollapse.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.lly) {

                } else if (view.getId() == R.id.btnCollapse) {

                    btnCollapse.setVisibility(View.GONE);
                    btnExpand.setVisibility(View.VISIBLE);
                    expandCollapse(false, getAdapterPosition());
                    notifyDataSetChanged();

                } else if (view.getId() == R.id.btnExpand) {

                    btnExpand.setVisibility(View.GONE);
                    btnCollapse.setVisibility(View.VISIBLE);
                    expandCollapse(true, getAdapterPosition());
                    notifyDataSetChanged();

                } else if (view.getId() == R.id.btn_feedback) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                    Fragment myFragment = new FeedBackFragment();
//                    activity.getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.masa, myFragment)
//                            .commit();
                }

                else if (view.getId() == R.id.tv_address) {

                }
            }
        }
    }

    private boolean isExpanded(int position) {
        boolean find = false;
        for (int i = 0; i < expandedPositions.size(); i++) {
            if (expandedPositions.get(i) == position) {
                find = true;
                break;
            }
        }
        return find;
    }
    private void expandCollapse(boolean expand, int position) {

        boolean find = false;
        int index = -1;

        for (int i = 0; i < expandedPositions.size(); i++) {
            if (expandedPositions.get(i) == position) {
                find = true;
                index = i;
                break;
            }
        }

        if (find) {
            if (!expand) {
                expandedPositions.remove(index);
            }
        } else {
            if (expand) {
                expandedPositions.add(position);
            }
        }

    }
}
