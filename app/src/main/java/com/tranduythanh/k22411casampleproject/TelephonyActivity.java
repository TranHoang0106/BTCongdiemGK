package com.tranduythanh.k22411casampleproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranduythanh.adapters.TelephonyInforAdapter;
import com.tranduythanh.models.TelephonyInfor;

public class TelephonyActivity extends AppCompatActivity {

    ListView lvTelephonyInfor;
    TelephonyInforAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_telephony);

        // Kiểm tra quyền READ_CONTACTS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // Yêu cầu quyền
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            // Đã có quyền, tiếp tục thực hiện đọc danh bạ
            addViews();
            readAllContacts();
        }

        // Áp dụng padding cho hệ thống thanh trạng thái và thanh điều hướng
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        lvTelephonyInfor = findViewById(R.id.lvTelephonyInfo);
        adapter = new TelephonyInforAdapter(this, R.layout.item_telephonyinfor);
        lvTelephonyInfor.setAdapter(adapter);
    }

    private void readAllContacts() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        adapter.clear();

        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(nameIndex);
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String phone = cursor.getString(phoneIndex);

            TelephonyInfor ti = new TelephonyInfor();
            ti.setDisplayName(name);
            ti.setPhoneNumber(phone);
            adapter.add(ti);
        }
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.telephony_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();  // Lấy ID của mục menu

        // Sử dụng if-else để xử lý menu
        if (itemId == R.id.menu_viettel) {
            filterByNetwork("Viettel");
            return true;
        } else if (itemId == R.id.menu_mobifone) {
            filterByNetwork("MobiFone");
            return true;
        } else if (itemId == R.id.menu_other) {
            filterByNetwork("Other");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void filterByNetwork(String networkType) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        adapter.clear();

        while (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(nameIndex);
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String phone = cursor.getString(phoneIndex);

            if (isPhoneMatchingNetwork(phone, networkType)) {
                TelephonyInfor ti = new TelephonyInfor();
                ti.setDisplayName(name);
                ti.setPhoneNumber(phone);
                adapter.add(ti);
            }
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private boolean isPhoneMatchingNetwork(String phone, String networkType) {
        switch (networkType) {
            case "Viettel":
                return phone.startsWith("086") || phone.startsWith("096") || phone.startsWith("097") || phone.startsWith("098");
            case "MobiFone":
                return phone.startsWith("090") || phone.startsWith("093") || phone.startsWith("070") || phone.startsWith("076");
            case "Other":
                return !phone.startsWith("086") && !phone.startsWith("096") && !phone.startsWith("097") && !phone.startsWith("098")
                        && !phone.startsWith("090") && !phone.startsWith("093") && !phone.startsWith("070") && !phone.startsWith("076");
            default:
                return false;
        }
    }

    // Xử lý kết quả từ yêu cầu quyền (READ_CONTACTS)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền đã được cấp, tiếp tục thực hiện đọc danh bạ
                addViews();
                readAllContacts();
            } else {
                // Quyền bị từ chối, thông báo người dùng
                Toast.makeText(this, "Bạn cần cấp quyền để đọc danh bạ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void callDirect(TelephonyInfor ti) {
    }

    public void callDialup(TelephonyInfor ti) {
    }
}
