package ranggacikal.com.myapplication.model;

public class Keranjang {

    int jumlah;
    int total_harga;
    int id_penjualan;
    int id_barang;
    String status_penjualan;

    public Keranjang() {
    }

    public Keranjang(int jumlah, int total_harga, int id_penjualan, int id_barang, String status_penjualan) {
        this.jumlah = jumlah;
        this.total_harga = total_harga;
        this.id_penjualan = id_penjualan;
        this.id_barang = id_barang;
        this.status_penjualan = status_penjualan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(int total_harga) {
        this.total_harga = total_harga;
    }

    public int getId_penjualan() {
        return id_penjualan;
    }

    public void setId_penjualan(int id_penjualan) {
        this.id_penjualan = id_penjualan;
    }

    public int getId_barang() {
        return id_barang;
    }

    public void setId_barang(int id_barang) {
        this.id_barang = id_barang;
    }

    public String getStatus_penjualan() {
        return status_penjualan;
    }

    public void setStatus_penjualan(String status_penjualan) {
        this.status_penjualan = status_penjualan;
    }
}
