package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSearchJenisSupplier{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataSearchJenisSupplier")
	private List<DataSearchJenisSupplierItem> dataSearchJenisSupplier;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataSearchJenisSupplier(List<DataSearchJenisSupplierItem> dataSearchJenisSupplier){
		this.dataSearchJenisSupplier = dataSearchJenisSupplier;
	}

	public List<DataSearchJenisSupplierItem> getDataSearchJenisSupplier(){
		return dataSearchJenisSupplier;
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
			"ResponseSearchJenisSupplier{" + 
			"pesan = '" + pesan + '\'' + 
			",dataSearchJenisSupplier = '" + dataSearchJenisSupplier + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}