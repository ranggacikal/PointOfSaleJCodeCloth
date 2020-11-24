package ranggacikal.com.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.adapter.DetailPenjualanAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataDetailPenjualanItem;
import ranggacikal.com.myapplication.model.ResponseDetailPenjualan;
import ranggacikal.com.myapplication.model.ResponseEditStatusDetailPenjualan;
import ranggacikal.com.myapplication.model.ResponseEditStatusPenjualan;
import ranggacikal.com.myapplication.model.ResponseEditTotalPenjualan;
import ranggacikal.com.myapplication.model.ResponseHapusDetailPenjualan;
import ranggacikal.com.myapplication.model.ResponseHapusPenjualan;
import ranggacikal.com.myapplication.model.ResponseTotalDetailPenjualan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KeranjangActivity extends AppCompatActivity {

    public static final String EXTRA_ID_PENJUALAN = "extraIdPenjualan";
    @BindView(R.id.rv_detail_penjualan)
    RecyclerView rvDetailPenjualan;
    @BindView(R.id.text_total_pesanan)
    TextView textTotalPesanan;

    String id;
    String total;
    String total_harga;
    int jumlah_byr;
    @BindView(R.id.btn_bayar)
    Button btnBayar;
    @BindView(R.id.btn_simpan_pesanan)
    Button btnSimpanPesanan;
    @BindView(R.id.btn_batalkan_pesanan)
    Button btnBatalkanPesanan;

    int kembalian, totalInt;
    @BindView(R.id.edt_jumlah_bayar)
    EditText edtJumlahBayar;
    @BindView(R.id.btn_selesaikan_transaksi)
    Button btnSelesaikanTransaksi;
    @BindView(R.id.text_kembalian_pesanan)
    TextView textKembalianPesanan;
    @BindView(R.id.rv_kembalian)
    RelativeLayout rvKembalian;
    @BindView(R.id.btn_transaksi)
    Button btnTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_keranjang);
        ButterKnife.bind(this);

        loadRecyclerView();
        loadTotalHarga();



        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBayar.setVisibility(View.GONE);
                btnSimpanPesanan.setVisibility(View.GONE);
                btnBatalkanPesanan.setVisibility(View.GONE);
                edtJumlahBayar.setVisibility(View.VISIBLE);
                btnSelesaikanTransaksi.setVisibility(View.VISIBLE);
            }
        });

        btnSelesaikanTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlah_byr = Integer.parseInt(edtJumlahBayar.getText().toString());

                totalInt = Integer.parseInt(total);

                kembalian = jumlah_byr - totalInt;


                Locale localID = new Locale("in", "ID");
                NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
                textKembalianPesanan.setText(formatRupiah.format(kembalian));
                rvKembalian.setVisibility(View.VISIBLE);
                btnSelesaikanTransaksi.setVisibility(View.GONE);
                btnTransaksi.setVisibility(View.VISIBLE);
            }
        });

        btnTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TampilDialog();
            }
        });

        btnSimpanPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpanPesanan();
                startActivity(new Intent(KeranjangActivity.this, PointOfSaleActivity.class));
                finish();
            }
        });

        btnBatalkanPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HapusDetailPenjualan();
                HapusPenjualan();
                startActivity(new Intent(KeranjangActivity.this, PointOfSaleActivity.class));
                finish();
            }
        });
    }

    private void HapusPenjualan() {

        ConfigRetrofit.service.HapusPenjualan(id).enqueue(new Callback<ResponseHapusPenjualan>() {
            @Override
            public void onResponse(Call<ResponseHapusPenjualan> call, Response<ResponseHapusPenjualan> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {
                        startActivity(new Intent(KeranjangActivity.this, PointOfSaleActivity.class));
                        finish();
                    } else {
                        Toast.makeText(KeranjangActivity.this, "Gagal Menghapus Pesanan", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(KeranjangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusPenjualan> call, Throwable t) {
                Toast.makeText(KeranjangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void HapusDetailPenjualan() {

        ConfigRetrofit.service.HapusDetailPenjualan(id).enqueue(new Callback<ResponseHapusDetailPenjualan>() {
            @Override
            public void onResponse(Call<ResponseHapusDetailPenjualan> call, Response<ResponseHapusDetailPenjualan> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {

                        Toast.makeText(KeranjangActivity.this, "Batalkan Pesanan Berhasil", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(KeranjangActivity.this, "Batalkan Pesanan Gagal", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(KeranjangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusDetailPenjualan> call, Throwable t) {
                Toast.makeText(KeranjangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void SimpanPesanan() {

        ConfigRetrofit.service.TotalPenjualan(id, total).enqueue(new Callback<ResponseEditTotalPenjualan>() {
            @Override
            public void onResponse(Call<ResponseEditTotalPenjualan> call, Response<ResponseEditTotalPenjualan> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {

                        Toast.makeText(KeranjangActivity.this, "Berhasil Simpan Pemesanan", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(KeranjangActivity.this, "GAGAL SIMPAN PESANAN", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(KeranjangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditTotalPenjualan> call, Throwable t) {
                Toast.makeText(KeranjangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void TampilDialog() {

        Dialog dialogBayar = new Dialog(KeranjangActivity.this);

        dialogBayar.setContentView(R.layout.dialog_pembayaran);

        final TextView txtIya = dialogBayar.findViewById(R.id.text_iya_dialog_selesai);
        final TextView txtTidak = dialogBayar.findViewById(R.id.text_tidak_dialog_selesai);


        dialogBayar.show();

        txtTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBayar.dismiss();
            }
        });

        txtIya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditStatusDetailPembelian();
                EditStatusPenjualan();
                SimpanPesanan();
                TampilKwitansi();
            }
        });

    }

    private void TampilKwitansi() {

        String id_penjualan = getIntent().getStringExtra(EXTRA_ID_PENJUALAN);
        Intent intent = new Intent(KeranjangActivity.this, BuktiPenjualanActivity.class);
        intent.putExtra(BuktiPenjualanActivity.EXTRA_ID_PENJUALAN, id_penjualan);
        startActivity(intent);
        finish();

    }

    private void EditStatusDetailPembelian() {

        String status = "Selesai";

        ConfigRetrofit.service.EditStatusDetailPenjualan(id, status).enqueue(new Callback<ResponseEditStatusDetailPenjualan>() {
            @Override
            public void onResponse(Call<ResponseEditStatusDetailPenjualan> call, Response<ResponseEditStatusDetailPenjualan> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status == 1){
                        Toast.makeText(KeranjangActivity.this, "Transaksi Berhasil", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(KeranjangActivity.this, "Transaksi Gagal", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(KeranjangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditStatusDetailPenjualan> call, Throwable t) {
                Toast.makeText(KeranjangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void EditStatusPenjualan() {

        String status = "Selesai";

        ConfigRetrofit.service.EditStatusPenjualan(id, status).enqueue(new Callback<ResponseEditStatusPenjualan>() {
            @Override
            public void onResponse(Call<ResponseEditStatusPenjualan> call, Response<ResponseEditStatusPenjualan> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status == 1){

                        Toast.makeText(KeranjangActivity.this, "Pemesanan Berhasil", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(KeranjangActivity.this, "Pemesanan Gagal", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(KeranjangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditStatusPenjualan> call, Throwable t) {
                Toast.makeText(KeranjangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadTotalHarga() {

        id = getIntent().getStringExtra(EXTRA_ID_PENJUALAN);

        ConfigRetrofit.service.TotalDetailPenjualan(id).enqueue(new Callback<ResponseTotalDetailPenjualan>() {
            @Override
            public void onResponse(Call<ResponseTotalDetailPenjualan> call, Response<ResponseTotalDetailPenjualan> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();


                    if (status == 1) {

                        total = response.body().getTotalDetailPenjualan().getTotalHarga();
                        Locale localID = new Locale("in", "ID");
                        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
                        textTotalPesanan.setText(formatRupiah.format(Integer.parseInt(total)));
                        total_harga = formatRupiah.format(Integer.parseInt(total));

                    } else {
                        textTotalPesanan.setText("null/Terjadi Kesalahan");
                    }
                } else {
                    Toast.makeText(KeranjangActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTotalDetailPenjualan> call, Throwable t) {
                Toast.makeText(KeranjangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadRecyclerView() {

        id = getIntent().getStringExtra(EXTRA_ID_PENJUALAN);

        final ProgressDialog progDialog = ProgressDialog.show(KeranjangActivity.this, "", "Loading..", false);

        ConfigRetrofit.service.DetailPenjualan(id).enqueue(new Callback<ResponseDetailPenjualan>() {
            @Override
            public void onResponse(Call<ResponseDetailPenjualan> call, Response<ResponseDetailPenjualan> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {

                        List<DataDetailPenjualanItem> detailPenjualanList = response.body().getDataDetailPenjualan();
                        DetailPenjualanAdapter adapter = new DetailPenjualanAdapter(KeranjangActivity.this, detailPenjualanList);
                        rvDetailPenjualan.setAdapter(adapter);
                        rvDetailPenjualan.setLayoutManager(new LinearLayoutManager(KeranjangActivity.this));
                        progDialog.dismiss();

                    } else {
                        Toast.makeText(KeranjangActivity.this, "Anda Belum Memilih Barang", Toast.LENGTH_SHORT).show();
                        progDialog.dismiss();
                    }
                } else {
                    Toast.makeText(KeranjangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                    progDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailPenjualan> call, Throwable t) {
                Toast.makeText(KeranjangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progDialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(KeranjangActivity.this, TambahPenjualanActivity.class));
        finish();
    }
}