package com.page_1.app.admin.Fees;

public class FeeModel {
    private String ERP;
    private String name;
    private String amount;
    private String payId;
    private String date;
    private String time;
    private String count;
    public FeeModel(String ERP,String name,String amount,String payId,String date,String time ,String count){
        this.ERP=ERP;
        this.name=name;
        this.amount=amount;
        this.payId=payId;
        this.date=date;
        this.time=time;
        this.count=count;
    }

    public String getERP() {
        return ERP;
    }

    public void setERP(String ERP) {
        this.ERP = ERP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
