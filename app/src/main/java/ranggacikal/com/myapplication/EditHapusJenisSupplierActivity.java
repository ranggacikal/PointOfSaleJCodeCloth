package ranggacikal.com.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.ResponseEditJenisSupplier;
import ranggacikal.com.myapplication.model.ResponseHapusJenisSupplier;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditHapusJenisSupplierActivity extends AppCompatActivity {

    @BindView(R.id.text_id_jenis_supplier_edit)
    TextView textIdJenisSupplierEdit;
    @BindView(R.id.edt_edithapus_jenis_supplier)
    EditText edtEdithapusJenisSupplier;
    @BindView(R.id.btn_update_jenis_supplier)
    Button btnUpdateJenisSupplier;
    @BindView(R.id.btn_hapus_jenis_supplier)
    Button btnHapusJenisSupplier;

    public static final String EXTRA_ID_JENIS_SUPPLIER = "extraIdJenisSupplier";
    public static final String EXTRA_JENIS_SUPPLIER = "extraJenisSupplier";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_edit_hapus_jenis_supplier);
        ButterKnife.bind(this);

        textIdJenisSupplierEdit.setText(getIntent().getStringExtra(EXTRA_ID_JENIS_SUPPLIER));
        edtEdithapusJenisSupplier.setText(getIntent().getStringExtra(EXTRA_JENIS_SUPPLIER));

        btnUpdateJenisSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateJenisSupplier();
            }
        });

        btnHapusJenisSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TampilDialogKonfirmasiHapus();
            }
        });
    }

    private void TampilDialogKonfirmasiHapus() {

        Dialog dialog = new Dialog(EditHapusJenisSupplierActivity.this);

        dialog.setContentView(R.layout.dialog_hapus_jenis_supplier);

        final TextView txtIya = dialog.findViewById(R.id.text_iya_hapus_jenis_supplier);
        final TextView txtTidak = dialog.findViewById(R.id.text_tidak_hapus_jenis_supplier);

        dialog.show();

        txtIya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HapusJenisSupplier();
                dialog.dismiss();
            }
        });

        txtTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void HapusJenisSupplier() {

        String id_jenis_supplier = getIntent().getStringExtra(EXTRA_ID_JENIS_SUPPLIER);

        ConfigRetrofit.service.HapusJenisSupplier(id_jenis_supplier).enqueue(new Callback<ResponseHapusJenisSupplier>() {
            @Override
            public void onResponse(Call<ResponseHapusJenisSupplier> call, Response<ResponseHapusJenisSupplier> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status==1){

                        Toast.makeText(EditHapusJenisSupplierActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditHapusJenisSupplierActivity.this, JenisSupplierActivity.class));
                        finish();

                    }else{
                        Toast.makeText(EditHapusJenisSupplierActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EditHapusJenisSupplierActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHapusJenisSupplier> call, Throwable t) {
                Toast.makeText(EditHapusJenisSupplierActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void UpdateJenisSupplier() {

        String id_jenis_supplier = getIntent().getStringExtra(EXTRA_ID_JENIS_SUPPLIER);
        String jenis_supplier = edtEdithapusJenisSupplier.getText().toString();

        if (jenis_supplier.isEmpty()){

            edtEdithapusJenisSupplier.setError("Jenis Supplier Tidak Boleh Kosong");
            edtEdithapusJenisSupplier.requestFocus();
            return;

        }

        ConfigRetrofit.service.EditJenisSupplier(id_jenis_supplier, jenis_supplier).enqueue(new Callback<ResponseEditJenisSupplier>() {
            @Override
            public void onResponse(Call<ResponseEditJenisSupplier> call, Response<ResponseEditJenisSupplier> response) {
                if (response.isSuccessful()){

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status==1){

                        Toast.makeText(EditHapusJenisSupplierActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditHapusJenisSupplierActivity.this, JenisSupplierActivity.class));
                        finish();
                    }else{
                        Toast.makeText(EditHapusJenisSupplierActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(EditHapusJenisSupplierActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditJenisSupplier> call, Throwable t) {
                Toast.makeText(EditHapusJenisSupplierActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditHapusJenisSupplierActivity.this, JenisSupplierActivity.class));
        finish();
    }
}