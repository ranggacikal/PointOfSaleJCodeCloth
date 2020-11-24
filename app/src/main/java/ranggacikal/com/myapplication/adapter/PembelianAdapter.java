package ranggacikal.com.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ranggacikal.com.myapplication.DetailPembelianActivity;
import ranggacikal.com.myapplication.EditHapusPembelianActivity;
import ranggacikal.com.myapplication.PembelianActivity;
import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.model.DataPembelianItem;

public class PembelianAdapter extends RecyclerView.Adapter<PembelianAdapter.PembelianViewHolder> {
    Context mContext;
    List<DataPembelianItem> pembelianItems;

    public PembelianAdapter(Context mContext, List<DataPembelianItem> pembelianItems) {
        this.mContext = mContext;
        this.pembelianItems = pembelianItems;
    }

    @NonNull
    @Override
    public PembelianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pembelian, parent, false);
        return new PembelianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PembelianViewHolder holder, int position) {

        holder.txtIdPembelian.setText(pembelianItems.get(position).getIdPembelian());
        holder.txtTanggal.setText(pembelianItems.get(position).getTanggalPembelian());
        holder.txtUser.setText(pembelianItems.get(position).getName());


        String id = pembelianItems.get(position).getIdPembelian();
        String tanggal_pembelian = pembelianItems.get(position).getTanggalPembelian();
        String nama_barang = pembelianItems.get(position).getNamaBarang();
        String jumlah = pembelianItems.get(position).getJumlah();
        String total_harga = pembelianItems.get(position).getTotalHarga();
        String supplier = pembelianItems.get(position).getNama();
        String id_barang = pembelianItems.get(position).getIdBarang();
        String id_supplier = pembelianItems.get(position).getIdSupplier();

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailPembelianActivity.class);
                intent.putExtra(DetailPembelianActivity.EXTRA_ID_PEMBELIAN, id);
                intent.putExtra(DetailPembelianActivity.EXTRA_TANGGAL_PEMBELIAN, tanggal_pembelian);
                intent.putExtra(DetailPembelianActivity.EXTRA_NAMA_BARANG_PEMBELIAN, nama_barang);
                intent.putExtra(DetailPembelianActivity.EXTRA_JUMLAH_PEMBELIAN, jumlah);
                intent.putExtra(DetailPembelianActivity.EXTRA_TOTAL_HARGA_PEMBELIAN, total_harga);
                intent.putExtra(DetailPembelianActivity.EXTRA_SUPPLIER_PEMBELIAN, supplier);
                mContext.startActivity(intent);
                ((PembelianActivity)mContext).finish();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit = new Intent(mContext, EditHapusPembelianActivity.class);
                intentEdit.putExtra(EditHapusPembelianActivity.EXTRA_ID_PEMBELIAN, id);
                intentEdit.putExtra(EditHapusPembelianActivity.EXTRA_TANGGAL_PEMBELIAN, tanggal_pembelian);
                intentEdit.putExtra(EditHapusPembelianActivity.EXTRA_NAMA_BARANG_PEMBELIAN, nama_barang);
                intentEdit.putExtra(EditHapusPembelianActivity.EXTRA_JUMLAH_PEMBELIAN, jumlah);
                intentEdit.putExtra(EditHapusPembelianActivity.EXTRA_TOTAL_HARGA_PEMBELIAN, total_harga);
                intentEdit.putExtra(EditHapusPembelianActivity.EXTRA_SUPPLIER_PEMBELIAN, supplier);
                intentEdit.putExtra(EditHapusPembelianActivity.EXTRA_ID_BARANG_PEMBELIAN, id_barang);
                intentEdit.putExtra(EditHapusPembelianActivity.EXTRA_ID_SUPPLIER_PEMBELIAN, id_supplier);
                mContext.startActivity(intentEdit);
                ((PembelianActivity)mContext).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pembelianItems.size();
    }

    public class PembelianViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdPembelian, txtTanggal, txtUser;
        Button btnDetail;
        public PembelianViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIdPembelian = itemView.findViewById(R.id.tv_list_id_pembelian);
            txtTanggal = itemView.findViewById(R.id.tv_list_tanggal_pembelian);
            txtUser = itemView.findViewById(R.id.tv_list_username_pembelian);
            btnDetail = itemView.findViewById(R.id.btn_detail_list_pembelian);
        }
    }
}
