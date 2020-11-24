package ranggacikal.com.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.ResponseRegister;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.img_register)
    CircleImageView imgRegister;
    @BindView(R.id.btn_pilih_gambar)
    Button btnPilihGambar;
    @BindView(R.id.tv_nama_lengkap)
    EditText tvNamaLengkap;
    @BindView(R.id.tv_email)
    EditText tvEmail;
    @BindView(R.id.tv_password)
    EditText tvPassword;
    @BindView(R.id.tv_repassword)
    EditText tvRepassword;
    @BindView(R.id.spinner_admin)
    Spinner spinnerAdmin;
    @BindView(R.id.btn_register)
    Button btnRegister;

    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        btnPilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PilihGambar();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser();
            }
        });
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap!=null) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        }else if (bitmap==null){
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }
        byte[] imgByte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void RegisterUser() {

        String nama = tvNamaLengkap.getText().toString();
        String email = tvEmail.getText().toString();
        String password = tvPassword.getText().toString();
        String repassword = tvRepassword.getText().toString();
        String level = spinnerAdmin.getSelectedItem().toString();
        String image_user = imageToString();

        if (nama.isEmpty()){
            tvNamaLengkap.setError("Nama Lengkap Tidak Boleh Kosong !");
            tvNamaLengkap.requestFocus();
            return;
        }

        if (email.isEmpty()){
            tvEmail.setError("Email Lengkap Tidak Boleh Kosong !");
            tvEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            tvEmail.setError("Harap Masukan Email Yang Valid");
            tvEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            tvPassword.setError("Password Tidak Boleh Kosong");
            tvPassword.requestFocus();
            return;
        }

        if (repassword.isEmpty()){
            tvRepassword.setError("Re-Password Tidak Boleh Kosong");
            tvRepassword.requestFocus();
            return;
        }

        if (!repassword.equals(password)){
            tvRepassword.setError("Anda memasukan password yang tidak sama");
            tvRepassword.requestFocus();
            return;
        }

        if(image_user.isEmpty()){
            Toast.makeText(this, "Gambar Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progDialog = ProgressDialog.show(RegisterActivity.this, "", "Loading..", false);

        ConfigRetrofit.service.Register(nama, email, password, level, image_user).enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status == 1){
                        progDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, KelolaUserActivity.class));
                        finish();
                    }else{
                        progDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                progDialog.dismiss();
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

        if (requestCode==IMG_REQUEST && resultCode == RESULT_OK && data != null){
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgRegister.setImageBitmap(bitmap);
                btnPilihGambar.setVisibility(View.GONE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, KelolaUserActivity.class));
        finish();
    }
}