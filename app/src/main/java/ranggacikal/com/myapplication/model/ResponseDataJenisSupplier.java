package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataJenisSupplier{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataJenisSupplier")
	private List<DataJenisSupplierItem> dataJenisSupplier;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataJenisSupplier(List<DataJenisSupplierItem> dataJenisSupplier){
		this.dataJenisSupplier = dataJenisSupplier;
	}

	public List<DataJenisSupplierItem> getDataJenisSupplier(){
		return dataJenisSupplier;
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
			"ResponseDataJenisSupplier{" + 
			"pesan = '" + pesan + '\'' + 
			",dataJenisSupplier = '" + dataJenisSupplier + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}