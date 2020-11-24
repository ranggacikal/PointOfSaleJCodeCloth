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
import java.util.List;
import java.util.Locale;

import ranggacikal.com.myapplication.BarangAdminActivity;
import ranggacikal.com.myapplication.EditBarangActivity;
import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.model.DataSearchBarangItem;

public class SearchBarangAdapter extends RecyclerView.Adapter<SearchBarangAdapter.SearchBarangViewHolder> {

    Context mContext;
    List<DataSearchBarangItem> searchBarangItems;

    public SearchBarangAdapter(Context mContext, List<DataSearchBarangItem> searchBarangItems) {
        this.mContext = mContext;
        this.searchBarangItems = searchBarangItems;
    }

    @NonNull
    @Override
    public SearchBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_barang_admin, parent, false);
        return new SearchBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchBarangViewHolder holder, int position) {

        String imgLink = searchBarangItems.get(position).getImageBarang();

        Glide.with(mContext)
                .load(imgLink)
                .error(R.mipmap.ic_logo)
                .into(holder.imgBarang);

        int harga = Integer.parseInt(searchBarangItems.get(position).getHargaJual());
        Locale localID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
        holder.textKodeBarang.setText(searchBarangItems.get(position).getKodeBarang());
        holder.textNamaBarang.setText(searchBarangItems.get(position).getNamaBarang());
        holder.textHargaBarang.setText(formatRupiah.format(harga));
        holder.textJenisBarang.setText(searchBarangItems.get(position).getJenisBarang());
        holder.textStock.setText(searchBarangItems.get(position).getStok());

        String id = searchBarangItems.get(position).getIdBarang();
        String kode = searchBarangItems.get(position).getKodeBarang();
        String nama = searchBarangItems.get(position).getNamaBarang();
        String harga_beli = searchBarangItems.get(position).getHargaBeli();
        String harga_jual = searchBarangItems.get(position).getHargaJual();
        String stok = searchBarangItems.get(position).getStok();
        String id_jenis_barang = searchBarangItems.get(position).getIdJenisBarang();
        String jenis_barang = searchBarangItems.get(position).getJenisBarang();
        String ukuran_barang = (String) searchBarangItems.get(position).getUkuranBarang();
        String image = searchBarangItems.get(position).getImageBarang();

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
                intent.putExtra(EditBarangActivity.EXTRA_IMAGE_BARANG, image);
                mContext.startActivity(intent);
                ((BarangAdminActivity)mContext).finish();
            }
        });

        int stock_barang = Integer.parseInt(searchBarangItems.get(position).getStok());

        if (stock_barang == 0){

            holder.RlBarang.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return searchBarangItems.size();
    }

    public class SearchBarangViewHolder extends RecyclerView.ViewHolder {

        TextView textNamaBarang, textHargaBarang, textStock, textJenisBarang, textKodeBarang;
        ImageView imgBarang;
        RelativeLayout RlBarang;

        public SearchBarangViewHolder(@NonNull View itemView) {
            super(itemView);

            textNamaBarang = itemView.findViewById(R.id.text_nama_barang_list);
            textHargaBarang = itemView.findViewById(R.id.text_harga_barang_list);
            textJenisBarang = itemView.findViewById(R.id.text_jenis_barang_list);
            textStock = itemView.findViewById(R.id.text_stock_barang_list);
            imgBarang = itemView.findViewById(R.id.img_data_barang_list);
            textKodeBarang = itemView.findViewById(R.id.text_kode_barang_list);
            RlBarang = itemView.findViewById(R.id.relative_barang);
        }
    }
}
