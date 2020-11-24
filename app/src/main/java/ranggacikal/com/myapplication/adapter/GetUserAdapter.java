package ranggacikal.com.myapplication.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ranggacikal.com.myapplication.EditUserActivity;
import ranggacikal.com.myapplication.KelolaUserActivity;
import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.UserListActivity;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataUserItem;
import ranggacikal.com.myapplication.model.ResponseHapusUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserAdapter extends RecyclerView.Adapter<GetUserAdapter.UserViewHolder> {

    Context mContext;
    List<DataUserItem> userItems;

    public GetUserAdapter(Context mContext, List<DataUserItem> userItems) {
        this.mContext = mContext;
        this.userItems = userItems;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        String linkImg = userItems.get(position).getImageUser();

        Glide.with(mContext)
                .load(linkImg)
                .error(R.mipmap.ic_launcher)
                .into(holder.imgUserList);

        holder.textNama.setText(userItems.get(position).getName());
        holder.textEmail.setText(userItems.get(position).getEmail());
        holder.textLevel.setText(userItems.get(position).getLevel());

        String id = userItems.get(position).getId();
        String nama = userItems.get(position).getName();
        String email = userItems.get(position).getEmail();
        String level = userItems.get(position).getLevel();
        String image = userItems.get(position).getImageUser();

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditUserActivity.class);
                intent.putExtra(EditUserActivity.EXTRA_ID_USER, id);
                intent.putExtra(EditUserActivity.EXTRA_NAMA_USER, nama);
                intent.putExtra(EditUserActivity.EXTRA_EMAIL_USER, email);
                intent.putExtra(EditUserActivity.EXTRA_LEVEL_USER, level);
                intent.putExtra(EditUserActivity.EXTRA_IMAGE_USER, image);
                mContext.startActivity(intent);
                ((UserListActivity)mContext).finish();
            }
        });

        holder.btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(mContext);

                dialog.setContentView(R.layout.dialog_konfirmasi_hapus_user);

                final TextView txtIya = dialog.findViewById(R.id.text_iya_hapus_user);
                final TextView txtTidak = dialog.findViewById(R.id.text_tidak_hapus_user);

                dialog.show();

                txtIya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConfigRetrofit.service.HapusUser(id).enqueue(new Callback<ResponseHapusUser>() {
                            @Override
                            public void onResponse(Call<ResponseHapusUser> call, Response<ResponseHapusUser> response) {
                                if (response.isSuccessful()){

                                    int status = response.body().getStatus();
                                    String pesan = response.body().getPesan();

                                    if (status==1){
                                        Toast.makeText(mContext, pesan, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }else{
                                        Toast.makeText(mContext, pesan, Toast.LENGTH_SHORT).show();
                                    }

                                }else{
                                    Toast.makeText(mContext, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseHapusUser> call, Throwable t) {
                                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                txtTidak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return userItems.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textNama, textEmail, textLevel;
        Button btnEdit, btnHapus;
        CircleImageView imgUserList;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUserList = itemView.findViewById(R.id.img_list_user);
            textNama = itemView.findViewById(R.id.tv_list_nama_user);
            textEmail = itemView.findViewById(R.id.tv_list_email_user);
            textLevel = itemView.findViewById(R.id.tv_list_level_user);
            btnEdit = itemView.findViewById(R.id.btn_edit_list_user);
            btnHapus = itemView.findViewById(R.id.btn_hapus_list_user);
        }
    }
}
