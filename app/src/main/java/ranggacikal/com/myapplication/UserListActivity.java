package ranggacikal.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ranggacikal.com.myapplication.adapter.GetUserAdapter;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataUserItem;
import ranggacikal.com.myapplication.model.ResponseDataUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {

    @BindView(R.id.recycler_user)
    RecyclerView recyclerUser;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                LoadRecyclerUser();
                refreshLayout.setRefreshing(false);
            }
        });

        LoadRecyclerUser();
    }

    private void LoadRecyclerUser() {

        ConfigRetrofit.service.DataUser().enqueue(new Callback<ResponseDataUser>() {
            @Override
            public void onResponse(Call<ResponseDataUser> call, Response<ResponseDataUser> response) {
                if (response.isSuccessful()) {
                    int status = response.body().getStatus();

                    if (status == 1) {
                        List<DataUserItem> userList = response.body().getDataUser();
                        GetUserAdapter adapter = new GetUserAdapter(UserListActivity.this, userList);
                        recyclerUser.setAdapter(adapter);
                        recyclerUser.setLayoutManager(new LinearLayoutManager(UserListActivity.this));
                    } else {
                        Toast.makeText(UserListActivity.this, "Data Tidak Ada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UserListActivity.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDataUser> call, Throwable t) {
                Toast.makeText(UserListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, KelolaUserActivity.class));
        finish();
    }
}