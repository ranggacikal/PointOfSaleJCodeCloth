package ranggacikal.com.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import ranggacikal.com.myapplication.SharedPreference.SharedPreferencedConfig;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_nama_user_home)
    TextView tvNamaUserHome;
    @BindView(R.id.tv_email_user_home)
    TextView tvEmailUserHome;
    @BindView(R.id.tv_level_user_home)
    TextView tvLevelUserHome;
    @BindView(R.id.img_user_home)
    CircleImageView imgUserHome;
    @BindView(R.id.linear_pos)
    LinearLayout linearPos;
    @BindView(R.id.linear_stock)
    LinearLayout linearStock;
    @BindView(R.id.linear_laporan)
    LinearLayout linearLaporan;
    @BindView(R.id.btn_logout_kasir)
    Button btnLogoutKasir;
//    @BindView(R.id.linear_transaksi)
//    LinearLayout linearTransaksi;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        preferencedConfig = new SharedPreferencedConfig(this);

        tvNamaUserHome.setText(preferencedConfig.getPreferenceNamaLengkap());
        tvEmailUserHome.setText(preferencedConfig.getPreferenceEmail());
        tvLevelUserHome.setText(preferencedConfig.getPreferenceLevelUser());

        final String imgLinkUser = preferencedConfig.getPreferenceImage();

        Glide.with(MainActivity.this)
                .load(imgLinkUser)
                .error(R.drawable.logojcodefix)
                .into(imgUserHome);

        linearPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PointOfSaleActivity.class));
                finish();
            }
        });

        linearStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BarangAdminActivity.class));
                finish();
            }
        });

        linearLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LaporanActivity.class));
                finish();
            }
        });

        btnLogoutKasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TampilDialogLogout();
            }
        });
    }

    private void TampilDialogLogout() {

        Dialog dialog = new Dialog(MainActivity.this);

        dialog.setContentView(R.layout.dialog_logout);

        final TextView txtIya = dialog.findViewById(R.id.text_iya_dialog_logout);
        final TextView txtTidak = dialog.findViewById(R.id.text_tidak_dialog_logout);

        dialog.show();

        txtIya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        txtTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void Logout() {

        Toast.makeText(this, "Logged Out...", Toast.LENGTH_SHORT).show();
        preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, false);
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();

    }
}