package ranggacikal.com.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import ranggacikal.com.myapplication.model.ResponseEditSupplier;
import ranggacikal.com.myapplication.model.ResponseHapusSupplier;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditHapusSupplier extends AppCompatActivity {

    @BindView(R.id.edt_edit_nama_supplier)
    EditText edtEditNamaSupplier;
    @BindView(R.id.edt_edit_notelpon_supplier)
    EditText edtEditNotelponSupplier;
    @BindView(R.id.edt_edit_email_supplier)
    EditText edtEditEmailSupplier;
    @BindView(R.id.edt_edit_alamat_supplier)
    EditText edtEditAlamatSupplier;
    @BindView(R.id.spinner_edit_jenis_supplier)
    Spinner spinnerEditJenisSupplier;
    @BindView(R.id.edt_edit_jenis_supplier)
    EditText edtEditJenisSupplier;
    @BindView(R.id.edt_edit_jenis_supplier_spinner)
    EditText edtEditJenisSupplierSpinner;
    @BindView(R.id.btn_edit_jenis_supplier)
    LinearLayout btnEditJenisSupplier;
    @BindView(R.id.btn_update_supplier)
    TextView btnUpdateSupplier;
    @BindView(R.id.btn_hapus_supplier)
    TextView btnHapusSupplier;

    public static final String EXTRA_ID_SUPPLIER = "extraIdSupplier";
    public static final String EXTRA_NAMA_SUPPLIER = "extraNamaSupplier";
    public static final String EXTRA_NO_TELPON_SUPPLIER = "extraNoTelponSupplier";
    public static final String EXTRA_ALAMAT_SUPPLIER = "extraALamatSupplier";
    public static final String EXTRA_EMAIL_SUPPLIER = "extraEmailSupplier";
    public static final String EXTRA_ID_JENIS_SUPPLIER = "extraIdJenisSupplier";

    List<DataJenisSupplierItem> jenisSupplierItems;

    String id_jenis_supplier;
    String id_supplier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_edit_hapus_supplier);
        ButterKnife.bind(this);

        initSpinnerJenisSupplier();

        spinnerEditJenisSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_jenis_supplier = jenisSupplierItems.get(position).getIdJenisSuplier();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtEditNamaSupplier.setText(getIntent().getStringExtra(EXTRA_ID_SUPPLIER));
        edtEditNotelponSupplier.setText(getIntent().getStringExtra(EXTRA_NO_TELPON_SUPPLIER));
        edtEditAlamatSupplier.setText(getIntent().getStringExtra(EXTRA_ALAMAT_SUPPLIER));
        edtEditEmailSupplier.setText(getIntent().getStringExtra(EXTRA_EMAIL_SUPPLIER));
        edtEditJenisSupplier.setText(getIntent().getStringExtra(EXTRA_ID_JENIS_SUPPLIER));
        id_supplier = getIntent().getStringExtra(EXTRA_ID_SUPPLIER);

        btnEditJenisSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerEditJenisSupplier.setVisibility(View.VISIBLE);
                btnEditJenisSupplier.setVisibility(View.GONE);
                edtEditJenisSupplier.setVisibility(View.VISIBLE);
            }
        });

        btnUpdateSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateSupplier();
            }
        });

        btnHapusSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HapusSupplier();
            }
        });

    }

    private void HapusSupplier() {

        Dialog dialogHapus = new Dialog(EditHapusSupplier.this);

        dialogHapus.setContentView(R.layout.dialog_hapus_supplier);

        final TextView txtIya = dialogHapus.findViewById(R.id.text_iya_hapus_supplier);
        final TextView txtTidak = dialogHapus.findViewById(R.id.text_tidak_hapus_supplier);

        dialogHapus.show();

        txtIya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProsesHapusData();
                dialogHapus.dismiss();
            }
        });

        txtTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHapus.dismiss();
            }
        });

    }

    private void ProsesHapusData() {

        ConfigRetrofit.service.HapusSupplier(id_supplier).enqueue(new Callback<ResponseHapusSupplier>() {
            @Override
            public void onResponse(Call<ResponseHapusSupplier> call, Response<ResponseHapusSupplier> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status==1){

                        Toast.makeText(EditHapusSupplier.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditHapusSupplier.this, SupplierActivity.class));
                        finish();

                    }else{
                        Toast.makeText(EditHapusSupplier.this, "Data Gagal Dihapus", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EditHapusSupplier.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusSupplier> call, Throwable t) {
                Toast.makeText(EditHapusSupplier.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void UpdateSupplier() {

        String nama_supplier = edtEditNamaSupplier.getText().toString();
        String no_telpon = edtEditNotelponSupplier.getText().toString();
        String alamat = edtEditAlamatSupplier.getText().toString();
        String email = edtEditEmailSupplier.getText().toString();
        String id_jenis_supplier2;

        if (id_jenis_supplier == null){
            id_jenis_supplier2 = getIntent().getStringExtra(EXTRA_ID_JENIS_SUPPLIER);
        }else{
            id_jenis_supplier2 = id_jenis_supplier;
        }

        if (nama_supplier.isEmpty()){
            edtEditNamaSupplier.setError("Nama Tidak Boleh Kosong");
            edtEditNamaSupplier.requestFocus();
            return;
        }

        if (no_telpon.isEmpty()){
            edtEditNotelponSupplier.setError("No Handphone tidak boleh kosong");
            edtEditNotelponSupplier.requestFocus();
            return;
        }

        if (alamat.isEmpty()){
            edtEditAlamatSupplier.setError("Alamat Tidak Boleh Kosong");
            edtEditAlamatSupplier.requestFocus();
            return;
        }

        if (email.isEmpty()){
            edtEditEmailSupplier.setError("Email Tidak Boleh Kosong");
            edtEditEmailSupplier.requestFocus();
            return;
        }

        ConfigRetrofit.service.EditSupplier(id_supplier, nama_supplier, no_telpon, alamat, email, id_jenis_supplier2).enqueue(new Callback<ResponseEditSupplier>() {
            @Override
            public void onResponse(Call<ResponseEditSupplier> call, Response<ResponseEditSupplier> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status == 1){

                        Toast.makeText(EditHapusSupplier.this, pesan, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditHapusSupplier.this, SupplierActivity.class));
                        finish();

                    }else{

                        Toast.makeText(EditHapusSupplier.this, pesan, Toast.LENGTH_SHORT).show();

                    }

                }else{

                    Toast.makeText(EditHapusSupplier.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseEditSupplier> call, Throwable t) {

                Toast.makeText(EditHapusSupplier.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initSpinnerJenisSupplier() {

        ConfigRetrofit.service.JenisSupplier().enqueue(new Callback<ResponseDataJenisSupplier>() {
            @Override
            public void onResponse(Call<ResponseDataJenisSupplier> call, Response<ResponseDataJenisSupplier> response) {
                if (response.isSuccessful()) {
                    jenisSupplierItems = response.body().getDataJenisSupplier();

                    List<String> listSpinnerJenisSupplier = new ArrayList<String>();

                    for (int i = 0; i < jenisSupplierItems.size(); i++) {
                        String jenis_supplier = jenisSupplierItems.get(i).getJenisSupplier();

                        listSpinnerJenisSupplier.add(jenis_supplier);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditHapusSupplier.this, android.R.layout.simple_spinner_dropdown_item, listSpinnerJenisSupplier);
                    spinnerEditJenisSupplier.setAdapter(adapter);
                } else {
                    Toast.makeText(EditHapusSupplier.this, "GAGAL MENGAMBIL DATA SPINNER", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataJenisSupplier> call, Throwable t) {

                Toast.makeText(EditHapusSupplier.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditHapusSupplier.this, SupplierActivity.class));
        finish();

    }
}