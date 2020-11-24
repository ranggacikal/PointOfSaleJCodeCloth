package ranggacikal.com.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import ranggacikal.com.myapplication.model.ResponseEditPembelian;
import ranggacikal.com.myapplication.model.ResponseHapusPembelian;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditHapusPembelianActivity extends AppCompatActivity {

    @BindView(R.id.spinner_edit_barang_pembelian)
    Spinner spinnerEditBarangPembelian;
    @BindView(R.id.edt_edit_barang_pembelian)
    EditText edtEditBarangPembelian;
    @BindView(R.id.edt_edit_barang_pembelian_spinner)
    EditText edtEditBarangPembelianSpinner;
    @BindView(R.id.btn_edit_barang_pembelian)
    LinearLayout btnEditBarangPembelian;
    @BindView(R.id.edt_edit_tanggal_pembelian)
    EditText edtEditTanggalPembelian;
    @BindView(R.id.linear_edit_pilih_tanggal_pembelian)
    LinearLayout linearEditPilihTanggalPembelian;
    @BindView(R.id.edt_edit_jumlah_pembelian)
    EditText edtEditJumlahPembelian;
    @BindView(R.id.edt_edit_total_harga_pembelian)
    EditText edtEditTotalHargaPembelian;
    @BindView(R.id.spinner_edit_supplier_pembelian)
    Spinner spinnerEditSupplierPembelian;
    @BindView(R.id.edt_edit_supplier_pembelian)
    EditText edtEditSupplierPembelian;
    @BindView(R.id.edt_edit_supplier_spinner)
    EditText edtEditSupplierSpinner;
    @BindView(R.id.btn_edit_supplier_pembelian)
    LinearLayout btnEditSupplierPembelian;

    public static final String EXTRA_ID_PEMBELIAN = "extraIdPembelian";
    public static final String EXTRA_ID_BARANG_PEMBELIAN = "extraIdBarang";
    public static final String EXTRA_ID_SUPPLIER_PEMBELIAN = "extraIdSupplier";
    public static final String EXTRA_NAMA_BARANG_PEMBELIAN = "extraNamaBarangPembelian";
    public static final String EXTRA_TANGGAL_PEMBELIAN = "extraTanggalPembelian";
    public static final String EXTRA_JUMLAH_PEMBELIAN = "extraJumlahPembelian";
    public static final String EXTRA_TOTAL_HARGA_PEMBELIAN = "extraTotalHargaPembelian";
    public static final String EXTRA_SUPPLIER_PEMBELIAN = "extraSupplierPembelian";
    @BindView(R.id.btn_update_pembelian)
    TextView btnUpdatePembelian;
    @BindView(R.id.btn_hapus_pembelian)
    TextView btnHapusPembelian;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    List<DataBarangItem> barangItems;
    List<DataSupplierItem> supplierItems;

    String id_barang, id_supplier;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_edit_hapus_pembelian);
        ButterKnife.bind(this);

        preferencedConfig = new SharedPreferencedConfig(this);

        edtEditBarangPembelian.setText(getIntent().getStringExtra(EXTRA_ID_PEMBELIAN));
        edtEditTanggalPembelian.setText(getIntent().getStringExtra(EXTRA_TANGGAL_PEMBELIAN));
        edtEditJumlahPembelian.setText(getIntent().getStringExtra(EXTRA_JUMLAH_PEMBELIAN));
        edtEditTotalHargaPembelian.setText(getIntent().getStringExtra(EXTRA_TOTAL_HARGA_PEMBELIAN));
        edtEditSupplierPembelian.setText(getIntent().getStringExtra(EXTRA_SUPPLIER_PEMBELIAN));

        initSpinnerBarang();
        initSpinnerSupplier();

        btnEditBarangPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEditBarangPembelian.setVisibility(View.GONE);
                spinnerEditBarangPembelian.setVisibility(View.VISIBLE);
                btnEditBarangPembelian.setVisibility(View.GONE);
            }
        });

        btnEditSupplierPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtEditSupplierPembelian.setVisibility(View.GONE);
                spinnerEditSupplierPembelian.setVisibility(View.VISIBLE);
                btnEditSupplierPembelian.setVisibility(View.GONE);
            }
        });

        dateFormatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        linearEditPilihTanggalPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btnUpdatePembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateBarang();
            }
        });

        spinnerEditBarangPembelian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_barang = barangItems.get(position).getIdBarang();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerEditSupplierPembelian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_supplier = supplierItems.get(position).getIdSupplier();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnHapusPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HapusPembelian();
            }
        });

    }

    private void HapusPembelian() {

        final Dialog dialog = new Dialog(EditHapusPembelianActivity.this);
        dialog.setContentView(R.layout.dialog_konfirmasi_hapus_pembelian);

        final TextView txtIya = dialog.findViewById(R.id.text_iya_hapus_pembelian);
        final TextView txtTidak = dialog.findViewById(R.id.text_tidak_hapus_pembelian);
        dialog.show();

        txtIya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HapusData();
            }
        });

        txtTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void HapusData() {

        String id = getIntent().getStringExtra(EXTRA_ID_PEMBELIAN);

        ConfigRetrofit.service.HapusPembelian(id).enqueue(new Callback<ResponseHapusPembelian>() {
            @Override
            public void onResponse(Call<ResponseHapusPembelian> call, Response<ResponseHapusPembelian> response) {
                if (response.isSuccessful()){
                    int status = response.body().getStatus();

                    if (status==1){
                        Toast.makeText(EditHapusPembelianActivity.this, "Hapus Data Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditHapusPembelianActivity.this, PembelianActivity.class));
                        finish();
                    }else{
                        Toast.makeText(EditHapusPembelianActivity.this, "GAGAL Hapus Data", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EditHapusPembelianActivity.this, "Terjadi Kesalahan..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusPembelian> call, Throwable t) {
                Toast.makeText(EditHapusPembelianActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateBarang() {

        int id = Integer.parseInt(getIntent().getStringExtra(EXTRA_ID_PEMBELIAN));
        String tanggal_pembelian = edtEditTanggalPembelian.getText().toString();
        String jumlah = edtEditJumlahPembelian.getText().toString();
        String total_harga = edtEditTotalHargaPembelian.getText().toString();
        String id_user = preferencedConfig.getPreferenceIdUser();
        int idBarang, idSupplier;

        if (id_barang == null) {
            idBarang = Integer.parseInt(getIntent().getStringExtra(EXTRA_ID_BARANG_PEMBELIAN));
        } else {
            idBarang = Integer.parseInt(id_barang);
        }

        if (id_supplier == null) {
            idSupplier = Integer.parseInt(getIntent().getStringExtra(EXTRA_ID_SUPPLIER_PEMBELIAN));
        } else {
            idSupplier = Integer.parseInt(id_supplier);
        }

        if (tanggal_pembelian.isEmpty()) {
            edtEditTanggalPembelian.setError("Tanggal Pembelian Tidak Boleh Kosong");
            edtEditTanggalPembelian.requestFocus();
            return;
        }

        if (jumlah.isEmpty()) {
            edtEditJumlahPembelian.setError("Jumlah Pembelian Tidak Boleh Kosong");
            edtEditJumlahPembelian.requestFocus();
            return;
        }

        if (total_harga.isEmpty()) {
            edtEditTotalHargaPembelian.setError("Total Harga Tidak boleh Kosong");
            edtEditTotalHargaPembelian.requestFocus();
            return;
        }

        int jumlah2 = Integer.parseInt(jumlah);
        int total_harga2 = Integer.parseInt(total_harga);

        ConfigRetrofit.service.EditPembelian(id, idBarang, tanggal_pembelian, jumlah2, total_harga2, idSupplier, id_user).enqueue(new Callback<ResponseEditPembelian>() {
            @Override
            public void onResponse(Call<ResponseEditPembelian> call, Response<ResponseEditPembelian> response) {
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();

                    if (status == 1) {
                        Toast.makeText(EditHapusPembelianActivity.this, "Berhasil Edit Data", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditHapusPembelianActivity.this, PembelianActivity.class));
                        finish();
                    } else {
                        Toast.makeText(EditHapusPembelianActivity.this, "GAGAL Edit Data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditHapusPembelianActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditPembelian> call, Throwable t) {
                Toast.makeText(EditHapusPembelianActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initSpinnerSupplier() {

        ConfigRetrofit.service.DataSupplier().enqueue(new Callback<ResponseDataSupplier>() {
            @Override
            public void onResponse(Call<ResponseDataSupplier> call, Response<ResponseDataSupplier> response) {
                if (response.isSuccessful()) {
                    supplierItems = response.body().getDataSupplier();

                    List<String> listSpinnerSupplier = new ArrayList<String>();

                    for (int i = 0; i < supplierItems.size(); i++) {
                        String nama_supplier = supplierItems.get(i).getNama();
                        String jenis_supplier = supplierItems.get(i).getJenisSupplier();

                        listSpinnerSupplier.add(nama_supplier + " , " + " ( " + jenis_supplier + " ) ");
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditHapusPembelianActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinnerSupplier);
                    spinnerEditSupplierPembelian.setAdapter(adapter);
                } else {
                    Toast.makeText(EditHapusPembelianActivity.this, "Gagal Memuat Spinner", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataSupplier> call, Throwable t) {
                Toast.makeText(EditHapusPembelianActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initSpinnerBarang() {

        ConfigRetrofit.service.DataBarang().enqueue(new Callback<ResponseDataBarang>() {
            @Override
            public void onResponse(Call<ResponseDataBarang> call, Response<ResponseDataBarang> response) {
                if (response.isSuccessful()) {
                    barangItems = response.body().getDataBarang();

                    List<String> listSpinnerBarang = new ArrayList<String>();

                    for (int i = 0; i < barangItems.size(); i++) {
                        String nama_barang = barangItems.get(i).getNamaBarang();
                        String jenis_barang = barangItems.get(i).getJenisBarang();

                        listSpinnerBarang.add(nama_barang + " , " + jenis_barang);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditHapusPembelianActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinnerBarang);
                    spinnerEditBarangPembelian.setAdapter(adapter);
                } else {
                    Toast.makeText(EditHapusPembelianActivity.this, "GAGAL MENGAMBIL DATA SPINNER", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataBarang> call, Throwable t) {
                Toast.makeText(EditHapusPembelianActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

                edtEditTanggalPembelian.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditHapusPembelianActivity.this, PembelianActivity.class));
        finish();
    }
}