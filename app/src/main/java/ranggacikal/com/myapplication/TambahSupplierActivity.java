package ranggacikal.com.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataJenisSupplierItem;
import ranggacikal.com.myapplication.model.ResponseDataJenisSupplier;
import ranggacikal.com.myapplication.model.ResponseTambahSupplier;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahSupplierActivity extends AppCompatActivity {

    @BindView(R.id.edt_nama_supplier)
    EditText edtNamaSupplier;
    @BindView(R.id.edt_no_telpon_supplier)
    EditText edtNoTelponSupplier;
    @BindView(R.id.edt_alamat_supplier)
    EditText edtAlamatSupplier;
    @BindView(R.id.edt_email_supplier)
    EditText edtEmailSupplier;
    @BindView(R.id.spinner_jenis_supplier_tambah)
    Spinner spinnerJenisSupplierTambah;
    @BindView(R.id.btn_simpan_tambah_supplier)
    TextView btnSimpanTambahSupplier;

    List<DataJenisSupplierItem> jenisSupplierItems;
    String id_jenis_supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_tambah_supplier);
        ButterKnife.bind(this);

        initSpinnerJenisSupplier();

        spinnerJenisSupplierTambah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_jenis_supplier = jenisSupplierItems.get(position).getIdJenisSuplier();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSimpanTambahSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahSupplier();
            }
        });
    }

    private void tambahSupplier() {

        String nama = edtNamaSupplier.getText().toString();
        String no_telpon = edtNoTelponSupplier.getText().toString();
        String alamat = edtAlamatSupplier.getText().toString();
        String email = edtEmailSupplier.getText().toString();

        if (nama.isEmpty()){
            edtNamaSupplier.setError("Nama Tidak Boleh Kosong");
            edtNamaSupplier.requestFocus();
            return;
        }

        if (no_telpon.isEmpty()){
            edtNoTelponSupplier.setError("No Telpon Tidak Boleh Kosong");
            edtNoTelponSupplier.requestFocus();
            return;
        }

        if (alamat.isEmpty()){
            edtAlamatSupplier.setError("Alamat TIdak Boleh Kosong");
            edtAlamatSupplier.requestFocus();
            return;
        }

        if (email.isEmpty()){
            edtEmailSupplier.setError("Email Tidak Boleh Kosong");
            edtEmailSupplier.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmailSupplier.setError("Alamat Email Tidak Valid");
            edtEmailSupplier.requestFocus();
            return;
        }

        final ProgressDialog progDialog = ProgressDialog.show(TambahSupplierActivity.this, "", "Loading..", false);
        ConfigRetrofit.service.TambahSupplier(nama, no_telpon, alamat, email, id_jenis_supplier).enqueue(new Callback<ResponseTambahSupplier>() {
            @Override
            public void onResponse(Call<ResponseTambahSupplier> call, Response<ResponseTambahSupplier> response) {
                if (response.isSuccessful()){

                    progDialog.dismiss();
                    int status = response.body().getStatus();

                    if (status == 1){

                        Toast.makeText(TambahSupplierActivity.this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                        edtNamaSupplier.setText("");
                        edtNoTelponSupplier.setText("");
                        edtAlamatSupplier.setText("");
                        edtEmailSupplier.setText("");

                    }else{
                        Toast.makeText(TambahSupplierActivity.this, "Gagal Tambah Data", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(TambahSupplierActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahSupplier> call, Throwable t) {
                Toast.makeText(TambahSupplierActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initSpinnerJenisSupplier() {

        ConfigRetrofit.service.JenisSupplier().enqueue(new Callback<ResponseDataJenisSupplier>() {
            @Override
            public void onResponse(Call<ResponseDataJenisSupplier> call, Response<ResponseDataJenisSupplier> response) {
                if (response.isSuccessful()){

                    jenisSupplierItems = response.body().getDataJenisSupplier();

                    List<String> listSpinnerJenisSupplier = new ArrayList<String>();

                    for (int i = 0; i < jenisSupplierItems.size(); i++){

                        String jenis_supplier = jenisSupplierItems.get(i).getJenisSupplier();

                        listSpinnerJenisSupplier.add(jenis_supplier);

                    }

                    ArrayAdapter<String> adapterJenisSupplier = new ArrayAdapter<String>(TambahSupplierActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinnerJenisSupplier);
                    spinnerJenisSupplierTambah.setAdapter(adapterJenisSupplier);

                }else{
                    Toast.makeText(TambahSupplierActivity.this, "Gagal Mengambil Data Jenis Supplier", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataJenisSupplier> call, Throwable t) {
                Toast.makeText(TambahSupplierActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TambahSupplierActivity.this, SupplierActivity.class));
        finish();
    }
}