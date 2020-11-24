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
import ranggacikal.com.myapplication.model.ResponseTambahJenisBarang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahJenisBarangActivity extends AppCompatActivity {

    @BindView(R.id.edt_nama_jenis_barang)
    EditText edtNamaJenisBarang;
    @BindView(R.id.btn_tambah_jenis_barang)
    Button btnTambahJenisBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_tambah_jenis_barang);
        ButterKnife.bind(this);

        btnTambahJenisBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahJenisBarang();
            }
        });
    }

    private void TambahJenisBarang() {

        String jenis_barang = edtNamaJenisBarang.getText().toString();

        if (jenis_barang.isEmpty()){
            edtNamaJenisBarang.setError("Nama Jenis Barang Tidak Boleh Kosong");
            edtNamaJenisBarang.requestFocus();
            return;
        }

        ConfigRetrofit.service.TambahJenisBarang(jenis_barang).enqueue(new Callback<ResponseTambahJenisBarang>() {
            @Override
            public void onResponse(Call<ResponseTambahJenisBarang> call, Response<ResponseTambahJenisBarang> response) {
                if (response.isSuccessful()){
                    int status = response.body().getStatus();

                    if (status == 1){
                        Toast.makeText(TambahJenisBarangActivity.this, "Tambah Data Jenis Barang Berhasil", Toast.LENGTH_SHORT).show();
                        edtNamaJenisBarang.setText("");
                    }else{
                        Toast.makeText(TambahJenisBarangActivity.this, "Tambah Data Jenis Barang GAGAL.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(TambahJenisBarangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahJenisBarang> call, Throwable t) {
                Toast.makeText(TambahJenisBarangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TambahJenisBarangActivity.this, JenisBarangActivity.class));
        finish();
    }
}