package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataLaporanItem{

	@SerializedName("id_penjualan")
	private String idPenjualan;

	@SerializedName("tanggal_penjualan")
	private String tanggalPenjualan;

	@SerializedName("total")
	private String total;

	@SerializedName("status_penjualan")
	private String statusPenjualan;

	@SerializedName("username")
	private String username;

	public void setIdPenjualan(String idPenjualan){
		this.idPenjualan = idPenjualan;
	}

	public String getIdPenjualan(){
		return idPenjualan;
	}

	public void setTanggalPenjualan(String tanggalPenjualan){
		this.tanggalPenjualan = tanggalPenjualan;
	}

	public String getTanggalPenjualan(){
		return tanggalPenjualan;
	}

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	public void setStatusPenjualan(String statusPenjualan){
		this.statusPenjualan = statusPenjualan;
	}

	public String getStatusPenjualan(){
		return statusPenjualan;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"DataLaporanItem{" + 
			"id_penjualan = '" + idPenjualan + '\'' + 
			",tanggal_penjualan = '" + tanggalPenjualan + '\'' + 
			",total = '" + total + '\'' + 
			",status_penjualan = '" + statusPenjualan + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}