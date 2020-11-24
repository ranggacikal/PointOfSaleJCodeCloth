package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataPenjualanItem{

	@SerializedName("id_penjualan")
	private String idPenjualan;

	@SerializedName("tanggal_penjualan")
	private String tanggalPenjualan;

	@SerializedName("total")
	private String total;

	@SerializedName("status_penjualan")
	private String statusPenjualan;

	@SerializedName("name")
	private String name;

	@SerializedName("id_user")
	private String idUser;

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

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	@Override
 	public String toString(){
		return 
			"DataPenjualanItem{" + 
			"id_penjualan = '" + idPenjualan + '\'' + 
			",tanggal_penjualan = '" + tanggalPenjualan + '\'' + 
			",total = '" + total + '\'' + 
			",status_penjualan = '" + statusPenjualan + '\'' + 
			",name = '" + name + '\'' + 
			",id_user = '" + idUser + '\'' + 
			"}";
		}
}