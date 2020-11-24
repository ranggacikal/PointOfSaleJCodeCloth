package ranggacikal.com.myapplication;

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
import ranggacikal.com.myapplication.model.ResponseEditJenisBarang;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditHapusJenisBarangActivity extends AppCompatActivity {

    @BindView(R.id.text_id_jenis_barang_edit)
    TextView textIdJenisBarangEdit;
    @BindView(R.id.edt_edithapus_jenis_barang)
    EditText edtEdithapusJenisBarang;
    @BindView(R.id.btn_update_jenis_barang)
    Button btnUpdateJenisBarang;

    public static final String EXTRA_ID_JENIS_BARANG = "extraIdJenisBarang";
    public static final String EXTRA_JENIS_BARANG = "extraJenisBarang";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_edit_hapus_jenis_barang);
        ButterKnife.bind(this);

        textIdJenisBarangEdit.setText(getIntent().getStringExtra(EXTRA_ID_JENIS_BARANG));
        edtEdithapusJenisBarang.setText(getIntent().getStringExtra(EXTRA_JENIS_BARANG));

        btnUpdateJenisBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateJenisBarang();
            }
        });
    }

    private void UpdateJenisBarang() {

        String id = getIntent().getStringExtra(EXTRA_ID_JENIS_BARANG);
        String jenis_barang = edtEdithapusJenisBarang.getText().toString();

        if (jenis_barang.isEmpty()){
            edtEdithapusJenisBarang.setError("Jenis Barang Tidak Boleh Kosong");
            edtEdithapusJenisBarang.requestFocus();
            return;
        }

        ConfigRetrofit.service.EditJenisBarang(id, jenis_barang).enqueue(new Callback<ResponseEditJenisBarang>() {
            @Override
            public void onResponse(Call<ResponseEditJenisBarang> call, Response<ResponseEditJenisBarang> response) {
                if(response.isSuccessful()){
                    int status = response.body().getStatus();

                    if (status == 1){
                        Toast.makeText(EditHapusJenisBarangActivity.this, "Data Berhasil Di Update", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditHapusJenisBarangActivity.this, JenisBarangActivity.class));
                        finish();
                    }else{
                        Toast.makeText(EditHapusJenisBarangActivity.this, "Data Gagal Di Update", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EditHapusJenisBarangActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditJenisBarang> call, Throwable t) {
                Toast.makeText(EditHapusJenisBarangActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditHapusJenisBarangActivity.this, JenisBarangActivity.class));
        finish();
    }
}