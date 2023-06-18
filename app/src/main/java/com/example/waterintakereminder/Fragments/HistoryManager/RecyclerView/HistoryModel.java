package com.example.waterintakereminder.Fragments.HistoryManager.RecyclerView;

public class HistoryModel {
    private String amount;
    private String time;

    public HistoryModel() {

    }

    public HistoryModel(String amount, String time) {
        this.amount = amount;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
