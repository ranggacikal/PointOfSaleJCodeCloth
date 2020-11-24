package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class ResponseTambahSupplier{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("status")
	private int status;

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

	@Override
 	public String toString(){
		return 
			"ResponseTambahSupplier{" + 
			"pesan = '" + pesan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}