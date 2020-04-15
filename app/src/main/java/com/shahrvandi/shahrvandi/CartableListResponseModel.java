package com.shahrvandi.shahrvandi;

import java.util.ArrayList;

public class CartableListResponseModel extends BasicResponseModel {

    private ArrayList<CartableReportModel> resultvalue;

    public ArrayList<CartableReportModel> getResultvalue() {
        return resultvalue;
    }
    public void setResultvalue(ArrayList<CartableReportModel> resultvalue) {
        this.resultvalue = resultvalue;
    }
}
