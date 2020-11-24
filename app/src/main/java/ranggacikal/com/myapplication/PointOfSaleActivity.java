package ranggacikal.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.SharedPreference.SharedPreferencedConfig;
import ranggacikal.com.myapplication.adapter.PenjualanAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataPenjualanItem;
import ranggacikal.com.myapplication.model.ResponseDataPenjualan;
import ranggacikal.com.myapplication.model.ResponseTambahPenjualan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointOfSaleActivity extends AppCompatActivity {

    @BindView(R.id.btn_tambah_penjualan)
    LinearLayout btnTambahPenjualan;
    @BindView(R.id.rv_penjualan_pos)
    RecyclerView rvPenjualanPos;
//    @BindView(R.id.bottom_navigation_penjualan)
//    BottomNavigationView bottomNavigationPenjualan;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_point_of_sale);
        ButterKnife.bind(this);

        preferencedConfig = new SharedPreferencedConfig(this);

        LoadRecyclerViewPenjualan();

        btnTambahPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahPenjualan();
            }
        });
    }

    private void LoadRecyclerViewPenjualan() {

        ConfigRetrofit.service.DataPenjualan().enqueue(new Callback<ResponseDataPenjualan>() {
            @Override
            public void onResponse(Call<ResponseDataPenjualan> call, Response<ResponseDataPenjualan> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {
                        List<DataPenjualanItem> penjualanList = response.body().getDataPenjualan();
                        PenjualanAdapter adapter = new PenjualanAdapter(PointOfSaleActivity.this, penjualanList);
                        rvPenjualanPos.setAdapter(adapter);
                        rvPenjualanPos.setLayoutManager(new LinearLayoutManager(PointOfSaleActivity.this));
                    } else {
                        Toast.makeText(PointOfSaleActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(PointOfSaleActivity.this, "Terjadi Kesalahan Saat Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataPenjualan> call, Throwable t) {
                Toast.makeText(PointOfSaleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void TambahPenjualan() {

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        date = dateFormat.format(calendar.getTime());

        String tanggal_pembelian = date;
        int total = 0;
        String id_user = preferencedConfig.getPreferenceIdUser();
        String status_penjualan = "Belum Selesai";

        ConfigRetrofit.service.TambahPenjualan(tanggal_pembelian, total, id_user, status_penjualan).enqueue(new Callback<ResponseTambahPenjualan>() {
            @Override
            public void onResponse(Call<ResponseTambahPenjualan> call, Response<ResponseTambahPenjualan> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {
                        Toast.makeText(PointOfSaleActivity.this, "Berhasil Tambah Penjualan", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PointOfSaleActivity.this, TambahPenjualanActivity.class));
                        finish();
                    } else {
                        Toast.makeText(PointOfSaleActivity.this, "Gagal Membuat Penjulan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PointOfSaleActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahPenjualan> call, Throwable t) {
                Toast.makeText(PointOfSaleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        String level = preferencedConfig.getPreferenceLevelUser();
        if (level.equals("Kasir") || level.equals("kasir")) {
            startActivity(new Intent(PointOfSaleActivity.this, MainActivity.class));
            finish();

        }else if (level.equals("Admin") || level.equals("admin")){
            startActivity(new Intent(PointOfSaleActivity.this, PenjualanAdminActivity.class));
            finish();
        }
    }
}