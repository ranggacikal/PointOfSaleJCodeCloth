package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSearchDataPembelian{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("status")
	private int status;

	@SerializedName("dataSearchPembelian")
	private List<DataSearchPembelianItem> dataSearchPembelian;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	public void setDataSearchPembelian(List<DataSearchPembelianItem> dataSearchPembelian){
		this.dataSearchPembelian = dataSearchPembelian;
	}

	public List<DataSearchPembelianItem> getDataSearchPembelian(){
		return dataSearchPembelian;
	}

	@Override
 	public String toString(){
		return 
			"ResponseSearchDataPembelian{" + 
			"pesan = '" + pesan + '\'' + 
			",status = '" + status + '\'' + 
			",dataSearchPembelian = '" + dataSearchPembelian + '\'' + 
			"}";
		}
}