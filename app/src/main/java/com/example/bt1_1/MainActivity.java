package com.example.bt1_1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private MyDatabase myDatabase;
    private ArrayList<SanPham> productList;
    private ArrayAdapter<SanPham> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        myDatabase = new MyDatabase(this);
        productList = myDatabase.getAllProducts();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SanPham selectedProduct = productList.get(position);
                showProductDetailDialog(selectedProduct);
            }

        });
    }

    private void showProductDetailDialog(final SanPham product) {
        // Tạo một layout cho dialog với 2 EditText cho tên và giá sản phẩm
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_edit_product, null);

        final EditText edtTen = dialogView.findViewById(R.id.edtTen);
        final EditText edtGia = dialogView.findViewById(R.id.edtGia);

        // Set dữ liệu cũ vào các EditText
        edtTen.setText(product.getTen());
        edtGia.setText(String.valueOf(product.getGia()));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chi tiết sản phẩm");
        builder.setView(dialogView);

        builder.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lấy giá trị mới từ EditText
                String newTen = edtTen.getText().toString();
                String newGiaStr = edtGia.getText().toString();

                if (!newTen.isEmpty() && !newGiaStr.isEmpty()) {
                    float newGia = Float.parseFloat(newGiaStr);

                    // Cập nhật thông tin sản phẩm trong cơ sở dữ liệu
                    product.setTen(newTen);
                    product.setGia(newGia);
                    myDatabase.updateProduct(product);  // Lưu thông tin vào CSDL

                    // Cập nhật lại ListView
                    productList.clear();
                    productList.addAll(myDatabase.getAllProducts());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xóa sản phẩm khỏi CSDL
                myDatabase.deleteProduct(product.getId());

                // Cập nhật lại ListView
                productList.clear();
                productList.addAll(myDatabase.getAllProducts());
                adapter.notifyDataSetChanged();
            }
        });

        builder.show();
    }

}
