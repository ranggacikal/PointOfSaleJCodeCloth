package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSearchBarang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataSearchBarang")
	private List<DataSearchBarangItem> dataSearchBarang;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataSearchBarang(List<DataSearchBarangItem> dataSearchBarang){
		this.dataSearchBarang = dataSearchBarang;
	}

	public List<DataSearchBarangItem> getDataSearchBarang(){
		return dataSearchBarang;
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
			"ResponseSearchBarang{" + 
			"pesan = '" + pesan + '\'' + 
			",dataSearchBarang = '" + dataSearchBarang + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}