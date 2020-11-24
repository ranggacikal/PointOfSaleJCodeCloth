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
import ranggacikal.com.myapplication.adapter.BuktiPenjualanAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataDetailPenjualanItem;
import ranggacikal.com.myapplication.model.ResponseDetailPenjualan;
import ranggacikal.com.myapplication.model.ResponseTotalDetailPenjualan;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuktiPenjualanActivity extends AppCompatActivity {

    @BindView(R.id.recycler_bukti_pembelian)
    RecyclerView recyclerBuktiPembelian;
    @BindView(R.id.linear_kwitansi)
    LinearLayout linearKwitansi;

    public static final String EXTRA_ID_PENJUALAN = "extraIdPenjualan";

    String id_penjualan;
    @BindView(R.id.btn_cetak_bukti)
    Button btnCetakBukti;
    @BindView(R.id.text_total_bukti_penjualan)
    TextView textTotalBuktiPenjualan;

    private Bitmap bitmap;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_bukti_penjualan);
        ButterKnife.bind(this);

        context = this;
        ActivityCompat.requestPermissions(BuktiPenjualanActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        id_penjualan = getIntent().getStringExtra(EXTRA_ID_PENJUALAN);

        loadRecyclerBuktiPenjualan();

        btnCetakBukti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("size", " " + linearKwitansi.getWidth() + "  " + linearKwitansi.getHeight());
                bitmap = loadBitmapFromView(linearKwitansi, linearKwitansi.getWidth(), linearKwitansi.getHeight());
                createPdf();
            }
        });
    }

    private void createPdf() {

        String namafile = getIntent().getStringExtra(EXTRA_ID_PENJUALAN);
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

        String namafile = getIntent().getStringExtra(EXTRA_ID_PENJUALAN);
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
                Toast.makeText(BuktiPenjualanActivity.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
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

    private void loadRecyclerBuktiPenjualan() {

        ConfigRetrofit.service.DetailPenjualan(id_penjualan).enqueue(new Callback<ResponseDetailPenjualan>() {
            @Override
            public void onResponse(Call<ResponseDetailPenjualan> call, Response<ResponseDetailPenjualan> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();

                    if (status == 1) {
                        List<DataDetailPenjualanItem> buktiPenjualanList = response.body().getDataDetailPenjualan();
                        BuktiPenjualanAdapter adapter = new BuktiPenjualanAdapter(BuktiPenjualanActivity.this, buktiPenjualanList);
                        recyclerBuktiPembelian.setAdapter(adapter);
                        recyclerBuktiPembelian.setLayoutManager(new LinearLayoutManager(BuktiPenjualanActivity.this));
                        GetTotalPenjualan();
                    } else {
                        Toast.makeText(BuktiPenjualanActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BuktiPenjualanActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDetailPenjualan> call, Throwable t) {
                Toast.makeText(BuktiPenjualanActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void GetTotalPenjualan() {

        ConfigRetrofit.service.TotalDetailPenjualan(id_penjualan).enqueue(new Callback<ResponseTotalDetailPenjualan>() {
            @Override
            public void onResponse(Call<ResponseTotalDetailPenjualan> call, Response<ResponseTotalDetailPenjualan> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();

                    if (status == 1){
                        int total = Integer.parseInt(response.body().getTotalDetailPenjualan().getTotalHarga());
                        Locale localID = new Locale("in", "ID");
                        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
                        textTotalBuktiPenjualan.setText(formatRupiah.format(total));
                    }else{
                        Toast.makeText(context, "Tidak Ada Hasil", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseTotalDetailPenjualan> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BuktiPenjualanActivity.this, PointOfSaleActivity.class));
        finish();
    }
}