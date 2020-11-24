package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataBarang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataBarang")
	private List<DataBarangItem> dataBarang;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataBarang(List<DataBarangItem> dataBarang){
		this.dataBarang = dataBarang;
	}

	public List<DataBarangItem> getDataBarang(){
		return dataBarang;
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
			"ResponseDataBarang{" + 
			"pesan = '" + pesan + '\'' + 
			",dataBarang = '" + dataBarang + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}