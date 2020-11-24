package ranggacikal.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.model.DataDetailPenjualanItem;

public class DetailBarangAdapter extends RecyclerView.Adapter<DetailBarangAdapter.DetailBarangViewHolder> {

    Context mContext;
    List<DataDetailPenjualanItem> detailBarangItems;

    public DetailBarangAdapter(Context mContext, List<DataDetailPenjualanItem> detailBarangItems) {
        this.mContext = mContext;
        this.detailBarangItems = detailBarangItems;
    }

    @NonNull
    @Override
    public DetailBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan, parent, false);
        return new DetailBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailBarangViewHolder holder, int position) {

        holder.txtKode.setText(detailBarangItems.get(position).getKodeBarang());
        holder.txtNama.setText(detailBarangItems.get(position).getNamaBarang());
        holder.txtUkuran.setText(detailBarangItems.get(position).getUkuranBarang());
        holder.txtJumlah.setText(detailBarangItems.get(position).getJumlah());
        int total = Integer.parseInt(detailBarangItems.get(position).getTotalHarga());
        Locale localID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
        holder.txtTotal.setText(formatRupiah.format(total));
    }

    @Override
    public int getItemCount() {
        return detailBarangItems.size();
    }

    public class DetailBarangViewHolder extends RecyclerView.ViewHolder {

        TextView txtKode, txtNama, txtUkuran, txtJumlah, txtTotal;
        public DetailBarangViewHolder(@NonNull View itemView) {
            super(itemView);

            txtKode = itemView.findViewById(R.id.txt_kode_barang_laporan);
            txtNama = itemView.findViewById(R.id.txt_nama_barang_laporan);
            txtUkuran = itemView.findViewById(R.id.txt_ukuran_barang_laporan);
            txtJumlah = itemView.findViewById(R.id.txt_jumlah_barang_laporan);
            txtTotal = itemView.findViewById(R.id.txt_total_harga_barang_laporan);
        }
    }
}
