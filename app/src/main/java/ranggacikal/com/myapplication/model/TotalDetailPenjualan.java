package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class TotalDetailPenjualan{

	@SerializedName("total_harga")
	private String totalHarga;

	public void setTotalHarga(String totalHarga){
		this.totalHarga = totalHarga;
	}

	public String getTotalHarga(){
		return totalHarga;
	}

	@Override
 	public String toString(){
		return 
			"TotalDetailPenjualan{" + 
			"total_harga = '" + totalHarga + '\'' + 
			"}";
		}
}