package ranggacikal.com.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.SharedPreference.SharedPreferencedConfig;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataLogin;
import ranggacikal.com.myapplication.model.ResponseLogin;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tv_email_login)
    EditText tvEmailLogin;
    @BindView(R.id.tv_password_login)
    EditText tvPasswordLogin;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        preferencedConfig = new SharedPreferencedConfig(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        if (preferencedConfig.getPreferenceIsLogin()){
            String levelUser = preferencedConfig.getPreferenceLevelUser();

            if (levelUser.equals("admin") || levelUser.equals("Admin")){
                startActivity(new Intent(LoginActivity.this, HomeAdminActivity.class));
                finish();
            }else if (levelUser.equals("kasir") || levelUser.equals("Kasir")){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        }

    }

    private void Login() {

        String email = tvEmailLogin.getText().toString();
        String password = tvPasswordLogin.getText().toString();

        if (email.isEmpty()){
            tvEmailLogin.setError("Email Tidak Boleh Kosong");
            tvEmailLogin.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            tvEmailLogin.setError("Harap Masukan Email Yang Valid");
            tvEmailLogin.requestFocus();
            return;
        }

        if (password.isEmpty()){
            tvPasswordLogin.setError("Password Tidak Boleh Kosong");
            tvPasswordLogin.requestFocus();
            return;
        }

        final ProgressDialog progDialog = ProgressDialog.show(LoginActivity.this, "", "Loading..", false);

        ConfigRetrofit.service.Login(email, password).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                progDialog.dismiss();

                if (response.isSuccessful()){
                    int status = response.body().getStatus();
                    String pesan = response.body().getPesan();


                    if (status == 1){

                        String level = response.body().getDataLogin().getLevel();
                        if (level.equals("admin") || level.equals("Admin")){
                            String id = response.body().getDataLogin().getId();
                            String email = response.body().getDataLogin().getEmail();
                            String nama = response.body().getDataLogin().getName();
                            String level2 = response.body().getDataLogin().getLevel();
                            String image = response.body().getDataLogin().getImageUser();

                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_USER, id);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_NAMA_LENGKAP, nama);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_EMAIL, email);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_LEVEL_USER, level2);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_IMAGE, image);
                            preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, true);

                            Toast.makeText(LoginActivity.this, pesan, Toast.LENGTH_SHORT).show();
                            Intent intentAdmin = new Intent(LoginActivity.this, HomeAdminActivity.class);
                            startActivity(intentAdmin);
                            finish();
                        }else if(level.equals("kasir") || level.equals("Kasir")){
                            Toast.makeText(LoginActivity.this, pesan, Toast.LENGTH_SHORT).show();

                            String id = response.body().getDataLogin().getId();
                            String email = response.body().getDataLogin().getEmail();
                            String nama = response.body().getDataLogin().getName();
                            String level3 = response.body().getDataLogin().getLevel();
                            String image = response.body().getDataLogin().getImageUser();

                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_ID_USER, id);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_NAMA_LENGKAP, nama);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_EMAIL, email);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_LEVEL_USER, level3);
                            preferencedConfig.savePrefString(SharedPreferencedConfig.PREFERENCE_IMAGE, image);
                            preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, true);

                            Intent intentUser = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intentUser);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Email/Password Salah", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Email/Password Salah", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progDialog.dismiss();
            }
        });

    }
}