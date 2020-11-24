package ranggacikal.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.model.DataSearchJenisBarangItem;

public class SearchJenisBarangAdapter extends RecyclerView.Adapter<SearchJenisBarangAdapter.ViewHolder> {
    Context mContext;
    List<DataSearchJenisBarangItem> searchJenisBarangItems;

    public SearchJenisBarangAdapter(Context mContext, List<DataSearchJenisBarangItem> searchJenisBarangItems) {
        this.mContext = mContext;
        this.searchJenisBarangItems = searchJenisBarangItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jenis_barang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtIdJenisBarang.setText(searchJenisBarangItems.get(position).getIdJenisBarang());
        holder.txtJenisBarang.setText(searchJenisBarangItems.get(position).getJenisBarang());
    }

    @Override
    public int getItemCount() {
        return searchJenisBarangItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdJenisBarang, txtJenisBarang;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdJenisBarang = itemView.findViewById(R.id.tv_list_id_jenis_barang);
            txtJenisBarang = itemView.findViewById(R.id.tv_list_jenis_barang);
        }
    }
}
