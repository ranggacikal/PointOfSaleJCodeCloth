package ranggacikal.com.myapplication.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseLaporanByBulan{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("dataLaporan")
	private List<DataLaporanItem> dataLaporan;

	@SerializedName("status")
	private int status;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setDataLaporan(List<DataLaporanItem> dataLaporan){
		this.dataLaporan = dataLaporan;
	}

	public List<DataLaporanItem> getDataLaporan(){
		return dataLaporan;
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
			"ResponseLaporanByBulan{" + 
			"pesan = '" + pesan + '\'' + 
			",dataLaporan = '" + dataLaporan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}