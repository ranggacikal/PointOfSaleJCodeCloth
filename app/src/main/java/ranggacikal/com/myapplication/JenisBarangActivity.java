package ranggacikal.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.adapter.JenisBarangAdapter;
import ranggacikal.com.myapplication.adapter.SearchJenisBarangAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataJenisBarangItem;
import ranggacikal.com.myapplication.model.DataSearchJenisBarangItem;
import ranggacikal.com.myapplication.model.ResponseDataJenisBarang;
import ranggacikal.com.myapplication.model.ResponseSearchJenisBarang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JenisBarangActivity extends AppCompatActivity {

//    @BindView(R.id.linear_tambah_jenis_barang)
//    LinearLayout linearTambahJenisBarang;
    @BindView(R.id.edt_search_barang)
    EditText edtSearchBarang;
    @BindView(R.id.btn_search)
    LinearLayout btnSearch;
    @BindView(R.id.rv_jenis_barang)
    RecyclerView rvJenisBarang;
    @BindView(R.id.text_data_tidak_ada_jenis_barang)
    TextView textDataTidakAdaJenisBarang;
    @BindView(R.id.bottom_navigation_jenis_barang)
    BottomNavigationView bottomNavigationJenisBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_jenis_barang);
        ButterKnife.bind(this);

        bottomNavigationHandler();

        LoadRecyclerJenisBarang();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchJenisBarang();
            }
        });

        edtSearchBarang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LoadRecyclerJenisBarang();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        linearTambahJenisBarang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(JenisBarangActivity.this, TambahJenisBarangActivity.class));
//                finish();
//            }
//        });
    }

    private void bottomNavigationHandler() {

        bottomNavigationJenisBarang.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){

                    case R.id.action_tambah_jenis_barang :

                        startActivity(new Intent(JenisBarangActivity.this, TambahJenisBarangActivity.class));
                        finish();
                        break;

                }

                return false;
            }
        });
    }

    private void SearchJenisBarang() {

        String id_jenis_barang = edtSearchBarang.getText().toString();


        ConfigRetrofit.service.SearchJenisBarang(id_jenis_barang).enqueue(new Callback<ResponseSearchJenisBarang>() {
            @Override
            public void onResponse(Call<ResponseSearchJenisBarang> call, Response<ResponseSearchJenisBarang> response) {
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();

                    if (status == 1) {
                        List<DataSearchJenisBarangItem> searchJenisBarangList = response.body().getDataSearchJenisBarang();
                        SearchJenisBarangAdapter adapterSearch = new SearchJenisBarangAdapter(JenisBarangActivity.this, searchJenisBarangList);
                        rvJenisBarang.setAdapter(adapterSearch);
                        rvJenisBarang.setLayoutManager(new LinearLayoutManager(JenisBarangActivity.this));
                    } else {
                        Toast.makeText(JenisBarangActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                        rvJenisBarang.setVisibility(View.GONE);
                        textDataTidakAdaJenisBarang.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(JenisBarangActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchJenisBarang> call, Throwable t) {
                Toast.makeText(JenisBarangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void LoadRecyclerJenisBarang() {

        ConfigRetrofit.service.JenisBarang().enqueue(new Callback<ResponseDataJenisBarang>() {
            @Override
            public void onResponse(Call<ResponseDataJenisBarang> call, Response<ResponseDataJenisBarang> response) {
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();

                    if (status == 1) {
                        List<DataJenisBarangItem> jenisBarangList = response.body().getDataJenisBarang();
                        JenisBarangAdapter adapter = new JenisBarangAdapter(JenisBarangActivity.this, jenisBarangList);
                        rvJenisBarang.setAdapter(adapter);
                        rvJenisBarang.setLayoutManager(new LinearLayoutManager(JenisBarangActivity.this));
                        textDataTidakAdaJenisBarang.setVisibility(View.GONE);
                        rvJenisBarang.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(JenisBarangActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(JenisBarangActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataJenisBarang> call, Throwable t) {
                Toast.makeText(JenisBarangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(JenisBarangActivity.this, BarangAdminActivity.class));
        finish();
    }
}