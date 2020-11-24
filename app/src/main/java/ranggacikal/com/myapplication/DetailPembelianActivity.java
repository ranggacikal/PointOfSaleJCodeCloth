package ranggacikal.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPembelianActivity extends AppCompatActivity {

    @BindView(R.id.text_id_detail_pembelian)
    TextView textIdDetailPembelian;
    @BindView(R.id.text_tanggal_detail_pembelian)
    TextView textTanggalDetailPembelian;
    @BindView(R.id.text_nama_barang_detail_pembelian)
    TextView textNamaBarangDetailPembelian;
    @BindView(R.id.text_jumlah_detail_pembelian)
    TextView textJumlahDetailPembelian;
    @BindView(R.id.text_total_harga_detail_pembelian)
    TextView textTotalHargaDetailPembelian;
    @BindView(R.id.text_supplier_detail_pembelian)
    TextView textSupplierDetailPembelian;

    public static final String EXTRA_ID_PEMBELIAN = "extraIdPembelian";
    public static final String EXTRA_TANGGAL_PEMBELIAN = "extraTanggalPembelian";
    public static final String EXTRA_NAMA_BARANG_PEMBELIAN = "extraNamaBarangPembelian";
    public static final String EXTRA_JUMLAH_PEMBELIAN = "extraJumlahPembelian";
    public static final String EXTRA_TOTAL_HARGA_PEMBELIAN = "extraTotalHargaPembelian";
    public static final String EXTRA_SUPPLIER_PEMBELIAN = "extraSupplierPembelian";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_detail_pembelian);
        ButterKnife.bind(this);

        int total_harga = Integer.parseInt(getIntent().getStringExtra(EXTRA_TOTAL_HARGA_PEMBELIAN));
        Locale localID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
        textIdDetailPembelian.setText(getIntent().getStringExtra(EXTRA_ID_PEMBELIAN));
        textTanggalDetailPembelian.setText(getIntent().getStringExtra(EXTRA_TANGGAL_PEMBELIAN));
        textNamaBarangDetailPembelian.setText(getIntent().getStringExtra(EXTRA_NAMA_BARANG_PEMBELIAN));
        textJumlahDetailPembelian.setText(getIntent().getStringExtra(EXTRA_JUMLAH_PEMBELIAN));
        textTotalHargaDetailPembelian.setText(formatRupiah.format(total_harga));
        textSupplierDetailPembelian.setText(getIntent().getStringExtra(EXTRA_SUPPLIER_PEMBELIAN));


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DetailPembelianActivity.this, PembelianActivity.class));
        finish();
    }
}