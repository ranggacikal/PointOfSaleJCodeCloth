package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class PemasukanLaporan{

	@SerializedName("total")
	private String total;

	public void setTotal(String total){
		this.total = total;
	}

	public String getTotal(){
		return total;
	}

	@Override
 	public String toString(){
		return 
			"PemasukanLaporan{" + 
			"total = '" + total + '\'' + 
			"}";
		}
}