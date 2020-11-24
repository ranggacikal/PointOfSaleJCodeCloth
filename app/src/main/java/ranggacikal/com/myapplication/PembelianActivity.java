package ranggacikal.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.adapter.PembelianAdapter;
import ranggacikal.com.myapplication.adapter.SearchBarangAdapter;
import ranggacikal.com.myapplication.adapter.SearchPembelianAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataPembelianItem;
import ranggacikal.com.myapplication.model.DataSearchBarangItem;
import ranggacikal.com.myapplication.model.DataSearchPembelianItem;
import ranggacikal.com.myapplication.model.ResponseDataPembelian;
import ranggacikal.com.myapplication.model.ResponseSearchDataPembelian;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembelianActivity extends AppCompatActivity {

    @BindView(R.id.edt_search_pembelian)
    EditText edtSearchPembelian;
    @BindView(R.id.btn_search_pembelian)
    LinearLayout btnSearchPembelian;
    @BindView(R.id.linear_tambah_pembelian)
    LinearLayout linearTambahPembelian;
    @BindView(R.id.text_data_tidak_ada_pembelian)
    TextView textDataTidakAdaPembelian;
    @BindView(R.id.rv_pembelian)
    RecyclerView rvPembelian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_pembelian);
        ButterKnife.bind(this);

        LoadRecyclerViewPembelian();

        edtSearchPembelian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LoadRecyclerViewPembelian();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSearchPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchPembelian();
            }
        });


        linearTambahPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PembelianActivity.this, TambahPembelianActivity.class));
                finish();
            }
        });
    }

    private void SearchPembelian() {

        String id_pembelian = edtSearchPembelian.getText().toString();

        ConfigRetrofit.service.SearchPembelian(id_pembelian).enqueue(new Callback<ResponseSearchDataPembelian>() {
            @Override
            public void onResponse(Call<ResponseSearchDataPembelian> call, Response<ResponseSearchDataPembelian> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){
                        List<DataSearchPembelianItem> searchPembelianList = response.body().getDataSearchPembelian();
                        SearchPembelianAdapter adapterSearch = new SearchPembelianAdapter(PembelianActivity.this, searchPembelianList);
                        rvPembelian.setLayoutManager(new LinearLayoutManager(PembelianActivity.this));
                        rvPembelian.setAdapter(adapterSearch);
                        textDataTidakAdaPembelian.setVisibility(View.GONE);
                        rvPembelian.setVisibility(View.VISIBLE);
                    }else{
                        textDataTidakAdaPembelian.setVisibility(View.VISIBLE);
                        rvPembelian.setVisibility(View.GONE);
                    }
                }else{
                    Toast.makeText(PembelianActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchDataPembelian> call, Throwable t) {
                Toast.makeText(PembelianActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void LoadRecyclerViewPembelian() {

        ConfigRetrofit.service.DataPembelian().enqueue(new Callback<ResponseDataPembelian>() {
            @Override
            public void onResponse(Call<ResponseDataPembelian> call, Response<ResponseDataPembelian> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status == 1){
                        List<DataPembelianItem> pembelianList = response.body().getDataPembelian();
                        PembelianAdapter adapter = new PembelianAdapter(PembelianActivity.this, pembelianList);
                        rvPembelian.setAdapter(adapter);
                        rvPembelian.setLayoutManager(new LinearLayoutManager(PembelianActivity.this));
                    }else{
                        Toast.makeText(PembelianActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(PembelianActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataPembelian> call, Throwable t) {
                Toast.makeText(PembelianActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PembelianActivity.this, HomeAdminActivity.class));
        finish();
    }
}