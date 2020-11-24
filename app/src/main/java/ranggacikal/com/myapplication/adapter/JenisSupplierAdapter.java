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
import ranggacikal.com.myapplication.model.DataJenisSupplierItem;

public class JenisSupplierAdapter extends RecyclerView.Adapter<JenisSupplierAdapter.JenisSupplierViewHolder> {

    Context mContext;
    List<DataJenisSupplierItem> jenisSupplierItems;

    public JenisSupplierAdapter(Context mContext, List<DataJenisSupplierItem> jenisSupplierItems) {
        this.mContext = mContext;
        this.jenisSupplierItems = jenisSupplierItems;
    }

    @NonNull
    @Override
    public JenisSupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jenis_supplier, parent, false);
        return new JenisSupplierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JenisSupplierViewHolder holder, int position) {

        holder.txtIdJenisSupplier.setText(jenisSupplierItems.get(position).getIdJenisSuplier());
        holder.txtJenisSupplier.setText(jenisSupplierItems.get(position).getJenisSupplier());

        String id_jenis_supplier = jenisSupplierItems.get(position).getIdJenisSuplier();
        String jenis_supplier = jenisSupplierItems.get(position).getJenisSupplier();

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
        return jenisSupplierItems.size();
    }

    public class JenisSupplierViewHolder extends RecyclerView.ViewHolder {
        TextView txtIdJenisSupplier, txtJenisSupplier;
        public JenisSupplierViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIdJenisSupplier = itemView.findViewById(R.id.tv_list_id_jenis_supplier);
            txtJenisSupplier = itemView.findViewById(R.id.tv_list_jenis_suplier_nama);
        }
    }
}
