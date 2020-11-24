package ranggacikal.com.myapplication;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.SharedPreference.SharedPreferencedConfig;
import ranggacikal.com.myapplication.adapter.LaporanAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataLaporanItem;
import ranggacikal.com.myapplication.model.ResponseLaporanByBulan;
import ranggacikal.com.myapplication.model.ResponseTotalPemasukan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporanActivity extends AppCompatActivity {

    @BindView(R.id.spinner_laporan_bulan)
    Spinner spinnerLaporanBulan;
    @BindView(R.id.spinner_laporan_tahun)
    Spinner spinnerLaporanTahun;
    @BindView(R.id.btn_filter_laporan)
    Button btnFilterLaporan;
    @BindView(R.id.rv_laporan)
    RecyclerView rvLaporan;
    @BindView(R.id.text_total_pemasukan)
    TextView textTotalPemasukan;
    @BindView(R.id.linear_filter_laporan)
    LinearLayout linearFilterLaporan;
    @BindView(R.id.linear_header_laporan)
    LinearLayout linearHeaderLaporan;
    @BindView(R.id.relative_total_pemasukan)
    RelativeLayout relativeTotalPemasukan;

    String bulanSpinner, tahunSpinner, bulan;
    @BindView(R.id.btn_cetak_laporan)
    Button btnCetakLaporan;

    private SharedPreferencedConfig preferencedConfig;

    private Bitmap bitmap;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_laporan);
        ButterKnife.bind(this);

        context = this;
        ActivityCompat.requestPermissions(LaporanActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        preferencedConfig = new SharedPreferencedConfig(this);

        btnFilterLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadRecyclerLaporan();

            }
        });

        btnCetakLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("size", " " + linearFilterLaporan.getWidth() + "  " + linearFilterLaporan.getHeight());
                bitmap = loadBitmapFromView(linearFilterLaporan, linearFilterLaporan.getWidth(), linearFilterLaporan.getHeight());
                createPdf();
            }
        });
    }

    private void createPdf() {

        String bulan = spinnerLaporanBulan.getSelectedItem().toString();
        String tahun = spinnerLaporanTahun.getSelectedItem().toString();
        String namafile = bulan+"-"+tahun;
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.densityDpi;
        float width = displaymetrics.densityDpi;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/" + namafile + ".pdf";
        File filePath;
        filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            Log.e("CetakBuktiActivity", e.getMessage());
        }

        // close the document
        document.close();
        Toast.makeText(this, "PDF is created!!!", Toast.LENGTH_SHORT).show();

        openGeneratedPDF();

    }

    private void openGeneratedPDF() {

        String bulan = spinnerLaporanBulan.getSelectedItem().toString();
        String tahun = spinnerLaporanTahun.getSelectedItem().toString();
        String namafile = bulan+"-"+tahun;
        File file = new File("/sdcard/" + namafile + ".pdf");
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(LaporanActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }

    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = null;
        b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }


    private void GetTotalPemasukan() {

        ConfigRetrofit.service.TotalPemasulan(bulan).enqueue(new Callback<ResponseTotalPemasukan>() {
            @Override
            public void onResponse(Call<ResponseTotalPemasukan> call, Response<ResponseTotalPemasukan> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();


                    if (status == 1) {

                        int total = Integer.parseInt(response.body().getPemasukanLaporan().getTotal());
                        Locale localID = new Locale("in", "ID");
                        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
                        textTotalPemasukan.setText(formatRupiah.format(total));

                    } else {
                        textTotalPemasukan.setText("0");
                    }
                } else {
                    Toast.makeText(LaporanActivity.this, "Gagal Mengambil Total Pemasukan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTotalPemasukan> call, Throwable t) {
                Toast.makeText(LaporanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadRecyclerLaporan() {

        bulanSpinner = spinnerLaporanBulan.getSelectedItem().toString();
        tahunSpinner = spinnerLaporanTahun.getSelectedItem().toString();
        bulan = bulanSpinner + "-" + tahunSpinner;

        ConfigRetrofit.service.LaporanPenjualan(bulan).enqueue(new Callback<ResponseLaporanByBulan>() {
            @Override
            public void onResponse(Call<ResponseLaporanByBulan> call, Response<ResponseLaporanByBulan> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {
                        List<DataLaporanItem> laporanList = response.body().getDataLaporan();
                        LaporanAdapter adapter = new LaporanAdapter(LaporanActivity.this, laporanList);
                        rvLaporan.setAdapter(adapter);
                        rvLaporan.setLayoutManager(new LinearLayoutManager(LaporanActivity.this));
                        linearHeaderLaporan.setVisibility(View.VISIBLE);
                        relativeTotalPemasukan.setVisibility(View.VISIBLE);
                        btnCetakLaporan.setVisibility(View.VISIBLE);
                        GetTotalPemasukan();
                    } else {
                        Toast.makeText(LaporanActivity.this, "Belum Ada Laporan Untuk Bulan Dan Tahun Yang Anda Pilih",
                                Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LaporanActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLaporanByBulan> call, Throwable t) {
                Toast.makeText(LaporanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        String level = preferencedConfig.getPreferenceLevelUser();

        if (level.equals("Kasir") || level.equals("kasir")) {
            startActivity(new Intent(LaporanActivity.this, MainActivity.class));
            finish();
        } else if (level.equals("Admin") || level.equals("admin")) {
            startActivity(new Intent(LaporanActivity.this, PenjualanAdminActivity.class));
        }
    }
}