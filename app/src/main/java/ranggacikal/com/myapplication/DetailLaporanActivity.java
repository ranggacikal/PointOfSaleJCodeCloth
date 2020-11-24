package ranggacikal.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.adapter.DetailLaporanAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataDetailPenjualanItem;
import ranggacikal.com.myapplication.model.ResponseDetailPenjualan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailLaporanActivity extends AppCompatActivity {

    @BindView(R.id.recycler_detail_laporan)
    RecyclerView recyclerDetailLaporan;

    public static final String EXTRA_ID_PENJUALAN = "extraIdPenjualan";

    String id_penjualan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_detail_laporan);
        ButterKnife.bind(this);

        id_penjualan = getIntent().getStringExtra(EXTRA_ID_PENJUALAN);

        LoadRecyclerDetailLaporan();
    }

    private void LoadRecyclerDetailLaporan() {

        ConfigRetrofit.service.DetailPenjualan(id_penjualan).enqueue(new Callback<ResponseDetailPenjualan>() {
            @Override
            public void onResponse(Call<ResponseDetailPenjualan> call, Response<ResponseDetailPenjualan> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        List<DataDetailPenjualanItem> detailLaporanList = response.body().getDataDetailPenjualan();
                        DetailLaporanAdapter adapter = new DetailLaporanAdapter(DetailLaporanActivity.this, detailLaporanList);
                        recyclerDetailLaporan.setAdapter(adapter);
                        recyclerDetailLaporan.setLayoutManager(new LinearLayoutManager(DetailLaporanActivity.this));
                    }else{
                        Toast.makeText(DetailLaporanActivity.this, "Tidak Ada Data", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(DetailLaporanActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailPenjualan> call, Throwable t) {
                Toast.makeText(DetailLaporanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetailLaporanActivity.this, LaporanActivity.class));
        finish();
    }
}