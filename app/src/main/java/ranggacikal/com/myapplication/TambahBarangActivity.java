package ranggacikal.com.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataJenisBarangItem;
import ranggacikal.com.myapplication.model.ResponseDataJenisBarang;
import ranggacikal.com.myapplication.model.ResponseTambahBarang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahBarangActivity extends AppCompatActivity {

    @BindView(R.id.edt_tambah_kode_barang)
    EditText edtTambahKodeBarang;
    @BindView(R.id.edt_tambah_nama_barang)
    EditText edtTambahNamaBarang;
    @BindView(R.id.edt_tambah_harga_beli_barang)
    EditText edtTambahHargaBeliBarang;
    @BindView(R.id.edt_tambah_harga_jual_barang)
    EditText edtTambahHargaJualBarang;
    @BindView(R.id.edt_tambah_stock_barang)
    EditText edtTambahStockBarang;
    @BindView(R.id.spinner_tambah_jenis_barang)
    Spinner spinnerTambahJenisBarang;
    @BindView(R.id.btn_simpan_tambah_barang)
    TextView btnSimpanTambahBarang;


    List<DataJenisBarangItem> jenisBarangItems;
    @BindView(R.id.img_tambah_barang)
    ImageView imgTambahBarang;
    @BindView(R.id.btn_select_image_tambah_barang)
    Button btnSelectImageTambahBarang;

    private static final int IMG_REQUEST = 777;
    @BindView(R.id.edt_tambah_ukuran_barang)
    EditText edtTambahUkuranBarang;
    private Bitmap bitmap;
    private int id_jenis_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_tambah_barang);
        ButterKnife.bind(this);

        initSpinner();
        

        btnSelectImageTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PilihGambar();
            }
        });

        btnSimpanTambahBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpanBarang();
            }
        });

        spinnerTambahJenisBarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                id_jenis_barang = Integer.parseInt(jenisBarangItems.get(position).getIdJenisBarang());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SimpanBarang() {

        String kode_barang = edtTambahKodeBarang.getText().toString();
        String nama_barang = edtTambahNamaBarang.getText().toString();
        String harga_beli = edtTambahHargaBeliBarang.getText().toString();
        String harga_jual = edtTambahHargaJualBarang.getText().toString();
        String stok = edtTambahStockBarang.getText().toString();
        String ukuran_barang = edtTambahUkuranBarang.getText().toString();
        String image_barang = imageToString();

        if (kode_barang.isEmpty()) {
            edtTambahKodeBarang.setError("Kode Barang Tidak Boleh Kosong !");
            edtTambahKodeBarang.requestFocus();
            return;
        }

        if (nama_barang.isEmpty()) {
            edtTambahNamaBarang.setError("Nama Barang Tidak Boleh Kosong !");
            edtTambahNamaBarang.requestFocus();
            return;
        }

        if (harga_beli.isEmpty()) {
            edtTambahHargaBeliBarang.setError("Harga Beli Tidak Boleh Kosong !");
            edtTambahHargaBeliBarang.requestFocus();
            return;
        }

        if (harga_jual.isEmpty()) {
            edtTambahHargaJualBarang.setError("Harga Jual Tidak Boleh Kosong !");
            edtTambahHargaJualBarang.requestFocus();
            return;
        }

        if (stok.isEmpty()) {
            edtTambahStockBarang.setError("Stock Awal Tidak Boleh 0");
            edtTambahStockBarang.requestFocus();
            return;
        }

        if (ukuran_barang.isEmpty()){
            edtTambahUkuranBarang.setError("Ukuran Barang Tidak Boleh Kosong");
            edtTambahUkuranBarang.requestFocus();
            return;
        }

        int hargabeli = Integer.parseInt(harga_beli);
        int hargajual = Integer.parseInt(harga_jual);
        int stok2 = Integer.parseInt(stok);

        final ProgressDialog progDialog = ProgressDialog.show(TambahBarangActivity.this, "", "Loading..", false);
        ConfigRetrofit.service.TambahBarang(kode_barang, nama_barang, hargabeli, hargajual, stok2, id_jenis_barang, ukuran_barang, image_barang)
                .enqueue(new Callback<ResponseTambahBarang>() {
                    @Override
                    public void onResponse(Call<ResponseTambahBarang> call, Response<ResponseTambahBarang> response) {
                        int status = response.body().getStatus();
                        String pesan = response.body().getPesan();
                        boolean sukses = response.body().isSukses();

                        if (status == 1 && sukses == true) {
                            progDialog.dismiss();
                            Toast.makeText(TambahBarangActivity.this, pesan, Toast.LENGTH_SHORT).show();
                            edtTambahNamaBarang.setText("");
                            edtTambahKodeBarang.setText("");
                            edtTambahHargaBeliBarang.setText("");
                            edtTambahHargaJualBarang.setText("");
                            edtTambahStockBarang.setText("");
                            imgTambahBarang.setImageResource(R.mipmap.ic_logo);
                        }else{
                            progDialog.dismiss();
                            Toast.makeText(TambahBarangActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseTambahBarang> call, Throwable t) {
                        progDialog.dismiss();
                        Toast.makeText(TambahBarangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void PilihGambar() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgTambahBarang.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        } else if (bitmap == null) {
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void initSpinner() {

        ConfigRetrofit.service.JenisBarang().enqueue(new Callback<ResponseDataJenisBarang>() {
            @Override
            public void onResponse(Call<ResponseDataJenisBarang> call, Response<ResponseDataJenisBarang> response) {
                if (response.isSuccessful()) {
                    jenisBarangItems = response.body().getDataJenisBarang();
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < jenisBarangItems.size(); i++) {

                        listSpinner.add(jenisBarangItems.get(i).getJenisBarang());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahBarangActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinner);
                    spinnerTambahJenisBarang.setAdapter(adapter);
                } else {
                    Toast.makeText(TambahBarangActivity.this, "Spinner Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataJenisBarang> call, Throwable t) {
                Toast.makeText(TambahBarangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(TambahBarangActivity.this, BarangAdminActivity.class));
        finish();
    }
}