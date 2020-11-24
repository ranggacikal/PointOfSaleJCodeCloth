package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class ResponseGetIdPenjualan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("DataIdPenjualan")
	private DataIdPenjualan dataIdPenjualan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataIdPenjualan(DataIdPenjualan dataIdPenjualan){
		this.dataIdPenjualan = dataIdPenjualan;
	}

	public DataIdPenjualan getDataIdPenjualan(){
		return dataIdPenjualan;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseGetIdPenjualan{" + 
			"pesan = '" + pesan + '\'' + 
			",dataIdPenjualan = '" + dataIdPenjualan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}