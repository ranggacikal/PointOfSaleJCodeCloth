package ranggacikal.com.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ranggacikal.com.myapplication.BarangAdminActivity;
import ranggacikal.com.myapplication.EditBarangActivity;
import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.SharedPreference.SharedPreferencedConfig;
import ranggacikal.com.myapplication.model.DataBarangItem;

public class BarangAdminAdapter extends RecyclerView.Adapter<BarangAdminAdapter.DataBarangViewHolder> {

    private Context mContext;
    private List<DataBarangItem> barangItems;
    private List<DataBarangItem> barangItemsAll;

    private SharedPreferencedConfig preferencedConfig;

    public BarangAdminAdapter(Context mContext, List<DataBarangItem> barangItems) {
        this.mContext = mContext;
        this.barangItems = barangItems;
        this.barangItemsAll = new ArrayList<>(barangItems);
    }

    @NonNull
    @Override
    public DataBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_barang_admin, parent, false);
        return new DataBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBarangViewHolder holder, int position) {

        String imgLink = (String) barangItems.get(position).getImageBarang();

        preferencedConfig = new SharedPreferencedConfig(mContext);

        Glide.with(mContext)
                .load(imgLink)
                .error(R.mipmap.ic_logo_round)
                .into(holder.imgBarang);

        int harga = Integer.parseInt(barangItems.get(position).getHargaJual());
        Locale localID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
        holder.textKodeBarang.setText(barangItems.get(position).getKodeBarang());
        holder.textNamaBarang.setText(barangItems.get(position).getNamaBarang());
        holder.textHargaBarang.setText(formatRupiah.format(harga));
        holder.textJenisBarang.setText(barangItems.get(position).getJenisBarang());
        holder.textStock.setText(barangItems.get(position).getStok());
        holder.textUkuran.setText(barangItems.get(position).getUkuranBarang());

        int stock_barang = Integer.parseInt(barangItems.get(position).getStok());

        if (stock_barang == 0){

            holder.RlBarang.setVisibility(View.VISIBLE);

        }

        String id = barangItems.get(position).getIdBarang();
        String kode = barangItems.get(position).getKodeBarang();
        String nama = barangItems.get(position).getNamaBarang();
        String harga_beli = barangItems.get(position).getHargaBeli();
        String harga_jual = barangItems.get(position).getHargaJual();
        String stok = barangItems.get(position).getStok();
        String id_jenis_barang = barangItems.get(position).getIdJenisBarang();
        String jenis_barang = barangItems.get(position).getJenisBarang();
        String ukuran_barang = (String) barangItems.get(position).getUkuranBarang();

        String level = preferencedConfig.getPreferenceLevelUser();

        if (level.equals("Admin") || level.equals("admin")) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, EditBarangActivity.class);
                    intent.putExtra(EditBarangActivity.EXTRA_ID_BARANG, id);
                    intent.putExtra(EditBarangActivity.EXTRA_KODE_BARANG, kode);
                    intent.putExtra(EditBarangActivity.EXTRA_NAMA_BARANG, nama);
                    intent.putExtra(EditBarangActivity.EXTRA_HARGA_BELI_BARANG, harga_beli);
                    intent.putExtra(EditBarangActivity.EXTRA_HARGA_JUAL_BARANG, harga_jual);
                    intent.putExtra(EditBarangActivity.EXTRA_STOK_BARANG, stok);
                    intent.putExtra(EditBarangActivity.EXTRA_ID_JENIS_BARANG, id_jenis_barang);
                    intent.putExtra(EditBarangActivity.EXTRA_JENIS_BARANG, jenis_barang);
                    intent.putExtra(EditBarangActivity.EXTRA_UKURAN_BARANG, ukuran_barang);
                    intent.putExtra(EditBarangActivity.EXTRA_IMAGE_BARANG, imgLink);
                    mContext.startActivity(intent);
                    ((BarangAdminActivity) mContext).finish();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return barangItems.size();
    }


    public class DataBarangViewHolder extends RecyclerView.ViewHolder {

        TextView textNamaBarang, textHargaBarang, textStock, textJenisBarang, textKodeBarang, textUkuran;
        ImageView imgBarang;
        RelativeLayout RlBarang;

        public DataBarangViewHolder(@NonNull View itemView) {
            super(itemView);

            textNamaBarang = itemView.findViewById(R.id.text_nama_barang_list);
            textHargaBarang = itemView.findViewById(R.id.text_harga_barang_list);
            textJenisBarang = itemView.findViewById(R.id.text_jenis_barang_list);
            textStock = itemView.findViewById(R.id.text_stock_barang_list);
            imgBarang = itemView.findViewById(R.id.img_data_barang_list);
            textKodeBarang = itemView.findViewById(R.id.text_kode_barang_list);
            textUkuran = itemView.findViewById(R.id.text_ukuran_barang_list);
            RlBarang = itemView.findViewById(R.id.relative_barang);


        }
    }

    public void filterList(ArrayList<DataBarangItem> filteredList) {

        barangItemsAll = filteredList;
        notifyDataSetChanged();
    }
}
