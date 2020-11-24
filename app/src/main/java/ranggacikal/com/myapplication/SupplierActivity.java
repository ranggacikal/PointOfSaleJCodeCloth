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
import ranggacikal.com.myapplication.adapter.SearchSupplierAdapter;
import ranggacikal.com.myapplication.adapter.SupplierAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataSearchSupplierItem;
import ranggacikal.com.myapplication.model.DataSupplierItem;
import ranggacikal.com.myapplication.model.ResponseDataSupplier;
import ranggacikal.com.myapplication.model.ResponseSearchSupplier;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierActivity extends AppCompatActivity {

    @BindView(R.id.edt_search_supplier)
    EditText edtSearchSupplier;
    @BindView(R.id.btn_search_supplier)
    LinearLayout btnSearchSupplier;
    @BindView(R.id.text_data_tidak_ada_supplier)
    TextView textDataTidakAdaSupplier;
    @BindView(R.id.rv_supplier)
    RecyclerView rvSupplier;
    @BindView(R.id.bottom_navigation_supplier)
    BottomNavigationView bottomNavigationSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_supplier);
        ButterKnife.bind(this);

        bottomNavigationHandler();

        LoadRecyclerSupplier();

        btnSearchSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchSupplier();
            }
        });

        edtSearchSupplier.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LoadRecyclerSupplier();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void SearchSupplier() {

        String id_supplier = edtSearchSupplier.getText().toString();

        ConfigRetrofit.service.SearchSupplier(id_supplier).enqueue(new Callback<ResponseSearchSupplier>() {
            @Override
            public void onResponse(Call<ResponseSearchSupplier> call, Response<ResponseSearchSupplier> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status == 1){

                        List<DataSearchSupplierItem> searchSupplierList = response.body().getDataSearchSupplier();
                        SearchSupplierAdapter searchAdapter = new SearchSupplierAdapter(SupplierActivity.this, searchSupplierList);
                        rvSupplier.setAdapter(searchAdapter);
                        rvSupplier.setLayoutManager(new LinearLayoutManager(SupplierActivity.this));
                    }else{
                        rvSupplier.setVisibility(View.GONE);
                        textDataTidakAdaSupplier.setVisibility(View.VISIBLE);
                    }

                }else{
                    Toast.makeText(SupplierActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSearchSupplier> call, Throwable t) {
                Toast.makeText(SupplierActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void LoadRecyclerSupplier() {

        ConfigRetrofit.service.DataSupplier().enqueue(new Callback<ResponseDataSupplier>() {
            @Override
            public void onResponse(Call<ResponseDataSupplier> call, Response<ResponseDataSupplier> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status == 1){

                        List<DataSupplierItem> supplierList = response.body().getDataSupplier();
                        SupplierAdapter adapter = new SupplierAdapter(SupplierActivity.this, supplierList);
                        rvSupplier.setAdapter(adapter);
                        rvSupplier.setLayoutManager(new LinearLayoutManager(SupplierActivity.this));
                        textDataTidakAdaSupplier.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(SupplierActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SupplierActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataSupplier> call, Throwable t) {
                Toast.makeText(SupplierActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void bottomNavigationHandler() {

        bottomNavigationSupplier.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){

                    case R.id.action_tambah_supplier :

                        startActivity(new Intent(SupplierActivity.this, TambahSupplierActivity.class));
                        finish();
                        break;

                    case R.id.action_kelola_jenis_supplier :
                        startActivity(new Intent(SupplierActivity.this, JenisSupplierActivity.class));
                        finish();
                        break;
                }

                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SupplierActivity.this, HomeAdminActivity.class));
        finish();
    }
}