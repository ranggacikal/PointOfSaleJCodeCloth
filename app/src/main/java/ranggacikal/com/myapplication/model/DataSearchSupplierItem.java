package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataSearchSupplierItem{

	@SerializedName("nama")
	private String nama;

	@SerializedName("jenis_supplier")
	private String jenisSupplier;

	@SerializedName("id_jenis_supplier")
	private String idJenisSupplier;

	@SerializedName("no_telpon")
	private String noTelpon;

	@SerializedName("id_supplier")
	private String idSupplier;

	@SerializedName("email")
	private String email;

	@SerializedName("alamat")
	private String alamat;

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setJenisSupplier(String jenisSupplier){
		this.jenisSupplier = jenisSupplier;
	}

	public String getJenisSupplier(){
		return jenisSupplier;
	}

	public void setIdJenisSupplier(String idJenisSupplier){
		this.idJenisSupplier = idJenisSupplier;
	}

	public String getIdJenisSupplier(){
		return idJenisSupplier;
	}

	public void setNoTelpon(String noTelpon){
		this.noTelpon = noTelpon;
	}

	public String getNoTelpon(){
		return noTelpon;
	}

	public void setIdSupplier(String idSupplier){
		this.idSupplier = idSupplier;
	}

	public String getIdSupplier(){
		return idSupplier;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	@Override
 	public String toString(){
		return 
			"DataSearchSupplierItem{" + 
			"nama = '" + nama + '\'' + 
			",jenis_supplier = '" + jenisSupplier + '\'' + 
			",id_jenis_supplier = '" + idJenisSupplier + '\'' + 
			",no_telpon = '" + noTelpon + '\'' + 
			",id_supplier = '" + idSupplier + '\'' + 
			",email = '" + email + '\'' + 
			",alamat = '" + alamat + '\'' + 
			"}";
		}
}