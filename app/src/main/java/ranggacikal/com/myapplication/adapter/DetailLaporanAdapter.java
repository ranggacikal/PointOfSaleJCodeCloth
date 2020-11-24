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

public class DetailLaporanAdapter extends RecyclerView.Adapter<DetailLaporanAdapter.DetailLaporanViewHolder> {

    Context mContext;
    List<DataDetailPenjualanItem> detailLaporanItems;

    public DetailLaporanAdapter(Context mContext, List<DataDetailPenjualanItem> detailLaporanItems) {
        this.mContext = mContext;
        this.detailLaporanItems = detailLaporanItems;
    }

    @NonNull
    @Override
    public DetailLaporanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan, parent, false);
        return new DetailLaporanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailLaporanViewHolder holder, int position) {

        holder.txtKode.setText(detailLaporanItems.get(position).getKodeBarang());
        holder.txtNama.setText(detailLaporanItems.get(position).getNamaBarang());
        holder.txtUkuran.setText(detailLaporanItems.get(position).getUkuranBarang());
        holder.txtJumlah.setText(detailLaporanItems.get(position).getJumlah());
        int total = Integer.parseInt(detailLaporanItems.get(position).getTotalHarga());
        Locale localID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
        holder.txtTotal.setText(formatRupiah.format(total));

    }

    @Override
    public int getItemCount() {
        return detailLaporanItems.size();
    }

    public class DetailLaporanViewHolder extends RecyclerView.ViewHolder {

        TextView txtKode, txtNama, txtUkuran, txtJumlah, txtTotal;

        public DetailLaporanViewHolder(@NonNull View itemView) {
            super(itemView);

            txtKode = itemView.findViewById(R.id.txt_kode_barang_laporan);
            txtNama = itemView.findViewById(R.id.txt_nama_barang_laporan);
            txtUkuran = itemView.findViewById(R.id.txt_ukuran_barang_laporan);
            txtJumlah = itemView.findViewById(R.id.txt_jumlah_barang_laporan);
            txtTotal = itemView.findViewById(R.id.txt_total_harga_barang_laporan);
        }
    }
}
