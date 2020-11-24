package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataJenisBarang{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataJenisBarang")
	private List<DataJenisBarangItem> dataJenisBarang;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataJenisBarang(List<DataJenisBarangItem> dataJenisBarang){
		this.dataJenisBarang = dataJenisBarang;
	}

	public List<DataJenisBarangItem> getDataJenisBarang(){
		return dataJenisBarang;
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
			"ResponseDataJenisBarang{" + 
			"pesan = '" + pesan + '\'' + 
			",dataJenisBarang = '" + dataJenisBarang + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}