package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataDetailPenjualanItem{

	@SerializedName("id_penjualan")
	private String idPenjualan;

	@SerializedName("status_penjualan")
	private String statusPenjualan;

	@SerializedName("id_detail_penjualan")
	private String idDetailPenjualan;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("ukuran_barang")
	private String ukuranBarang;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("kode_barang")
	private String kodeBarang;

	@SerializedName("total_harga")
	private String totalHarga;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("harga_jual")
	private String hargaJual;

	@SerializedName("image_barang")
	private String imageBarang;

	public void setIdPenjualan(String idPenjualan){
		this.idPenjualan = idPenjualan;
	}

	public String getIdPenjualan(){
		return idPenjualan;
	}

	public void setStatusPenjualan(String statusPenjualan){
		this.statusPenjualan = statusPenjualan;
	}

	public String getStatusPenjualan(){
		return statusPenjualan;
	}

	public void setIdDetailPenjualan(String idDetailPenjualan){
		this.idDetailPenjualan = idDetailPenjualan;
	}

	public String getIdDetailPenjualan(){
		return idDetailPenjualan;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setUkuranBarang(String ukuranBarang){
		this.ukuranBarang = ukuranBarang;
	}

	public String getUkuranBarang(){
		return ukuranBarang;
	}

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
	}

	public void setKodeBarang(String kodeBarang){
		this.kodeBarang = kodeBarang;
	}

	public String getKodeBarang(){
		return kodeBarang;
	}

	public void setTotalHarga(String totalHarga){
		this.totalHarga = totalHarga;
	}

	public String getTotalHarga(){
		return totalHarga;
	}

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setHargaJual(String hargaJual){
		this.hargaJual = hargaJual;
	}

	public String getHargaJual(){
		return hargaJual;
	}

	public void setImageBarang(String imageBarang){
		this.imageBarang = imageBarang;
	}

	public String getImageBarang(){
		return imageBarang;
	}

	@Override
 	public String toString(){
		return 
			"DataDetailPenjualanItem{" + 
			"id_penjualan = '" + idPenjualan + '\'' + 
			",status_penjualan = '" + statusPenjualan + '\'' + 
			",id_detail_penjualan = '" + idDetailPenjualan + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",ukuran_barang = '" + ukuranBarang + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",kode_barang = '" + kodeBarang + '\'' + 
			",total_harga = '" + totalHarga + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",harga_jual = '" + hargaJual + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			"}";
		}
}