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
import ranggacikal.com.myapplication.adapter.JenisSupplierAdapter;
import ranggacikal.com.myapplication.adapter.SearchJenisSupplierAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataJenisSupplierItem;
import ranggacikal.com.myapplication.model.DataSearchJenisSupplierItem;
import ranggacikal.com.myapplication.model.ResponseDataJenisSupplier;
import ranggacikal.com.myapplication.model.ResponseSearchJenisSupplier;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JenisSupplierActivity extends AppCompatActivity {

    @BindView(R.id.edt_search_jenis_supplier)
    EditText edtSearchJenisSupplier;
    @BindView(R.id.btn_search_jenis_supplier)
    LinearLayout btnSearchJenisSupplier;
    @BindView(R.id.text_data_tidak_ada_jenis_supplier)
    TextView textDataTidakAdaJenisSupplier;
    @BindView(R.id.rv_jenis_supplier)
    RecyclerView rvJenisSupplier;
    @BindView(R.id.bottom_navigation_jenis_supplier)
    BottomNavigationView bottomNavigationJenisSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_jenis_supplier);
        ButterKnife.bind(this);

        LoadRecyclerJenisSupplier();

        bottomNavigationHandler();

        btnSearchJenisSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchJenisSupplier();
            }
        });

        edtSearchJenisSupplier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LoadRecyclerJenisSupplier();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void bottomNavigationHandler() {

        bottomNavigationJenisSupplier.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.action_tambah_jenis_supplier:

                        startActivity(new Intent(JenisSupplierActivity.this, TambahJenisSupplierActivity.class));
                        finish();
                        break;
                }

                return false;
            }
        });

    }

    private void SearchJenisSupplier() {

        String id_jenis_supplier = edtSearchJenisSupplier.getText().toString();

        ConfigRetrofit.service.SearchJenisSupplier(id_jenis_supplier).enqueue(new Callback<ResponseSearchJenisSupplier>() {
            @Override
            public void onResponse(Call<ResponseSearchJenisSupplier> call, Response<ResponseSearchJenisSupplier> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        List<DataSearchJenisSupplierItem> searchJenisSupplierList = response.body().getDataSearchJenisSupplier();
                        SearchJenisSupplierAdapter adapterSearch = new SearchJenisSupplierAdapter(JenisSupplierActivity.this, searchJenisSupplierList);
                        rvJenisSupplier.setAdapter(adapterSearch);
                        rvJenisSupplier.setLayoutManager(new LinearLayoutManager(JenisSupplierActivity.this));

                    }else{
                        textDataTidakAdaJenisSupplier.setVisibility(View.VISIBLE);
                        rvJenisSupplier.setVisibility(View.GONE);
                    }
                }else{
                    Toast.makeText(JenisSupplierActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchJenisSupplier> call, Throwable t) {
                Toast.makeText(JenisSupplierActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void LoadRecyclerJenisSupplier() {

        ConfigRetrofit.service.JenisSupplier().enqueue(new Callback<ResponseDataJenisSupplier>() {
            @Override
            public void onResponse(Call<ResponseDataJenisSupplier> call, Response<ResponseDataJenisSupplier> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        List<DataJenisSupplierItem> jenisSupplierList = response.body().getDataJenisSupplier();
                        JenisSupplierAdapter adapter = new JenisSupplierAdapter(JenisSupplierActivity.this, jenisSupplierList);
                        rvJenisSupplier.setAdapter(adapter);
                        rvJenisSupplier.setLayoutManager(new LinearLayoutManager(JenisSupplierActivity.this));
                        textDataTidakAdaJenisSupplier.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(JenisSupplierActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(JenisSupplierActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataJenisSupplier> call, Throwable t) {
                Toast.makeText(JenisSupplierActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(JenisSupplierActivity.this, SupplierActivity.class));
        finish();
    }
}