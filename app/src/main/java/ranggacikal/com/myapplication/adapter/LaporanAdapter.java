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

import ranggacikal.com.myapplication.DetailBarangPenjualanActivity;
import ranggacikal.com.myapplication.DetailLaporanActivity;
import ranggacikal.com.myapplication.LaporanActivity;
import ranggacikal.com.myapplication.R;
import ranggacikal.com.myapplication.model.DataLaporanItem;

public class LaporanAdapter extends RecyclerView.Adapter<LaporanAdapter.LaporanViewHolder> {

    Context mContext;
    List<DataLaporanItem> laporanItems;

    public LaporanAdapter(Context mContext, List<DataLaporanItem> laporanItems) {
        this.mContext = mContext;
        this.laporanItems = laporanItems;
    }

    @NonNull
    @Override
    public LaporanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_laporan, parent, false);
        return new LaporanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaporanViewHolder holder, int position) {

        int total = Integer.parseInt(laporanItems.get(position).getTotal());
        Locale localID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localID);
        holder.txtIdPenjualan.setText(laporanItems.get(position).getIdPenjualan());
        holder.txtTanggalPenjualan.setText(laporanItems.get(position).getTanggalPenjualan());
        holder.txtTotal.setText(formatRupiah.format(total));

        String id_penjualan = laporanItems.get(position).getIdPenjualan();

        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailLaporanActivity.class);
                intent.putExtra(DetailLaporanActivity.EXTRA_ID_PENJUALAN, id_penjualan);
                mContext.startActivity(intent);
                ((LaporanActivity)mContext).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return laporanItems.size();
    }

    public class LaporanViewHolder extends RecyclerView.ViewHolder {

        TextView txtIdPenjualan, txtTanggalPenjualan, txtTotal;
        Button btnDetail;

        public LaporanViewHolder(@NonNull View itemView) {
            super(itemView);

            txtIdPenjualan = itemView.findViewById(R.id.txt_id_penjualan_laporan);
            txtTanggalPenjualan = itemView.findViewById(R.id.txt_tanggal_penjualan_laporan);
            txtTotal = itemView.findViewById(R.id.txt_total_penjualan_laporan);
            btnDetail = itemView.findViewById(R.id.btn_detail_barang_laporan);
        }
    }
}
