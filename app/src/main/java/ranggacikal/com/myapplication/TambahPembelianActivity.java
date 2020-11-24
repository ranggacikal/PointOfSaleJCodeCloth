package ranggacikal.com.myapplication;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.SharedPreference.SharedPreferencedConfig;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataBarangItem;
import ranggacikal.com.myapplication.model.DataSupplierItem;
import ranggacikal.com.myapplication.model.ResponseDataBarang;
import ranggacikal.com.myapplication.model.ResponseDataSupplier;
import ranggacikal.com.myapplication.model.ResponseTambahPembelian;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahPembelianActivity extends AppCompatActivity {

    @BindView(R.id.spinner_tambah_nama_barang_pembelian)
    Spinner spinnerTambahNamaBarangPembelian;
    @BindView(R.id.edt_tanggal_pembelian)
    EditText edtTanggalPembelian;
    @BindView(R.id.edt_jumlah_pembelian)
    EditText edtJumlahPembelian;
    @BindView(R.id.edt_total_harga_pembelian)
    EditText edtTotalHargaPembelian;
    @BindView(R.id.spinner_tambah_supplier_pembelian)
    Spinner spinnerTambahSupplierPembelian;
    @BindView(R.id.btn_simpan_tambah_barang)
    TextView btnSimpanTambahBarang;

    List<DataBarangItem> barangItems;
    List<DataSupplierItem> supplierItems;

    @BindView(R.id.linear_pilih_tanggal)
    LinearLayout linearPilihTanggal;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    int id_barang, id_supplier;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_tambah_pembelian);
        ButterKnife.bind(this);

        preferencedConfig = new SharedPreferencedConfig(this);

        initSpinnerNamaBarang();

        initSpinnerSupplier();

        dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        linearPilihTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        spinnerTambahNamaBarangPembelian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_barang = Integer.parseInt(barangItems.get(position).getIdBarang());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTambahSupplierPembelian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_supplier = Integer.parseInt(supplierItems.get(position).getIdSupplier());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSimpanTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TambahPembelian();
            }
        });
    }

    private void TambahPembelian() {

        String tanggal_pembelian = edtTanggalPembelian.getText().toString();
        String jumlah2 = edtJumlahPembelian.getText().toString();
        String total_harga2 = edtTotalHargaPembelian.getText().toString();
        String id_user = preferencedConfig.getPreferenceIdUser();

        if (tanggal_pembelian.isEmpty()){
            edtTanggalPembelian.setError("Tanggal Pembelian Tidak Boleh Kosong");
            edtTanggalPembelian.requestFocus();
            return;
        }

        if (jumlah2.isEmpty()){
            edtJumlahPembelian.setError("Jumlah Tidak Boleh Kosong");
            edtJumlahPembelian.requestFocus();
            return;
        }

        if (total_harga2.isEmpty()){
            edtTotalHargaPembelian.setError("Total Harga Tidak Boleh Kosong");
            edtJumlahPembelian.requestFocus();
            return;
        }

        int jumlah = Integer.parseInt(jumlah2);
        int total_harga = Integer.parseInt(total_harga2);

        final ProgressDialog progDialog = ProgressDialog.show(TambahPembelianActivity.this, "", "Loading..", false);
        ConfigRetrofit.service.TambahPembelian(id_barang, tanggal_pembelian, jumlah, total_harga, id_supplier, id_user).enqueue(new Callback<ResponseTambahPembelian>() {
            @Override
            public void onResponse(Call<ResponseTambahPembelian> call, Response<ResponseTambahPembelian> response) {
                if (response.isSuccessful()){
                    progDialog.dismiss();

                    int status = response.body().getStatus();

                    if (status == 1){
                        progDialog.dismiss();
                        Toast.makeText(TambahPembelianActivity.this, "Berhasil Membuat Pembelian", Toast.LENGTH_SHORT).show();
                        edtTanggalPembelian.setText("");
                        edtJumlahPembelian.setText("");
                        edtTotalHargaPembelian.setText("");
                    }else{
                        Toast.makeText(TambahPembelianActivity.this, "Gagal Membuat Pembelian", Toast.LENGTH_SHORT).show();
                        progDialog.dismiss();
                    }
                }else{
                    progDialog.dismiss();
                    Toast.makeText(TambahPembelianActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTambahPembelian> call, Throwable t) {
                progDialog.dismiss();
                Toast.makeText(TambahPembelianActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initSpinnerSupplier() {

        ConfigRetrofit.service.DataSupplier().enqueue(new Callback<ResponseDataSupplier>() {
            @Override
            public void onResponse(Call<ResponseDataSupplier> call, Response<ResponseDataSupplier> response) {
                if (response.isSuccessful()){
                    supplierItems = response.body().getDataSupplier();
                    List<String> listSpinnerSupplier = new ArrayList<String>();

                    for (int j = 0; j < supplierItems.size(); j++){

                        String nama_supplier = supplierItems.get(j).getNama();
                        String jenis_supplier = supplierItems.get(j).getJenisSupplier();

                        listSpinnerSupplier.add(nama_supplier+" , "+"( "+jenis_supplier+" )");
                    }

                    ArrayAdapter<String> adapterSupplier = new ArrayAdapter<String>(TambahPembelianActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinnerSupplier);
                    spinnerTambahSupplierPembelian.setAdapter(adapterSupplier);
                }else{
                    Toast.makeText(TambahPembelianActivity.this, "GAGAL MENGAMBIL DATA SPINNER SUPPLIER", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataSupplier> call, Throwable t) {
                Toast.makeText(TambahPembelianActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showDateDialog() {

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                edtTanggalPembelian.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void initSpinnerNamaBarang() {

        ConfigRetrofit.service.DataBarang().enqueue(new Callback<ResponseDataBarang>() {
            @Override
            public void onResponse(Call<ResponseDataBarang> call, Response<ResponseDataBarang> response) {
                if (response.isSuccessful()) {
                    barangItems = response.body().getDataBarang();

                    List<String> listSpinnerBarang = new ArrayList<String>();

                    for (int i = 0; i < barangItems.size(); i++) {
                        String nama_barang = barangItems.get(i).getNamaBarang();
                        String jenis_barang = barangItems.get(i).getJenisBarang();
                        String ukuran = barangItems.get(i).getUkuranBarang();

                        listSpinnerBarang.add(nama_barang + " , " + jenis_barang +", "+ukuran);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahPembelianActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinnerBarang);
                    spinnerTambahNamaBarangPembelian.setAdapter(adapter);
                } else {
                    Toast.makeText(TambahPembelianActivity.this, "GAGAL MENGAMBIL DATA SPINNER", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataBarang> call, Throwable t) {
                Toast.makeText(TambahPembelianActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(TambahPembelianActivity.this, PembelianActivity.class));
        finish();
    }
}