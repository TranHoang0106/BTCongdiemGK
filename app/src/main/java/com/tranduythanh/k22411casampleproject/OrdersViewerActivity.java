package com.tranduythanh.k22411casampleproject;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranduythanh.adapters.OrdersViewerAdapter;
import com.tranduythanh.connectors.OrdersViewerConnector;
import com.tranduythanh.connectors.SQLiteConnector;
import com.tranduythanh.models.OrdersViewer;

import java.util.ArrayList;

public class OrdersViewerActivity extends AppCompatActivity {

    ListView lvOrdersViewer;
    OrdersViewerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders_viewer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addViews();
    }

    private void addViews() {
        lvOrdersViewer=findViewById(R.id.lvOdersViewer);
        adapter=new OrdersViewerAdapter(this,R.layout.item_odersviewer);
        lvOrdersViewer.setAdapter(adapter);

        SQLiteConnector connector=new SQLiteConnector(this);

        OrdersViewerConnector ovc=new OrdersViewerConnector();

        ArrayList<OrdersViewer>dataset=ovc.getAllOrdersViewers(connector.openDatabase());

        adapter.addAll(dataset);
    }
}