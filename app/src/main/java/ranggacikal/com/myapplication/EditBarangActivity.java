package ranggacikal.com.myapplication;

import android.app.Dialog;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataJenisBarangItem;
import ranggacikal.com.myapplication.model.ResponseDataJenisBarang;
import ranggacikal.com.myapplication.model.ResponseEditBarang;
import ranggacikal.com.myapplication.model.ResponseEditImageBarang;
import ranggacikal.com.myapplication.model.ResponseHapusBarang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditBarangActivity extends AppCompatActivity {

    @BindView(R.id.img_edit_barang)
    CircleImageView imgEditBarang;
    @BindView(R.id.btn_edit_gambar_barang)
    Button btnEditGambarBarang;
    @BindView(R.id.btn_pilih_gambar_edit_barang)
    Button btnPilihGambarEditBarang;
    @BindView(R.id.btn_edit_upload_gambar_barang)
    Button btnEditUploadGambarBarang;
    @BindView(R.id.edt_edit_kode_barang)
    EditText edtEditKodeBarang;
    @BindView(R.id.edt_edit_nama_barang)
    EditText edtEditNamaBarang;
    @BindView(R.id.edt_edit_harga_beli_barang)
    EditText edtEditHargaBeliBarang;
    @BindView(R.id.edt_edit_harga_jual_barang)
    EditText edtEditHargaJualBarang;
    @BindView(R.id.edt_edit_stock_barang)
    EditText edtEditStockBarang;
    @BindView(R.id.edt_edit_jenis_barang)
    EditText edtEditJenisBarang;
    @BindView(R.id.btn_edit_jenis_barang)
    LinearLayout btnEditJenisBarang;
    @BindView(R.id.btn_update_barang)
    TextView btnUpdateBarang;

    public static final String EXTRA_ID_BARANG = "extraIdBarang";
    public static final String EXTRA_KODE_BARANG = "extraKodeBarang";
    public static final String EXTRA_NAMA_BARANG = "extraNamaBarang";
    public static final String EXTRA_HARGA_BELI_BARANG = "extraHargaBeliBarang";
    public static final String EXTRA_HARGA_JUAL_BARANG = "extraHargaJualBarang";
    public static final String EXTRA_STOK_BARANG = "extraStokBarang";
    public static final String EXTRA_ID_JENIS_BARANG = "extraIdJenisBarang";
    public static final String EXTRA_JENIS_BARANG = "extraJenisBarang";
    public static final String EXTRA_UKURAN_BARANG = "extraUkuranBarang";
    public static final String EXTRA_IMAGE_BARANG = "extraImageBarang";

    List<DataJenisBarangItem> jenisBarangItems;
    @BindView(R.id.spinner_edit_jenis_barang)
    Spinner spinnerEditJenisBarang;
    String id_jenis_barang;
    @BindView(R.id.edt_edit_jenis_barang_spinner)
    EditText edtEditJenisBarangSpinner;
    @BindView(R.id.btn_hapus_barang)
    TextView btnHapusBarang;

    Bitmap bitmap;
    private static final int IMG_REQUEST = 777;
    @BindView(R.id.edt_edit_ukuran_barang)
    EditText edtEditUkuranBarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_edit_barang);
        ButterKnife.bind(this);

        final String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_BARANG);

        Glide.with(EditBarangActivity.this)
                .load(imageUrl)
                .error(R.mipmap.ic_launcher)
                .into(imgEditBarang);

        initSpinner();

        edtEditKodeBarang.setText(getIntent().getStringExtra(EXTRA_KODE_BARANG));
        edtEditNamaBarang.setText(getIntent().getStringExtra(EXTRA_NAMA_BARANG));
        edtEditHargaBeliBarang.setText(getIntent().getStringExtra(EXTRA_HARGA_BELI_BARANG));
        edtEditHargaJualBarang.setText(getIntent().getStringExtra(EXTRA_HARGA_JUAL_BARANG));
        edtEditStockBarang.setText(getIntent().getStringExtra(EXTRA_STOK_BARANG));
        edtEditJenisBarang.setText(getIntent().getStringExtra(EXTRA_JENIS_BARANG));
        edtEditUkuranBarang.setText(getIntent().getStringExtra(EXTRA_UKURAN_BARANG));


        btnEditGambarBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPilihGambarEditBarang.setVisibility(View.VISIBLE);
                btnEditUploadGambarBarang.setVisibility(View.VISIBLE);
                btnEditGambarBarang.setVisibility(View.GONE);
            }
        });

        btnPilihGambarEditBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        btnEditUploadGambarBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadEditGambar();
            }
        });

        btnEditJenisBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerEditJenisBarang.setVisibility(View.VISIBLE);
                btnEditJenisBarang.setVisibility(View.GONE);
                edtEditJenisBarang.setVisibility(View.GONE);
            }
        });

        spinnerEditJenisBarang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                id_jenis_barang = jenisBarangItems.get(position).getIdJenisBarang();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnUpdateBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateBarang();
            }
        });

        btnHapusBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HapusBarang();
            }
        });

    }

    private void UploadEditGambar() {

        int id_barang = Integer.parseInt(getIntent().getStringExtra(EXTRA_ID_BARANG));
        String image_barang = imageToString();

        ConfigRetrofit.service.EditImageBarang(id_barang, image_barang).enqueue(new Callback<ResponseEditImageBarang>() {
            @Override
            public void onResponse(Call<ResponseEditImageBarang> call, Response<ResponseEditImageBarang> response) {
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();

                    if (status == 1) {
                        Toast.makeText(EditBarangActivity.this, "Edit Gambar Berhasil", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditBarangActivity.this, "Edit Gambar Gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditBarangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditImageBarang> call, Throwable t) {
                Toast.makeText(EditBarangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void pilihGambar() {

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
                imgEditBarang.setImageBitmap(bitmap);
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
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
        }
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void HapusBarang() {

        final Dialog dialogHapus = new Dialog(EditBarangActivity.this);
        dialogHapus.setContentView(R.layout.dialog_konfirmasi_hapus);
        final TextView txtIya = dialogHapus.findViewById(R.id.text_iya_hapus);
        final TextView txtTidak = dialogHapus.findViewById(R.id.text_tidak_hapus);
        dialogHapus.show();

        txtIya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HapusData();
            }
        });

        txtTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHapus.dismiss();
            }
        });

    }

    private void HapusData() {

        String id = getIntent().getStringExtra(EXTRA_ID_BARANG);

        ConfigRetrofit.service.HapusBarang(id).enqueue(new Callback<ResponseHapusBarang>() {
            @Override
            public void onResponse(Call<ResponseHapusBarang> call, Response<ResponseHapusBarang> response) {
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status == 1) {
                        Toast.makeText(EditBarangActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditBarangActivity.this, BarangAdminActivity.class));
                        finish();
                    } else {
                        Toast.makeText(EditBarangActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditBarangActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusBarang> call, Throwable t) {
                Toast.makeText(EditBarangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateBarang() {

        int id_barang = Integer.parseInt(getIntent().getStringExtra(EXTRA_ID_BARANG));
        String kode_barang = edtEditKodeBarang.getText().toString();
        String nama_barang = edtEditNamaBarang.getText().toString();
        String harga_beli = edtEditHargaBeliBarang.getText().toString();
        String harga_jual = edtEditHargaJualBarang.getText().toString();
        String stok = edtEditStockBarang.getText().toString();
        String ukuran_barang = edtEditUkuranBarang.getText().toString();
        int jenis_barang;
        if (id_jenis_barang == null) {
            jenis_barang = Integer.parseInt(getIntent().getStringExtra(EXTRA_ID_JENIS_BARANG));
        } else {
            jenis_barang = Integer.parseInt(id_jenis_barang);
        }


        if (kode_barang.isEmpty()) {
            edtEditKodeBarang.setError("Kode Barang Tidak Boleh Kosong");
            edtEditKodeBarang.requestFocus();
            return;
        }

        if (nama_barang.isEmpty()) {
            edtEditNamaBarang.setError("Nama Barang Tidak Boleh Kosong");
            edtEditNamaBarang.requestFocus();
            return;
        }

        if (harga_beli.isEmpty()) {
            edtEditHargaBeliBarang.setError("Harga Beli Tidak Boleh Kosong");
            edtEditHargaBeliBarang.requestFocus();
            return;
        }

        if (harga_jual.isEmpty()) {
            edtEditHargaJualBarang.setError("Harga Jual Tidak Boleh Kosong");
            edtEditHargaJualBarang.requestFocus();
            return;
        }

        if (stok.isEmpty()) {
            edtEditStockBarang.setError("Stock Tidak Boleh Kosong");
            edtEditStockBarang.requestFocus();
            return;
        }

        if (ukuran_barang.isEmpty()){
            edtEditUkuranBarang.setError("Ukuran Barang Tidak Boleh Kosong");
            edtEditUkuranBarang.requestFocus();
            return;
        }

        int hargaBeli = Integer.parseInt(harga_beli);
        int hargaJual = Integer.parseInt(harga_jual);
        int stok2 = Integer.parseInt(stok);

        ConfigRetrofit.service.EditBarang(id_barang, kode_barang, nama_barang, hargaBeli, hargaJual, stok2, jenis_barang, ukuran_barang).enqueue(new Callback<ResponseEditBarang>() {
            @Override
            public void onResponse(Call<ResponseEditBarang> call, Response<ResponseEditBarang> response) {
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();

                    if (status == 1) {
                        Toast.makeText(EditBarangActivity.this, "Edit Barang Sukses", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditBarangActivity.this, BarangAdminActivity.class));
                        finish();
                    } else {
                        Toast.makeText(EditBarangActivity.this, "Edit Barang Gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditBarangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditBarang> call, Throwable t) {
                Toast.makeText(EditBarangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditBarangActivity.this, android.R.layout.simple_spinner_dropdown_item, listSpinner);
                    spinnerEditJenisBarang.setAdapter(adapter);
                } else {
                    Toast.makeText(EditBarangActivity.this, "Spinner Gagal Mengambil Data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataJenisBarang> call, Throwable t) {
                Toast.makeText(EditBarangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditBarangActivity.this, BarangAdminActivity.class));
        finish();
    }
}