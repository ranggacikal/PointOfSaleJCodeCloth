package ranggacikal.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.ResponseTambahJenisSupplier;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahJenisSupplierActivity extends AppCompatActivity {

    @BindView(R.id.edt_nama_jenis_supplier)
    EditText edtNamaJenisSupplier;
    @BindView(R.id.btn_tambah_jenis_supplier)
    Button btnTambahJenisSupplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_tambah_jenis_supplier);
        ButterKnife.bind(this);

        btnTambahJenisSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahJenisSupplier();
            }
        });
    }

    private void TambahJenisSupplier() {

        String jenis_supplier = edtNamaJenisSupplier.getText().toString();

        ConfigRetrofit.service.TambahJenisSupplier(jenis_supplier).enqueue(new Callback<ResponseTambahJenisSupplier>() {
            @Override
            public void onResponse(Call<ResponseTambahJenisSupplier> call, Response<ResponseTambahJenisSupplier> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status == 1){

                        Toast.makeText(TambahJenisSupplierActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        edtNamaJenisSupplier.setText("");

                    }else{

                        Toast.makeText(TambahJenisSupplierActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(TambahJenisSupplierActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahJenisSupplier> call, Throwable t) {
                Toast.makeText(TambahJenisSupplierActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TambahJenisSupplierActivity.this, JenisSupplierActivity.class));
        finish();
    }
}