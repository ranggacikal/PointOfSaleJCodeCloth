package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDetailPenjualan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataDetailPenjualan")
	private List<DataDetailPenjualanItem> dataDetailPenjualan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataDetailPenjualan(List<DataDetailPenjualanItem> dataDetailPenjualan){
		this.dataDetailPenjualan = dataDetailPenjualan;
	}

	public List<DataDetailPenjualanItem> getDataDetailPenjualan(){
		return dataDetailPenjualan;
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
			"ResponseDetailPenjualan{" + 
			"pesan = '" + pesan + '\'' + 
			",dataDetailPenjualan = '" + dataDetailPenjualan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}