package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataJenisSupplierItem{

	@SerializedName("id_jenis_suplier")
	private String idJenisSuplier;

	@SerializedName("jenis_supplier")
	private String jenisSupplier;

	public void setIdJenisSuplier(String idJenisSuplier){
		this.idJenisSuplier = idJenisSuplier;
	}

	public String getIdJenisSuplier(){
		return idJenisSuplier;
	}

	public void setJenisSupplier(String jenisSupplier){
		this.jenisSupplier = jenisSupplier;
	}

	public String getJenisSupplier(){
		return jenisSupplier;
	}

	@Override
 	public String toString(){
		return 
			"DataJenisSupplierItem{" + 
			"id_jenis_suplier = '" + idJenisSuplier + '\'' + 
			",jenis_supplier = '" + jenisSupplier + '\'' + 
			"}";
		}
}