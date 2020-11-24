package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseSearchSupplier{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataSearchSupplier")
	private List<DataSearchSupplierItem> dataSearchSupplier;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataSearchSupplier(List<DataSearchSupplierItem> dataSearchSupplier){
		this.dataSearchSupplier = dataSearchSupplier;
	}

	public List<DataSearchSupplierItem> getDataSearchSupplier(){
		return dataSearchSupplier;
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
			"ResponseSearchSupplier{" + 
			"pesan = '" + pesan + '\'' + 
			",dataSearchSupplier = '" + dataSearchSupplier + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}