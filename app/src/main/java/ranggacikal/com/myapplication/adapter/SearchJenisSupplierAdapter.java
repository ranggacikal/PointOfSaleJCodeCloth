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

import ranggacikal.com.myapplication.EditHapusJenisSupplierActivity;
import ranggacikal.com.myapplication.JenisSupplierActivity;
import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.model.DataSearchJenisSupplierItem;

public class SearchJenisSupplierAdapter extends RecyclerView.Adapter<SearchJenisSupplierAdapter.SearchJenisSupplierViewHolder> {

    Context mContext;
    List<DataSearchJenisSupplierItem> searchJenisSupplierItems;

    public SearchJenisSupplierAdapter(Context mContext, List<DataSearchJenisSupplierItem> searchJenisSupplierItems) {
        this.mContext = mContext;
        this.searchJenisSupplierItems = searchJenisSupplierItems;
    }

    @NonNull
    @Override
    public SearchJenisSupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jenis_supplier, parent, false);
        return new SearchJenisSupplierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchJenisSupplierViewHolder holder, int position) {

        holder.txtIdJenisSupplier.setText(searchJenisSupplierItems.get(position).getIdJenisSuplier());
        holder.txtJenisSupplier.setText(searchJenisSupplierItems.get(position).getJenisSupplier());

        String id_jenis_supplier = searchJenisSupplierItems.get(position).getIdJenisSuplier();
        String jenis_supplier = searchJenisSupplierItems.get(position).getJenisSupplier();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditHapusJenisSupplierActivity.class);
                intent.putExtra(EditHapusJenisSupplierActivity.EXTRA_ID_JENIS_SUPPLIER, id_jenis_supplier);
                intent.putExtra(EditHapusJenisSupplierActivity.EXTRA_JENIS_SUPPLIER, jenis_supplier);
                mContext.startActivity(intent);
                ((JenisSupplierActivity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return searchJenisSupplierItems.size();
    }

    public class SearchJenisSupplierViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdJenisSupplier, txtJenisSupplier;
        public SearchJenisSupplierViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdJenisSupplier = itemView.findViewById(R.id.tv_list_id_jenis_supplier);
            txtJenisSupplier = itemView.findViewById(R.id.tv_list_jenis_suplier_nama);
        }
    }
}
