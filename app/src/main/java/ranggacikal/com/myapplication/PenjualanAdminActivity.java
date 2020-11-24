package ranggacikal.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PenjualanAdminActivity extends AppCompatActivity {

    @BindView(R.id.linear_transaksi_admin)
    LinearLayout linearTransaksiAdmin;
    @BindView(R.id.linear_laporan_admin)
    LinearLayout linearLaporanAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_penjualan_admin);
        ButterKnife.bind(this);

        linearTransaksiAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PenjualanAdminActivity.this, PointOfSaleActivity.class));
                finish();
            }
        });

        linearLaporanAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PenjualanAdminActivity.this, LaporanActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PenjualanAdminActivity.this, HomeAdminActivity.class));
        finish();
    }
}