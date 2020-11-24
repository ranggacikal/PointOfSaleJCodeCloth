package ranggacikal.com.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.ResponseEditImageUser;
import ranggacikal.com.myapplication.model.ResponseEditUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserActivity extends AppCompatActivity {

    @BindView(R.id.img_edit_user)
    CircleImageView imgEditUser;
    @BindView(R.id.btn_edit_gambar_user)
    Button btnEditGambarUser;
    @BindView(R.id.btn_pilih_gambar_edit_user)
    Button btnPilihGambarEditUser;
    @BindView(R.id.btn_edit_upload_gambar_user)
    Button btnEditUploadGambarUser;
    @BindView(R.id.edt_edit_nama_user)
    EditText edtEditNamaUser;
    @BindView(R.id.edt_edit_email_user)
    EditText edtEditEmailUser;
    @BindView(R.id.edt_edit_level_user)
    EditText edtEditLevelUser;
    @BindView(R.id.btn_update_user)
    TextView btnUpdateUser;
    @BindView(R.id.btn_ganti_password)
    TextView btnGantiPassword;

    public static final String EXTRA_ID_USER = "ExtraIdUser";
    public static final String EXTRA_NAMA_USER = "ExtraNamaUser";
    public static final String EXTRA_EMAIL_USER = "ExtraEmailUser";
    public static final String EXTRA_LEVEL_USER = "ExtraLevelUser";
    public static final String EXTRA_IMAGE_USER = "ExtraImageUser";

    Bitmap bitmap;
    private static final int IMG_REQUEST = 777;
    @BindView(R.id.img_edit_user_pilih)
    CircleImageView imgEditUserPilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);

        final String imgUser = getIntent().getStringExtra(EXTRA_IMAGE_USER);

        Glide.with(EditUserActivity.this)
                .load(imgUser)
                .error(R.drawable.logojcodefix)
                .into(imgEditUser);

        edtEditNamaUser.setText(getIntent().getStringExtra(EXTRA_NAMA_USER));
        edtEditEmailUser.setText(getIntent().getStringExtra(EXTRA_EMAIL_USER));
        edtEditLevelUser.setText(getIntent().getStringExtra(EXTRA_LEVEL_USER));

        btnEditGambarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEditGambarUser.setVisibility(View.GONE);
                btnPilihGambarEditUser.setVisibility(View.VISIBLE);
                btnEditUploadGambarUser.setVisibility(View.VISIBLE);
            }
        });

        btnPilihGambarEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PilihGambar();
            }
        });

        btnEditUploadGambarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadEditGambar();
            }
        });

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDataUser();
            }
        });
    }

    private void updateDataUser() {

        String id = getIntent().getStringExtra(EXTRA_ID_USER);

        String nama = edtEditNamaUser.getText().toString();
        String email = edtEditEmailUser.getText().toString();
        String level = edtEditLevelUser.getText().toString();

        if (nama.isEmpty()) {

            edtEditNamaUser.setError("Nama Tidak Boleh Kosong");
            edtEditNamaUser.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            edtEditEmailUser.setError("Email Tidak Boleh Kosong");
            edtEditEmailUser.requestFocus();
            return;
        }

        if (level.isEmpty()) {
            edtEditLevelUser.setError("Level Tidak Boleh Kosong");
            edtEditLevelUser.requestFocus();
            return;
        }

        ConfigRetrofit.service.EditUser(id, nama, email, level).enqueue(new Callback<ResponseEditUser>() {
            @Override
            public void onResponse(Call<ResponseEditUser> call, Response<ResponseEditUser> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status == 1) {

                        Toast.makeText(EditUserActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditUserActivity.this, UserListActivity.class));
                        finish();
                    } else {
                        Toast.makeText(EditUserActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditUserActivity.this, "Terjadi Kesalahan...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditUser> call, Throwable t) {
                Toast.makeText(EditUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void UploadEditGambar() {

        String id = getIntent().getStringExtra(EXTRA_ID_USER);

        String image_user = imageToString();

        ConfigRetrofit.service.EditImageUser(id, image_user).enqueue(new Callback<ResponseEditImageUser>() {
            @Override
            public void onResponse(Call<ResponseEditImageUser> call, Response<ResponseEditImageUser> response) {
                if (response.isSuccessful()) {

                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();

                    if (status == 1) {

                        Toast.makeText(EditUserActivity.this, pesan, Toast.LENGTH_SHORT).show();
                        btnEditGambarUser.setVisibility(View.VISIBLE);
                        btnPilihGambarEditUser.setVisibility(View.GONE);
                        btnEditUploadGambarUser.setVisibility(View.GONE);

                    } else {
                        Toast.makeText(EditUserActivity.this, pesan, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditUserActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseEditImageUser> call, Throwable t) {
                Toast.makeText(EditUserActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                imgEditUserPilih.setVisibility(View.VISIBLE);
                imgEditUserPilih.setImageBitmap(bitmap);
                imgEditUser.setVisibility(View.GONE);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(EditUserActivity.this, UserListActivity.class));
        finish();
    }
}