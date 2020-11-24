package ranggacikal.com.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import ranggacikal.com.myapplication.BuktiPenjualanActivity;
import ranggacikal.com.myapplication.DetailBarangPenjualanActivity;
import ranggacikal.com.myapplication.KeranjangActivity;
import ranggacikal.com.myapplication.PointOfSaleActivity;
import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.model.DataPenjualanItem;

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanAdapter.PenjualanViewHolder> {

    Context mContext;
    List<DataPenjualanItem> penjualanItems;


    public PenjualanAdapter(Context mContext, List<DataPenjualanItem> penjualanItems) {
        this.mContext = mContext;
        this.penjualanItems = penjualanItems;
    }

    @NonNull
    @Override
    public PenjualanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_penjualan_pos, parent, false);
        return new PenjualanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualanViewHolder holder, int position) {

        String id_penjualan = penjualanItems.get(position).getIdPenjualan();
        String tanggal_penjualan = penjualanItems.get(position).getTanggalPenjualan();
//        String total_penjualan = penjualanItems.get(position).getTotal();
        String username = penjualanItems.get(position).getName();
        String status = penjualanItems.get(position).getStatusPenjualan();

        holder.txtIdPenjualan.setText(id_penjualan);
        holder.txtTanggalPenjualan.setText(tanggal_penjualan);
        holder.txtStatusPenjualan.setText(status);
        holder.txtUser.setText(username);

        int harga = Integer.parseInt(penjualanItems.get(position).getTotal());
        Locale localID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
        holder.txtTotalPenjualan.setText(formatRupiah.format(harga));

        if (status.equals("Belum Selesai")){
            holder.btnCheckout.setVisibility(View.VISIBLE);
            holder.btnDetail.setVisibility(View.GONE);
            holder.btnCetakBukti.setVisibility(View.GONE);
            holder.btnCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentKeranjang = new Intent(mContext, KeranjangActivity.class);
                    intentKeranjang.putExtra(KeranjangActivity.EXTRA_ID_PENJUALAN, id_penjualan);
                    mContext.startActivity(intentKeranjang);
                    ((PointOfSaleActivity)mContext).finish();
                }
            });
        }else if (status.equals("Selesai")){
            holder.btnDetail.setVisibility(View.VISIBLE);
            holder.btnCetakBukti.setVisibility(View.VISIBLE);
            holder.btnCheckout.setVisibility(View.GONE);

            holder.btnCetakBukti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentCetak = new Intent(mContext, BuktiPenjualanActivity.class);
                    intentCetak.putExtra(BuktiPenjualanActivity.EXTRA_ID_PENJUALAN, id_penjualan);
                    mContext.startActivity(intentCetak);
                    ((PointOfSaleActivity)mContext).finish();
                }
            });

            holder.btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentDetail = new Intent(mContext, DetailBarangPenjualanActivity.class);
                    intentDetail.putExtra(DetailBarangPenjualanActivity.EXTRA_ID_PENJUALAN, id_penjualan);
                    mContext.startActivity(intentDetail);
                    ((PointOfSaleActivity)mContext).finish();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return penjualanItems.size();
    }

    public class PenjualanViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdPenjualan, txtTanggalPenjualan, txtTotalPenjualan, txtStatusPenjualan, txtUser;
        Button btnDetail, btnCheckout, btnCetakBukti;

        public PenjualanViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIdPenjualan = itemView.findViewById(R.id.tv_list_id_penjualan);
            txtTanggalPenjualan = itemView.findViewById(R.id.tv_list_tanggal_penjualan);
            txtTotalPenjualan = itemView.findViewById(R.id.tv_list_total_penjualan);
            txtStatusPenjualan = itemView.findViewById(R.id.tv_list_status_penjualan);
            txtUser = itemView.findViewById(R.id.tv_list_username_penjualan);
            btnCheckout = itemView.findViewById(R.id.btn_selesaikan_pembelian_list_penjualan);
            btnDetail = itemView.findViewById(R.id.btn_detail_list_penjualan);
            btnCetakBukti = itemView.findViewById(R.id.btn_cetak_bukti_penjualan);

        }
    }


}
