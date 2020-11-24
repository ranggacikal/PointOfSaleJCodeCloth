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
import ranggacikal.com.myapplication.model.DataSearchSupplierItem;

public class SearchSupplierAdapter extends RecyclerView.Adapter<SearchSupplierAdapter.SearchSupplierViewHolder> {

    Context mContext;
    List<DataSearchSupplierItem> searchSupplierItems;

    public SearchSupplierAdapter(Context mContext, List<DataSearchSupplierItem> searchSupplierItems) {
        this.mContext = mContext;
        this.searchSupplierItems = searchSupplierItems;
    }

    @NonNull
    @Override
    public SearchSupplierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_supplier, parent, false);
        return new SearchSupplierViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSupplierViewHolder holder, int position) {


        holder.txtIdSupplier.setText(searchSupplierItems.get(position).getIdSupplier());
        holder.txtNamaSupplier.setText(searchSupplierItems.get(position).getNama());
        holder.txtNoTelpon.setText(searchSupplierItems.get(position).getNoTelpon());
        holder.txtEmail.setText(searchSupplierItems.get(position).getEmail());
        holder.txtAlamat.setText(searchSupplierItems.get(position).getAlamat());
        holder.txtJenisSupplier.setText(searchSupplierItems.get(position).getJenisSupplier());

        String id_supplier = searchSupplierItems.get(position).getIdSupplier();
        String nama = searchSupplierItems.get(position).getNama();
        String no_telpon = searchSupplierItems.get(position).getNoTelpon();
        String alamat = searchSupplierItems.get(position).getAlamat();
        String email = searchSupplierItems.get(position).getEmail();
        String id_jenis_supplier = searchSupplierItems.get(position).getIdJenisSupplier();

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
        return searchSupplierItems.size();
    }

    public class SearchSupplierViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdSupplier, txtNamaSupplier, txtNoTelpon, txtEmail, txtAlamat, txtJenisSupplier;

        public SearchSupplierViewHolder(@NonNull View itemView) {
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
