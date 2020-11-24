package ranggacikal.com.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.model.DataDetailPenjualanItem;

public class DetailPenjualanAdapter extends RecyclerView.Adapter<DetailPenjualanAdapter.DetailPenjualanViewHolder> {

    Context mContext;
    List<DataDetailPenjualanItem> detailPenjualanItems;

    public DetailPenjualanAdapter(Context mContext, List<DataDetailPenjualanItem> detailPenjualanItems) {
        this.mContext = mContext;
        this.detailPenjualanItems = detailPenjualanItems;
    }

    @NonNull
    @Override
    public DetailPenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_keranjang, parent, false);

        return new DetailPenjualanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPenjualanViewHolder holder, int position) {

        final String imgLink = detailPenjualanItems.get(position).getImageBarang();
        int harga = Integer.parseInt(detailPenjualanItems.get(position).getHargaJual());
        int total = Integer.parseInt(detailPenjualanItems.get(position).getTotalHarga());

        Glide.with(mContext)
                .load(imgLink)
                .error(R.drawable.logojcodefix)
                .into(holder.imgKeranjang);

        Locale localID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);

        holder.txtKodeBarang.setText(detailPenjualanItems.get(position).getKodeBarang());
        holder.txtNamaBarang.setText(detailPenjualanItems.get(position).getNamaBarang());
        holder.txtUkuran.setText(detailPenjualanItems.get(position).getUkuranBarang());
        holder.txtHarga.setText(formatRupiah.format(harga));
        holder.txtJumlah.setText(detailPenjualanItems.get(position).getJumlah());
        holder.txtTotal.setText(formatRupiah.format(total));

    }

    @Override
    public int getItemCount() {
        return detailPenjualanItems.size();
    }

    public class DetailPenjualanViewHolder extends RecyclerView.ViewHolder {

        ImageView imgKeranjang;

        TextView txtKodeBarang, txtNamaBarang, txtUkuran, txtHarga, txtJumlah, txtTotal;

        public DetailPenjualanViewHolder(@NonNull View itemView) {
            super(itemView);

            imgKeranjang = itemView.findViewById(R.id.img_list_keranjang);
            txtKodeBarang = itemView.findViewById(R.id.tv_list_kode_barang_keranjang);
            txtNamaBarang = itemView.findViewById(R.id.tv_list_nama_barang_keranjang);
            txtUkuran = itemView.findViewById(R.id.tv_list_ukuran_barang_keranjang);
            txtHarga = itemView.findViewById(R.id.tv_list_harga_barang_keranjang);
            txtJumlah = itemView.findViewById(R.id.tv_list_jumlah_barang_keranjang);
            txtTotal = itemView.findViewById(R.id.tv_list_total_keranjang);
        }
    }
}
