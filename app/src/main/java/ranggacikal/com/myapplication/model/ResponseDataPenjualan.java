package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataPenjualan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("DataPenjualan")
	private List<DataPenjualanItem> dataPenjualan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataPenjualan(List<DataPenjualanItem> dataPenjualan){
		this.dataPenjualan = dataPenjualan;
	}

	public List<DataPenjualanItem> getDataPenjualan(){
		return dataPenjualan;
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
			"ResponseDataPenjualan{" + 
			"pesan = '" + pesan + '\'' + 
			",dataPenjualan = '" + dataPenjualan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}