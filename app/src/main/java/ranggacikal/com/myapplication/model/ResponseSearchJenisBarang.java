package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSearchJenisBarang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataSearchJenisBarang")
	private List<DataSearchJenisBarangItem> dataSearchJenisBarang;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataSearchJenisBarang(List<DataSearchJenisBarangItem> dataSearchJenisBarang){
		this.dataSearchJenisBarang = dataSearchJenisBarang;
	}

	public List<DataSearchJenisBarangItem> getDataSearchJenisBarang(){
		return dataSearchJenisBarang;
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
			"ResponseSearchJenisBarang{" + 
			"pesan = '" + pesan + '\'' + 
			",dataSearchJenisBarang = '" + dataSearchJenisBarang + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}