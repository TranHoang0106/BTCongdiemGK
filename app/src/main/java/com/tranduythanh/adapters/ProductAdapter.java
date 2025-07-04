package com.tranduythanh.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tranduythanh.k22411casampleproject.R;
import com.tranduythanh.models.Product;

public class ProductAdapter extends ArrayAdapter<Product> {

    Activity context;
    int resource;
    public ProductAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        //nhân bản giao diện:
        View item=inflater.inflate(resource,null);
        //lúc này: Toàn bộ giao diện trong "resource"
        //đã được nạp vào ô nhớ và mô hình hóa hướng đối tượng
        //và được quản lý bởi biến item (tổng tài giao diện 1 dòng)

        TextView txtProductId=item.findViewById(R.id.txtProductId);
        TextView txtProductName=item.findViewById(R.id.txtProductName);
        TextView txtProductQuantity=item.findViewById(R.id.txtProductQuantity);
        TextView txtProductPrice=item.findViewById(R.id.txtProductPrice);
        ImageView imgProduct=item.findViewById(R.id.imgProduct);
        ImageView imgCart=item.findViewById(R.id.imgCart);

        //truy suất data model ở dòng position (đối số 1) mà nó đang nhân bản:
        Product p=getItem(position);
        //sau đó rải dữ liệu từ Product lên giao diện cho người dùng xem:
        txtProductId.setText(p.getId()+"");
        txtProductName.setText(p.getName());
        txtProductQuantity.setText(p.getQuantity()+"");
        txtProductPrice.setText(p.getPrice()+"(VNĐ)");
        imgProduct.setImageResource(p.getImage_id());
        //xử lý nhấn mua hàng (bấm vào CART)--> tính sau
        return item;
    }
}
