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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.SharedPreference.SharedPreferencedConfig;
import ranggacikal.com.myapplication.adapter.BarangAdminAdapter;
import ranggacikal.com.myapplication.adapter.SearchBarangAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataBarangItem;
import ranggacikal.com.myapplication.model.DataSearchBarangItem;
import ranggacikal.com.myapplication.model.ResponseDataBarang;
import ranggacikal.com.myapplication.model.ResponseSearchBarang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BarangAdminActivity extends AppCompatActivity {

//    @BindView(R.id.linear_kelola_jenis_barang)
//    LinearLayout linearKelolaJenisBarang;
//    @BindView(R.id.linear_tambah_barang)
//    LinearLayout linearTambahBarang;
    @BindView(R.id.recycler_data_barang)
    RecyclerView recyclerDataBarang;
    @BindView(R.id.edt_search_barang)
    EditText edtSearchBarang;

    List<DataBarangItem> barangItemList;
    BarangAdminAdapter adapter;
    SearchBarangAdapter adapterSearch;
    @BindView(R.id.btn_search)
    LinearLayout btnSearch;
    @BindView(R.id.text_data_tidak_ada)
    TextView textDataTidakAda;
    @BindView(R.id.bottom_navigation_barang_admin)
    BottomNavigationView bottomNavigationBarangAdmin;

    private SharedPreferencedConfig preferencedConfig;

    String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_barang_admin);
        ButterKnife.bind(this);

        preferencedConfig = new SharedPreferencedConfig(this);

        LoadDataBarang();

        level = preferencedConfig.getPreferenceLevelUser();

        if (level.equals("Admin") || level.equals("admin")) {
            bottomNavigationHandler();
        }else if (level.equals("Kasir") || level.equals("kasir")){
            bottomNavigationBarangAdmin.setVisibility(View.GONE);
        }

        edtSearchBarang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LoadDataBarang();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                LoadSearchBarang();
            }
        });

//        linearTambahBarang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(BarangAdminActivity.this, TambahBarangActivity.class));
//                finish();
//            }
//        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadSearchBarang();
            }
        });

//        linearKelolaJenisBarang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(BarangAdminActivity.this, JenisBarangActivity.class));
//                finish();
//            }
//        });


    }

    private void bottomNavigationHandler() {

        bottomNavigationBarangAdmin.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_tambah_barang :
                        startActivity(new Intent(BarangAdminActivity.this, TambahBarangActivity.class));
                        finish();
                        break;
                    case R.id.action_kelola_jenis_barang:
                        startActivity(new Intent(BarangAdminActivity.this, JenisBarangActivity.class));
                        finish();
                        break;
                }

                return false;
            }
        });

    }

    private void LoadSearchBarang() {

        String kode_barang = edtSearchBarang.getText().toString().toUpperCase();

        ConfigRetrofit.service.SearchBarang(kode_barang).enqueue(new Callback<ResponseSearchBarang>() {
            @Override
            public void onResponse(Call<ResponseSearchBarang> call, Response<ResponseSearchBarang> response) {
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();

                    if (status == 1) {
                        List<DataSearchBarangItem> searchBarangList = response.body().getDataSearchBarang();
                        adapterSearch = new SearchBarangAdapter(BarangAdminActivity.this, searchBarangList);
                        GridLayoutManager gridLayoutSearch = new GridLayoutManager(BarangAdminActivity.this, 2, LinearLayoutManager.VERTICAL, false);
                        recyclerDataBarang.setLayoutManager(gridLayoutSearch);
                        recyclerDataBarang.setAdapter(adapterSearch);
                        textDataTidakAda.setVisibility(View.GONE);
                        recyclerDataBarang.setVisibility(View.VISIBLE);
                    } else {
                        textDataTidakAda.setVisibility(View.VISIBLE);
                        recyclerDataBarang.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(BarangAdminActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchBarang> call, Throwable t) {
                Toast.makeText(BarangAdminActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void LoadDataBarang() {

        ConfigRetrofit.service.DataBarang().enqueue(new Callback<ResponseDataBarang>() {
            @Override
            public void onResponse(Call<ResponseDataBarang> call, Response<ResponseDataBarang> response) {
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();

                    if (status == 1) {
                        List<DataBarangItem> barangList = response.body().getDataBarang();
                        adapter = new BarangAdminAdapter(BarangAdminActivity.this, barangList);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(BarangAdminActivity.this, 2, LinearLayoutManager.VERTICAL, false);
                        recyclerDataBarang.setLayoutManager(gridLayoutManager);
                        recyclerDataBarang.setAdapter(adapter);
                        textDataTidakAda.setVisibility(View.GONE);
                        recyclerDataBarang.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(BarangAdminActivity.this, "Data Barang Kosong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BarangAdminActivity.this, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataBarang> call, Throwable t) {
                Toast.makeText(BarangAdminActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (level.equals("Admin") || level.equals("admin")){
            startActivity(new Intent(BarangAdminActivity.this, HomeAdminActivity.class));
            finish();
        }else if (level.equals("Kasir") || level.equals("kasir")){
            startActivity(new Intent(BarangAdminActivity.this, MainActivity.class));
            finish();
        }
    }
}