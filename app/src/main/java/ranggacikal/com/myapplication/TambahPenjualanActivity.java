package ranggacikal.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.adapter.PilihBarangAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataBarangItem;
import ranggacikal.com.myapplication.model.DataIdPenjualan;
import ranggacikal.com.myapplication.model.ResponseDataBarang;
import ranggacikal.com.myapplication.model.ResponseGetIdPenjualan;
import ranggacikal.com.myapplication.model.ResponseHapusPenjualan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPenjualanActivity extends AppCompatActivity {

    @BindView(R.id.edt_id_penjualan)
    EditText edtIdPenjualan;

    List<DataIdPenjualan> idItems;
    @BindView(R.id.edt_search_pilih_barang)
    EditText edtSearchPilihBarang;
    @BindView(R.id.btn_search_pilih_barang)
    LinearLayout btnSearchPilihBarang;
    @BindView(R.id.rv_pilih_barang)
    RecyclerView rvPilihBarang;
    @BindView(R.id.fab_keranjang)
    FloatingActionButton fabKeranjang;

    String id_penjualan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_tambah_penjualan);
        ButterKnife.bind(this);



        fabKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TambahPenjualanActivity.this, KeranjangActivity.class);
                intent.putExtra(KeranjangActivity.EXTRA_ID_PENJUALAN, id_penjualan);
                startActivity(intent);
                finish();
            }
        });

        LoadRecyclerPilihBarang();

        initIdPenjualan();

    }

    private void LoadRecyclerPilihBarang() {

        ConfigRetrofit.service.DataBarang().enqueue(new Callback<ResponseDataBarang>() {
            @Override
            public void onResponse(Call<ResponseDataBarang> call, Response<ResponseDataBarang> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {

                        List<DataBarangItem> barangList = response.body().getDataBarang();
                        PilihBarangAdapter adapter = new PilihBarangAdapter(TambahPenjualanActivity.this, barangList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(TambahPenjualanActivity.this, 2, LinearLayoutManager.VERTICAL, false);
                        rvPilihBarang.setAdapter(adapter);
                        rvPilihBarang.setLayoutManager(gridLayoutManager);

                    } else {
                        Toast.makeText(TambahPenjualanActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(TambahPenjualanActivity.this, "Terjadi Kesalahan Saat mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataBarang> call, Throwable t) {
                Toast.makeText(TambahPenjualanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initIdPenjualan() {

        ConfigRetrofit.service.IdPenjulan().enqueue(new Callback<ResponseGetIdPenjualan>() {
            @Override
            public void onResponse(Call<ResponseGetIdPenjualan> call, Response<ResponseGetIdPenjualan> response) {
                if (response.isSuccessful()) {
                    String id = response.body().getDataIdPenjualan().getIdPenjualan();

                    edtIdPenjualan.setText(id);
                    id_penjualan = edtIdPenjualan.getText().toString();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetIdPenjualan> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HapusPenjualan();
    }

    private void HapusPenjualan() {


        ConfigRetrofit.service.HapusPenjualan(id_penjualan).enqueue(new Callback<ResponseHapusPenjualan>() {
            @Override
            public void onResponse(Call<ResponseHapusPenjualan> call, Response<ResponseHapusPenjualan> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status == 1){
                        Toast.makeText(TambahPenjualanActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TambahPenjualanActivity.this, PointOfSaleActivity.class));
                        finish();
                    }else{
                        Toast.makeText(TambahPenjualanActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(TambahPenjualanActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusPenjualan> call, Throwable t) {
                Toast.makeText(TambahPenjualanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}