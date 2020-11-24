package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataJenisBarangItem{

	@SerializedName("id_jenis_barang")
	private String idJenisBarang;

	@SerializedName("jenis_barang")
	private String jenisBarang;

	public void setIdJenisBarang(String idJenisBarang){
		this.idJenisBarang = idJenisBarang;
	}

	public String getIdJenisBarang(){
		return idJenisBarang;
	}

	public void setJenisBarang(String jenisBarang){
		this.jenisBarang = jenisBarang;
	}

	public String getJenisBarang(){
		return jenisBarang;
	}

	@Override
 	public String toString(){
		return 
			"DataJenisBarangItem{" + 
			"id_jenis_barang = '" + idJenisBarang + '\'' + 
			",jenis_barang = '" + jenisBarang + '\'' + 
			"}";
		}
}