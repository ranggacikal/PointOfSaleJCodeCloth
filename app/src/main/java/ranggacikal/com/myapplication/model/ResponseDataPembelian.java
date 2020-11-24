package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataPembelian{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataPembelian")
	private List<DataPembelianItem> dataPembelian;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataPembelian(List<DataPembelianItem> dataPembelian){
		this.dataPembelian = dataPembelian;
	}

	public List<DataPembelianItem> getDataPembelian(){
		return dataPembelian;
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
			"ResponseDataPembelian{" + 
			"pesan = '" + pesan + '\'' + 
			",dataPembelian = '" + dataPembelian + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}