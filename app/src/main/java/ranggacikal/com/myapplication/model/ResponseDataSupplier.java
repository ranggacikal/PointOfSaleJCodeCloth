package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseDataSupplier{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataSupplier")
	private List<DataSupplierItem> dataSupplier;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataSupplier(List<DataSupplierItem> dataSupplier){
		this.dataSupplier = dataSupplier;
	}

	public List<DataSupplierItem> getDataSupplier(){
		return dataSupplier;
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
			"ResponseDataSupplier{" + 
			"pesan = '" + pesan + '\'' + 
			",dataSupplier = '" + dataSupplier + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}