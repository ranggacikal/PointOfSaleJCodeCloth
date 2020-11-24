package ranggacikal.com.myapplication.adapter;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.api.ConfigRetrofit;
import ranggacikal.com.myapplication.model.DataBarangItem;
import ranggacikal.com.myapplication.model.ResponseGetIdPenjualan;
import ranggacikal.com.myapplication.model.ResponseTambahDetailPenjualan;
import ranggacikal.com.myapplication.sqllite.DatabaseHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihBarangAdapter extends RecyclerView.Adapter<PilihBarangAdapter.PilihBarangViewHolder> {

    Context mContext;
    List<DataBarangItem> barangItems;

    String idPenjualan;
    private DatabaseHandler dbHandler;

    public PilihBarangAdapter(Context mContext, List<DataBarangItem> barangItems) {
        this.mContext = mContext;
        this.barangItems = barangItems;
    }

    @NonNull
    @Override
    public PilihBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_barang_admin, parent, false);
        return new PilihBarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PilihBarangViewHolder holder, int position) {

        final String linkImg = barangItems.get(position).getImageBarang();



        Glide.with(mContext)
                .load(linkImg)
                .error(R.drawable.logojcodefix)
                .into(holder.imgBarang);

        holder.txtKode.setText(barangItems.get(position).getKodeBarang());
        holder.txtNama.setText(barangItems.get(position).getNamaBarang());
        int harga = Integer.parseInt(barangItems.get(position).getHargaJual());
        Locale localID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
        holder.txtHarga.setText(formatRupiah.format(harga));
        holder.txtJenisBarang.setText(barangItems.get(position).getJenisBarang());
        holder.txtUkuran.setText(barangItems.get(position).getUkuranBarang());
        holder.txtStok.setText(barangItems.get(position).getStok());

        int stock_barang = Integer.parseInt(barangItems.get(position).getStok());

        if (stock_barang == 0){

            holder.RlBarang.setVisibility(View.VISIBLE);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (stock_barang==0){
                    showDialogStokKosong();
                }else {

                    final Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.dialog_item_qty);
                    dialog.setTitle("Quantity");

                    final ImageView cartKurang = dialog.findViewById(R.id.cart_kurang);
                    final ImageView cartTambah = dialog.findViewById(R.id.cart_tambah);
                    final TextView addTocart = dialog.findViewById(R.id.tambah_cart_dialog);
                    final TextView quantity = dialog.findViewById(R.id.cart_quantity);
                    //PREFERENCED CONFIG
                    quantity.setText(String.valueOf(0));
                    final int[] cartCounter = {0};
                    cartKurang.setEnabled(true);

                    cartKurang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (cartCounter[0] == 1) {
                                Toast.makeText(mContext, "cant add less than 0", Toast.LENGTH_SHORT).show();
                            } else {
                                cartCounter[0] -= 1;
                                quantity.setText(String.valueOf(cartCounter[0]));
                            }
                        }
                    });

                    cartTambah.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            cartTambah.setEnabled(true);
                            cartCounter[0] += 1;
                            quantity.setText(String.valueOf(cartCounter[0]));
                        }
                    });

                    dialog.show();

                    getIdPenjualan();

                    addTocart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final int jumlah = cartCounter[0];
                            int harga_jual = Integer.parseInt(barangItems.get(position).getHargaJual());
                            int total_harga = jumlah * harga_jual;
                            int id_penjualan = Integer.parseInt(idPenjualan);
                            int id_barang = Integer.parseInt(barangItems.get(position).getIdBarang());
                            String status_penjualan = "Belum Selesai";

                            int stok = Integer.parseInt(barangItems.get(position).getStok());
                            int stok_habis = stok - jumlah;
                            if (jumlah < 1) {
                                Toast.makeText(mContext, "Jumlah Atau Quantity Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                            } else if (stok_habis < 0) {
                                showDialog();
                            } else {

                                  //SAVE TO SERVER
                                ConfigRetrofit.service.TambahDetailPenjualan(jumlah, total_harga, id_penjualan, id_barang, status_penjualan).enqueue(new Callback<ResponseTambahDetailPenjualan>() {
                                    @Override
                                    public void onResponse(Call<ResponseTambahDetailPenjualan> call, Response<ResponseTambahDetailPenjualan> response) {
                                        if (response.isSuccessful()) {
                                            int status = response.body().getStatus();

                                            if (status == 1) {
                                                Toast.makeText(mContext, "Berhasil Tambah Barang", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            } else {
                                                Toast.makeText(mContext, "Gagal Tambah Barang", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(mContext, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseTambahDetailPenjualan> call, Throwable t) {
                                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }

                        }
                    });

                }
            }
        });

    }

    private void showDialogStokKosong() {

        Dialog dialogKosong = new Dialog(mContext);

        dialogKosong.setContentView(R.layout.dialog_stock_kosong);

        final TextView txtTutupKosong = dialogKosong.findViewById(R.id.text_tutup_stok_kosong);

        dialogKosong.show();

        txtTutupKosong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogKosong.dismiss();
            }
        });

    }

    private void showDialog() {

        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_stok_tidak_cukup);
        final TextView txtTutup = dialog.findViewById(R.id.text_tutup_stok_tidak_cukup);

        dialog.show();

        txtTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void getIdPenjualan() {

        ConfigRetrofit.service.IdPenjulan().enqueue(new Callback<ResponseGetIdPenjualan>() {
            @Override
            public void onResponse(Call<ResponseGetIdPenjualan> call, Response<ResponseGetIdPenjualan> response) {
                if (response.isSuccessful()) {
                    idPenjualan = response.body().getDataIdPenjualan().getIdPenjualan();

                }
            }

            @Override
            public void onFailure(Call<ResponseGetIdPenjualan> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return barangItems.size();
    }

    public class PilihBarangViewHolder extends RecyclerView.ViewHolder {

        ImageView imgBarang;
        TextView txtNama, txtKode, txtHarga, txtJenisBarang, txtUkuran, txtStok;
        RelativeLayout RlBarang;
        public PilihBarangViewHolder(@NonNull View itemView) {
            super(itemView);

            imgBarang = itemView.findViewById(R.id.img_data_barang_list);
            txtKode = itemView.findViewById(R.id.text_kode_barang_list);
            txtNama = itemView.findViewById(R.id.text_nama_barang_list);
            txtHarga = itemView.findViewById(R.id.text_harga_barang_list);
            txtJenisBarang = itemView.findViewById(R.id.text_jenis_barang_list);
            txtUkuran = itemView.findViewById(R.id.text_ukuran_barang_list);
            txtStok = itemView.findViewById(R.id.text_stock_barang_list);
            RlBarang = itemView.findViewById(R.id.relative_barang);
        }
    }
}
