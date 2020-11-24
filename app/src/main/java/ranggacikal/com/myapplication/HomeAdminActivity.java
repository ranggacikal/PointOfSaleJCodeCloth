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

public class HomeAdminActivity extends AppCompatActivity {

    @BindView(R.id.tv_nama_user_admin)
    TextView tvNamaUserAdmin;
    @BindView(R.id.tv_email_admin)
    TextView tvEmailAdmin;
    @BindView(R.id.tv_level_admin)
    TextView tvLevelAdmin;
    @BindView(R.id.img_admin_home)
    CircleImageView imgAdminHome;
    @BindView(R.id.linear_barang)
    LinearLayout linearBarang;
    @BindView(R.id.linear_pembelian)
    LinearLayout linearPembelian;
    @BindView(R.id.linear_penjualan)
    LinearLayout linearPenjualan;
    @BindView(R.id.linear_supplier)
    LinearLayout linearSupplier;
    @BindView(R.id.linear_user)
    LinearLayout linearUser;
    @BindView(R.id.btn_logout_admin)
    Button btnLogoutAdmin;

    private SharedPreferencedConfig preferencedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_home_admin);
        ButterKnife.bind(this);

        preferencedConfig = new SharedPreferencedConfig(this);

        tvNamaUserAdmin.setText(preferencedConfig.getPreferenceNamaLengkap());
        tvEmailAdmin.setText(preferencedConfig.getPreferenceEmail());
        tvLevelAdmin.setText(preferencedConfig.getPreferenceLevelUser());

        String linkImageUser = preferencedConfig.getPreferenceImage();

        Glide.with(HomeAdminActivity.this)
                .load(linkImageUser)
                .error(R.drawable.logojcodefix)
                .into(imgAdminHome);

        linearUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdminActivity.this, KelolaUserActivity.class));
            }
        });

        linearBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdminActivity.this, BarangAdminActivity.class));
                finish();
            }
        });

        linearPembelian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdminActivity.this, PembelianActivity.class));
                finish();
            }
        });

        linearPenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdminActivity.this, PenjualanAdminActivity.class));
                finish();
            }
        });

        linearSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeAdminActivity.this, SupplierActivity.class));
                finish();
            }
        });

        btnLogoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TampilDialogKonfirmasiLogout();
            }
        });
    }

    private void TampilDialogKonfirmasiLogout() {

        Dialog dialog = new Dialog(HomeAdminActivity.this);

        dialog.setContentView(R.layout.dialog_logout_admin);

        final TextView txtIya = dialog.findViewById(R.id.text_iya_dialog_logout_admin);
        final TextView txtTidak = dialog.findViewById(R.id.text_tidak_dialog_logout_admin);

        dialog.show();

        txtIya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
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

    private void logout() {

        Toast.makeText(this, "Logged Out...", Toast.LENGTH_SHORT).show();
        preferencedConfig.savePrefBoolean(SharedPreferencedConfig.PREFERENCE_IS_LOGIN, false);
        startActivity(new Intent(HomeAdminActivity.this, LoginActivity.class));
        finish();

    }
}