package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class ResponseTotalDetailPenjualan{

	@SerializedName("total_detail_penjualan")
	private TotalDetailPenjualan totalDetailPenjualan;

	@SerializedName("status")
	private int status;

	public void setTotalDetailPenjualan(TotalDetailPenjualan totalDetailPenjualan){
		this.totalDetailPenjualan = totalDetailPenjualan;
	}

	public TotalDetailPenjualan getTotalDetailPenjualan(){
		return totalDetailPenjualan;
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
			"ResponseTotalDetailPenjualan{" + 
			"total_detail_penjualan = '" + totalDetailPenjualan + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}