package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataPembelianItem{

	@SerializedName("tanggal_pembelian")
	private String tanggalPembelian;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("nama")
	private String nama;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("name")
	private String name;

	@SerializedName("total_harga")
	private String totalHarga;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("id_pembelian")
	private String idPembelian;

	@SerializedName("id_supplier")
	private String idSupplier;

	public void setTanggalPembelian(String tanggalPembelian){
		this.tanggalPembelian = tanggalPembelian;
	}

	public String getTanggalPembelian(){
		return tanggalPembelian;
	}

	public void setJumlah(String jumlah){
		this.jumlah = jumlah;
	}

	public String getJumlah(){
		return jumlah;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
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

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setIdPembelian(String idPembelian){
		this.idPembelian = idPembelian;
	}

	public String getIdPembelian(){
		return idPembelian;
	}

	public void setIdSupplier(String idSupplier){
		this.idSupplier = idSupplier;
	}

	public String getIdSupplier(){
		return idSupplier;
	}

	@Override
 	public String toString(){
		return 
			"DataPembelianItem{" + 
			"tanggal_pembelian = '" + tanggalPembelian + '\'' + 
			",jumlah = '" + jumlah + '\'' + 
			",nama = '" + nama + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",name = '" + name + '\'' + 
			",total_harga = '" + totalHarga + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",id_user = '" + idUser + '\'' + 
			",id_pembelian = '" + idPembelian + '\'' + 
			",id_supplier = '" + idSupplier + '\'' + 
			"}";
		}
}