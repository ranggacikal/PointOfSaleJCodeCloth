package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class ResponseTotalPemasukan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("pemasukanLaporan")
	private PemasukanLaporan pemasukanLaporan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setPemasukanLaporan(PemasukanLaporan pemasukanLaporan){
		this.pemasukanLaporan = pemasukanLaporan;
	}

	public PemasukanLaporan getPemasukanLaporan(){
		return pemasukanLaporan;
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
			"ResponseTotalPemasukan{" + 
			"pesan = '" + pesan + '\'' + 
			",pemasukanLaporan = '" + pemasukanLaporan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}