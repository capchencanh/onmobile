package bt2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bt1_1.R;

public class MainActivity_b2_2 extends AppCompatActivity {

    private TextView tvPhongBan, tvSoLuongNV;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_b22);

        // Ánh xạ các thành phần giao diện
        tvPhongBan = findViewById(R.id.txtPhongBan);
        tvSoLuongNV = findViewById(R.id.txtSoLuongNV);
        btnBack = findViewById(R.id.btnBack);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            String tenPhongBan = intent.getStringExtra("tenPhongBan");
            int soLuongNV = intent.getIntExtra("soLuong", 0);

            // Hiển thị thông tin trên giao diện
            tvPhongBan.setText("Phòng ban: " + tenPhongBan);
            tvSoLuongNV.setText("Số lượng nhân viên: " + soLuongNV);
        }

        // Nút quay lại Activity 1
        btnBack.setOnClickListener(v -> finish());
    }
}