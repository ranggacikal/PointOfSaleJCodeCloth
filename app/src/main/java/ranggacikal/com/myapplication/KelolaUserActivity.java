package ranggacikal.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KelolaUserActivity extends AppCompatActivity {

    @BindView(R.id.linear_regitrasi_user)
    LinearLayout linearRegitrasiUser;
    @BindView(R.id.linear_edit_user)
    LinearLayout linearEditUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_kelola_user);
        ButterKnife.bind(this);

        linearRegitrasiUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KelolaUserActivity.this, RegisterActivity.class));
                finish();
            }
        });

        linearEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(KelolaUserActivity.this, UserListActivity.class));
                finish();
            }
        });
    }
}