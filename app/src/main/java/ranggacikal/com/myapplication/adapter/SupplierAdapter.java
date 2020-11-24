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

import ranggacikal.com.myapplication.EditHapusSupplier;
import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.SupplierActivity;
import ranggacikal.com.myapplication.model.DataSupplierItem;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder> {

    Context mContext;
    List<DataSupplierItem> supplierItems;

    public SupplierAdapter(Context mContext, List<DataSupplierItem> supplierItems) {
        this.mContext = mContext;
        this.supplierItems = supplierItems;
    }

    @NonNull
    @Override
    public SupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_supplier, parent, false);
        return new SupplierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierViewHolder holder, int position) {

        holder.txtIdSupplier.setText(supplierItems.get(position).getIdSupplier());
        holder.txtNamaSupplier.setText(supplierItems.get(position).getNama());
        holder.txtNoTelpon.setText(supplierItems.get(position).getNoTelpon());
        holder.txtEmail.setText(supplierItems.get(position).getEmail());
        holder.txtAlamat.setText(supplierItems.get(position).getAlamat());
        holder.txtJenisSupplier.setText(supplierItems.get(position).getJenisSupplier());

        String id_supplier = supplierItems.get(position).getIdSupplier();
        String nama = supplierItems.get(position).getNama();
        String no_telpon = supplierItems.get(position).getNoTelpon();
        String alamat = supplierItems.get(position).getAlamat();
        String email = supplierItems.get(position).getEmail();
        String id_jenis_supplier = supplierItems.get(position).getIdJenisSupplier();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditHapusSupplier.class);
                intent.putExtra(EditHapusSupplier.EXTRA_ID_SUPPLIER, id_supplier);
                intent.putExtra(EditHapusSupplier.EXTRA_NAMA_SUPPLIER, nama);
                intent.putExtra(EditHapusSupplier.EXTRA_NO_TELPON_SUPPLIER, no_telpon);
                intent.putExtra(EditHapusSupplier.EXTRA_ALAMAT_SUPPLIER, alamat);
                intent.putExtra(EditHapusSupplier.EXTRA_EMAIL_SUPPLIER, email);
                intent.putExtra(EditHapusSupplier.EXTRA_ID_JENIS_SUPPLIER, id_jenis_supplier);
                mContext.startActivity(intent);
                ((SupplierActivity)mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return supplierItems.size();
    }

    public class SupplierViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdSupplier, txtNamaSupplier, txtNoTelpon, txtEmail, txtAlamat, txtJenisSupplier;

        public SupplierViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdSupplier = itemView.findViewById(R.id.tv_list_id_supplier);
            txtNamaSupplier = itemView.findViewById(R.id.tv_list_nama_supplier);
            txtNoTelpon = itemView.findViewById(R.id.tv_list_notelpon_supplier);
            txtEmail = itemView.findViewById(R.id.tv_list_email_supplier);
            txtAlamat = itemView.findViewById(R.id.tv_list_alamat_supplier);
            txtJenisSupplier = itemView.findViewById(R.id.tv_list_jenis_supplier);
        }


    }
}
