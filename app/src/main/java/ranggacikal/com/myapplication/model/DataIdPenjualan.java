package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataIdPenjualan{

	@SerializedName("id_penjualan")
	private String idPenjualan;

	@SerializedName("tanggal_penjualan")
	private String tanggalPenjualan;

	@SerializedName("total")
	private String total;

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

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"DataIdPenjualan{" + 
			"id_penjualan = '" + idPenjualan + '\'' + 
			",tanggal_penjualan = '" + tanggalPenjualan + '\'' + 
			",total = '" + total + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}