package ranggacikal.com.myapplication.api;


import ranggacikal.com.myapplication.model.ResponseDataBarang;
import ranggacikal.com.myapplication.model.ResponseDataJenisBarang;
import ranggacikal.com.myapplication.model.ResponseDataJenisSupplier;
import ranggacikal.com.myapplication.model.ResponseDataPembelian;
import ranggacikal.com.myapplication.model.ResponseDataPenjualan;
import ranggacikal.com.myapplication.model.ResponseDataSupplier;
import ranggacikal.com.myapplication.model.ResponseDataUser;
import ranggacikal.com.myapplication.model.ResponseDetailPenjualan;
import ranggacikal.com.myapplication.model.ResponseEditBarang;
import ranggacikal.com.myapplication.model.ResponseEditImageBarang;
import ranggacikal.com.myapplication.model.ResponseEditImageUser;
import ranggacikal.com.myapplication.model.ResponseEditJenisBarang;
import ranggacikal.com.myapplication.model.ResponseEditJenisSupplier;
import ranggacikal.com.myapplication.model.ResponseEditPembelian;
import ranggacikal.com.myapplication.model.ResponseEditStatusDetailPenjualan;
import ranggacikal.com.myapplication.model.ResponseEditStatusPenjualan;
import ranggacikal.com.myapplication.model.ResponseEditSupplier;
import ranggacikal.com.myapplication.model.ResponseEditTotalPenjualan;
import ranggacikal.com.myapplication.model.ResponseEditUser;
import ranggacikal.com.myapplication.model.ResponseGantiPasswordUser;
import ranggacikal.com.myapplication.model.ResponseGetIdPenjualan;
import ranggacikal.com.myapplication.model.ResponseHapusBarang;
import ranggacikal.com.myapplication.model.ResponseHapusDetailPenjualan;
import ranggacikal.com.myapplication.model.ResponseHapusJenisSupplier;
import ranggacikal.com.myapplication.model.ResponseHapusPembelian;
import ranggacikal.com.myapplication.model.ResponseHapusPenjualan;
import ranggacikal.com.myapplication.model.ResponseHapusSupplier;
import ranggacikal.com.myapplication.model.ResponseHapusUser;
import ranggacikal.com.myapplication.model.ResponseLaporanByBulan;
import ranggacikal.com.myapplication.model.ResponseLogin;
import ranggacikal.com.myapplication.model.ResponseRegister;
import ranggacikal.com.myapplication.model.ResponseSearchBarang;
import ranggacikal.com.myapplication.model.ResponseSearchDataPembelian;
import ranggacikal.com.myapplication.model.ResponseSearchJenisBarang;
import ranggacikal.com.myapplication.model.ResponseSearchJenisSupplier;
import ranggacikal.com.myapplication.model.ResponseSearchSupplier;
import ranggacikal.com.myapplication.model.ResponseTambahBarang;
import ranggacikal.com.myapplication.model.ResponseTambahDetailPenjualan;
import ranggacikal.com.myapplication.model.ResponseTambahJenisBarang;
import ranggacikal.com.myapplication.model.ResponseTambahJenisSupplier;
import ranggacikal.com.myapplication.model.ResponseTambahPembelian;
import ranggacikal.com.myapplication.model.ResponseTambahPenjualan;
import ranggacikal.com.myapplication.model.ResponseTambahSupplier;
import ranggacikal.com.myapplication.model.ResponseTotalDetailPenjualan;
import ranggacikal.com.myapplication.model.ResponseTotalPemasukan;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @FormUrlEncoded
    @POST("Register")
    Call<ResponseRegister> Register(@Field("name") String name,
                                    @Field("email") String email,
                                    @Field("password") String password,
                                    @Field("level") String level,
                                    @Field("image_user") String image_user);

    @FormUrlEncoded
    @POST("Login")
    Call<ResponseLogin> Login(@Field("email") String email,
                              @Field("password") String password);

    @GET("GetDataUser")
    Call<ResponseDataUser> DataUser();

    @GET("GetDataBarang")
    Call<ResponseDataBarang> DataBarang();

    @GET("GetJenisBarang")
    Call<ResponseDataJenisBarang> JenisBarang();

    @FormUrlEncoded
    @POST("TambahBarang")
    Call<ResponseTambahBarang> TambahBarang(@Field("kode_barang") String kode_barang,
                                            @Field("nama_barang") String nama_barang,
                                            @Field("harga_beli") int harga_beli,
                                            @Field("harga_jual") int harga_jual,
                                            @Field("stok") int stok,
                                            @Field("id_jenis_barang") int jenis_barang,
                                            @Field("ukuran_barang") String ukuran_barang,
                                            @Field("image_barang") String image_barang);

    @GET("GetSearchBarang/{kode_barang}")
    Call<ResponseSearchBarang> SearchBarang(@Path("kode_barang") String kode_barang);

    @FormUrlEncoded
    @POST("EditBarang")
    Call<ResponseEditBarang> EditBarang(@Field("id_barang") int id_barang,
                                        @Field("kode_barang") String kode_barang,
                                        @Field("nama_barang") String nama_barang,
                                        @Field("harga_beli") int harga_beli,
                                        @Field("harga_jual") int harga_jual,
                                        @Field("stok") int stok,
                                        @Field("id_jenis_barang") int id_jenis_barang,
                                        @Field("ukuran_barang") String ukuran_barang);

    @FormUrlEncoded
    @POST("HapusBarang")
    Call<ResponseHapusBarang> HapusBarang(@Field("id_barang") String id_barang);

    @FormUrlEncoded
    @POST("EditImageBarang")
    Call<ResponseEditImageBarang> EditImageBarang(@Field("id_barang") int id_barang,
                                                  @Field("image_barang") String image_barang);

    @GET("GetSearchJenisBarang/{id_jenis_barang}")
    Call<ResponseSearchJenisBarang> SearchJenisBarang(@Path("id_jenis_barang") String id_jenis_barang);

    @FormUrlEncoded
    @POST("TambahJenisBarang")
    Call<ResponseTambahJenisBarang> TambahJenisBarang(@Field("jenis_barang") String jenis_barang);

    @FormUrlEncoded
    @POST("EditJenisBarang")
    Call<ResponseEditJenisBarang> EditJenisBarang(@Field("id_jenis_barang") String id_jenis_barang,
                                                  @Field("jenis_barang") String jenis_barang);

    @GET("GetDataSupplier")
    Call<ResponseDataSupplier> DataSupplier();

    @FormUrlEncoded
    @POST("TambahPembelian")
    Call<ResponseTambahPembelian> TambahPembelian(@Field("id_barang") int id_barang,
                                                  @Field("tanggal_pembelian") String tanggal_pembelian,
                                                  @Field("jumlah") int jumlah,
                                                  @Field("total_harga") int total_harga,
                                                  @Field("id_supplier") int id_supplier,
                                                  @Field("id_user") String id_user);

    @GET("GetDataPembelian")
    Call<ResponseDataPembelian> DataPembelian();

    @FormUrlEncoded
    @POST("EditPembelian")
    Call<ResponseEditPembelian> EditPembelian(@Field("id_pembelian") int id_pembelian,
                                              @Field("id_barang") int id_barang,
                                              @Field("tanggal_pembelian") String tanggal_pembelian,
                                              @Field("jumlah") int jumlah,
                                              @Field("total_harga") int total_harga,
                                              @Field("id_supplier") int id_supplier,
                                              @Field("id_user") String id_user);

    @FormUrlEncoded
    @POST("HapusPembelian")
    Call<ResponseHapusPembelian> HapusPembelian(@Field("id_pembelian") String id_pembelian);

    @GET("GetSearchDataPembelian/{id_pembelian}")
    Call<ResponseSearchDataPembelian> SearchPembelian(@Path("id_pembelian") String id_pembelian);

    @FormUrlEncoded
    @POST("TambahPenjualan")
    Call<ResponseTambahPenjualan> TambahPenjualan(@Field("tanggal_penjualan") String tanggal_penjualan,
                                                  @Field("total") int total,
                                                  @Field("id_user") String id_user,
                                                  @Field("status_penjualan") String status_penjualan);

    @GET("GetIdPenjualan")
    Call<ResponseGetIdPenjualan> IdPenjulan();

    @GET("GetDataPenjualan")
    Call<ResponseDataPenjualan> DataPenjualan();

    @FormUrlEncoded
    @POST("TambahDetailPenjualan")
    Call<ResponseTambahDetailPenjualan> TambahDetailPenjualan(@Field("jumlah") int jumlah,
                                                              @Field("total_harga") int total_harga,
                                                              @Field("id_penjualan") int id_penjualan,
                                                              @Field("id_barang") int id_barang,
                                                              @Field("status_penjualan") String status_penjualan);

    @GET("GetDataDetailPenjualan/{id_penjualan}")
    Call<ResponseDetailPenjualan> DetailPenjualan(@Path("id_penjualan") String id_penjualan);

    @GET("TotalHargaDetailPenjualan/{id_penjualan}")
    Call<ResponseTotalDetailPenjualan> TotalDetailPenjualan(@Path("id_penjualan") String id_penjualan);

    @FormUrlEncoded
    @POST("DeletePenjualan")
    Call<ResponseHapusPenjualan> HapusPenjualan(@Field("id_penjualan") String id_penjualan);

    @FormUrlEncoded
    @POST("EditTotalHargaPenjualan")
    Call<ResponseEditTotalPenjualan> TotalPenjualan(@Field("id_penjualan") String id_penjualan,
                                                    @Field("total_harga") String total_harga);
    @DELETE("HapusDetailPenjualan/{id_penjualan}")
    Call<ResponseHapusDetailPenjualan> HapusDetailPenjualan(@Path("id_penjualan") String id_penjualan);

    @FormUrlEncoded
    @POST("EditStatusPenjualan")
    Call<ResponseEditStatusPenjualan> EditStatusPenjualan(@Field("id_penjualan") String id_penjualan,
                                                          @Field("status_penjualan") String status_penjualan);

    @FormUrlEncoded
    @POST("EditStatusDetailPenjualan/{id_penjualan}")
    Call<ResponseEditStatusDetailPenjualan> EditStatusDetailPenjualan(@Path("id_penjualan") String id_penjualan,
                                                                      @Field("status") String status_penjualan);

    @GET("GetLaporanByBulan/{bulan}")
    Call<ResponseLaporanByBulan> LaporanPenjualan(@Path("bulan") String bulan);

    @GET("PemasukanLaporanByBulan/{bulan}")
    Call<ResponseTotalPemasukan> TotalPemasulan(@Path("bulan") String bulan);

    @FormUrlEncoded
    @POST("TambahSupplier")
    Call<ResponseTambahSupplier> TambahSupplier(@Field("nama") String nama,
                                                @Field("no_telpon") String no_telpon,
                                                @Field("alamat") String alamat,
                                                @Field("email") String email,
                                                @Field("id_jenis_supplier") String id_jenis_suppier);

    @GET("GetDataJenisSupplier")
    Call<ResponseDataJenisSupplier> JenisSupplier();

    @GET("GetSearchSupplier/{id_supplier}")
    Call<ResponseSearchSupplier> SearchSupplier(@Path("id_supplier") String id_supplier);

    @FormUrlEncoded
    @POST("EditSupplier")
    Call<ResponseEditSupplier> EditSupplier(@Field("id_supplier") String id_supplier,
                                            @Field("nama") String nama,
                                            @Field("no_telpon") String no_telpon,
                                            @Field("alamat") String alamat,
                                            @Field("email") String email,
                                            @Field("id_jenis_supplier") String id_jenis_supplier);

    @GET("GetSearchJenisSupplier/{id_jenis_suplier}")
    Call<ResponseSearchJenisSupplier> SearchJenisSupplier(@Path("id_jenis_suplier") String id_jenis_suplier);

    @FormUrlEncoded
    @POST("HapusSupplier")
    Call<ResponseHapusSupplier> HapusSupplier(@Field("id_supplier") String id_supplier);

    @FormUrlEncoded
    @POST("TambahJenisSupplier")
    Call<ResponseTambahJenisSupplier> TambahJenisSupplier(@Field("jenis_supplier") String jenis_supplier);

    @FormUrlEncoded
    @POST("EditJenisSupplier")
    Call<ResponseEditJenisSupplier> EditJenisSupplier(@Field("id_jenis_suplier") String id_jenis_suplier,
                                                      @Field("jenis_supplier") String jenis_supplier);

    @FormUrlEncoded
    @POST("HapusJenisSupplier")
    Call<ResponseHapusJenisSupplier> HapusJenisSupplier(@Field("id_jenis_suplier") String id_jenis_suplier);

    @FormUrlEncoded
    @POST("EditUser")
    Call<ResponseEditUser> EditUser(@Field("id") String id,
                                    @Field("name") String name,
                                    @Field("email") String email,
                                    @Field("level") String level);

    @FormUrlEncoded
    @POST("EditImageUser")
    Call<ResponseEditImageUser> EditImageUser(@Field("id") String id,
                                              @Field("image_user") String image_user);

    @FormUrlEncoded
    @POST("GantiPasswordUser")
    Call<ResponseGantiPasswordUser> GantiPassword(@Field("id") String id,
                                                  @Field("password") String password);

    @FormUrlEncoded
    @POST("HapusUser")
    Call<ResponseHapusUser> HapusUser(@Field("id") String id);

}
