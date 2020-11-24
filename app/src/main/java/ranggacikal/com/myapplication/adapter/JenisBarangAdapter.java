package ranggacikal.com.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ranggacikal.com.myapplication.EditHapusJenisBarangActivity;
import ranggacikal.com.myapplication.JenisBarangActivity;
import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.model.DataJenisBarangItem;

public class JenisBarangAdapter extends RecyclerView.Adapter<JenisBarangAdapter.jenisBarangViewHolder> {

    Context mContext;
    List<DataJenisBarangItem> jenisBarangItems;

    public JenisBarangAdapter(Context mContext, List<DataJenisBarangItem> jenisBarangItems) {
        this.mContext = mContext;
        this.jenisBarangItems = jenisBarangItems;
    }

    @NonNull
    @Override
    public jenisBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jenis_barang, parent, false);
        return new jenisBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull jenisBarangViewHolder holder, int position) {

        holder.txtIdJenisBarang.setText(jenisBarangItems.get(position).getIdJenisBarang());
        holder.txtJenisBarang.setText(jenisBarangItems.get(position).getJenisBarang());

        String id = jenisBarangItems.get(position).getIdJenisBarang();
        String jenis_barang = jenisBarangItems.get(position).getJenisBarang();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditHapusJenisBarangActivity.class);
                intent.putExtra(EditHapusJenisBarangActivity.EXTRA_ID_JENIS_BARANG, id);
                intent.putExtra(EditHapusJenisBarangActivity.EXTRA_JENIS_BARANG, jenis_barang);
                mContext.startActivity(intent);
                ((JenisBarangActivity)mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return jenisBarangItems.size();
    }

    public class jenisBarangViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdJenisBarang, txtJenisBarang;
        public jenisBarangViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIdJenisBarang = itemView.findViewById(R.id.tv_list_id_jenis_barang);
            txtJenisBarang = itemView.findViewById(R.id.tv_list_jenis_barang);
        }
    }
}
