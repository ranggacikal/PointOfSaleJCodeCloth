package ranggacikal.com.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class DataBarangItem{

	@SerializedName("ukuran_barang")
	private String ukuranBarang;

	@SerializedName("id_barang")
	private String idBarang;

	@SerializedName("id_jenis_barang")
	private String idJenisBarang;

	@SerializedName("kode_barang")
	private String kodeBarang;

	@SerializedName("harga_beli")
	private String hargaBeli;

	@SerializedName("nama_barang")
	private String namaBarang;

	@SerializedName("stok")
	private String stok;

	@SerializedName("harga_jual")
	private String hargaJual;

	@SerializedName("image_barang")
	private String imageBarang;

	@SerializedName("jenis_barang")
	private String jenisBarang;

	public void setUkuranBarang(String ukuranBarang){
		this.ukuranBarang = ukuranBarang;
	}

	public String getUkuranBarang(){
		return ukuranBarang;
	}

	public void setIdBarang(String idBarang){
		this.idBarang = idBarang;
	}

	public String getIdBarang(){
		return idBarang;
	}

	public void setIdJenisBarang(String idJenisBarang){
		this.idJenisBarang = idJenisBarang;
	}

	public String getIdJenisBarang(){
		return idJenisBarang;
	}

	public void setKodeBarang(String kodeBarang){
		this.kodeBarang = kodeBarang;
	}

	public String getKodeBarang(){
		return kodeBarang;
	}

	public void setHargaBeli(String hargaBeli){
		this.hargaBeli = hargaBeli;
	}

	public String getHargaBeli(){
		return hargaBeli;
	}

	public void setNamaBarang(String namaBarang){
		this.namaBarang = namaBarang;
	}

	public String getNamaBarang(){
		return namaBarang;
	}

	public void setStok(String stok){
		this.stok = stok;
	}

	public String getStok(){
		return stok;
	}

	public void setHargaJual(String hargaJual){
		this.hargaJual = hargaJual;
	}

	public String getHargaJual(){
		return hargaJual;
	}

	public void setImageBarang(String imageBarang){
		this.imageBarang = imageBarang;
	}

	public String getImageBarang(){
		return imageBarang;
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
			"DataBarangItem{" + 
			"ukuran_barang = '" + ukuranBarang + '\'' + 
			",id_barang = '" + idBarang + '\'' + 
			",id_jenis_barang = '" + idJenisBarang + '\'' + 
			",kode_barang = '" + kodeBarang + '\'' + 
			",harga_beli = '" + hargaBeli + '\'' + 
			",nama_barang = '" + namaBarang + '\'' + 
			",stok = '" + stok + '\'' + 
			",harga_jual = '" + hargaJual + '\'' + 
			",image_barang = '" + imageBarang + '\'' + 
			",jenis_barang = '" + jenisBarang + '\'' + 
			"}";
		}
}