package com.tranduythanh.models;

import java.util.ArrayList;

public class ListPaymentMethod {
    ArrayList<PaymentMethod>paymentMethods;
    public ListPaymentMethod()
    {
        paymentMethods=new ArrayList<>();
    }

    public ArrayList<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(ArrayList<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
    public void gen_payment_method()
    {
        paymentMethods.add(new PaymentMethod(1,"Banking Account","Chuyển khoản ngân hàng"));
        paymentMethods.add(new PaymentMethod(2,"MOMO","Dùng ví điện tử MOMO"));
        paymentMethods.add(new PaymentMethod(3,"Cash","Trả bằng tiền mặt"));
        paymentMethods.add(new PaymentMethod(4,"COD","Xem hàng xong mới trả tiền"));
    }
}
