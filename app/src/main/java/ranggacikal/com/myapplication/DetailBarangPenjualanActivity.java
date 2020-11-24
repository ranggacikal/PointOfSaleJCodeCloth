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
import ranggacikal.com.myapplication.adapter.DetailBarangAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataDetailPenjualanItem;
import ranggacikal.com.myapplication.model.ResponseDetailPenjualan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailBarangPenjualanActivity extends AppCompatActivity {

    @BindView(R.id.recycler_detail_barang)
    RecyclerView recyclerDetailBarang;

    public static final String EXTRA_ID_PENJUALAN = "extraIdPenjualan";

    String id_penjualan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_detail_barang_penjualan);
        ButterKnife.bind(this);

        id_penjualan = getIntent().getStringExtra(EXTRA_ID_PENJUALAN);
        loadRecyclerDetailBarang();
    }

    private void loadRecyclerDetailBarang() {

        ConfigRetrofit.service.DetailPenjualan(id_penjualan).enqueue(new Callback<ResponseDetailPenjualan>() {
            @Override
            public void onResponse(Call<ResponseDetailPenjualan> call, Response<ResponseDetailPenjualan> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){
                        List<DataDetailPenjualanItem> detailBarangList = response.body().getDataDetailPenjualan();
                        DetailBarangAdapter adapter = new DetailBarangAdapter(DetailBarangPenjualanActivity.this, detailBarangList);
                        recyclerDetailBarang.setAdapter(adapter);
                        recyclerDetailBarang.setLayoutManager(new LinearLayoutManager(DetailBarangPenjualanActivity.this));
                    }else{
                        Toast.makeText(DetailBarangPenjualanActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(DetailBarangPenjualanActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailPenjualan> call, Throwable t) {
                Toast.makeText(DetailBarangPenjualanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetailBarangPenjualanActivity.this, PointOfSaleActivity.class));
        finish();
    }
}