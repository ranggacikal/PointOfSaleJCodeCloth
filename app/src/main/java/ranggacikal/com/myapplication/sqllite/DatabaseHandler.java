package ranggacikal.com.myapplication.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ranggacikal.com.myapplication.model.Keranjang;

public class DatabaseHandler extends SQLiteOpenHelper {

    //InnerClass, untuk mengatur artibut seperti Nama Tabel, nama-nama kolom dan Query
    public static abstract class MyColumns implements BaseColumns {
        //Menentukan Nama Table dan Kolom
        public static final String NamaTabel = "Keranjang";
        public static final String jumlah = "jumlah";
        public static final String total_harga = "TotalHarga";
        public static final String id_penjualan = "idPenjualan";
        public static final String id_barang = "idBarang";
        public static final String status = "status";
    }

    private static final String NamaDatabse = "Keranjang.db";
    private static final int VersiDatabase = 14;

    //Query yang digunakan untuk membuat Tabel
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+MyColumns.NamaTabel+
            "("+MyColumns.jumlah+" INTEGER NOT NULL, "+MyColumns.total_harga+
            " INTEGER NOT NULL, "+MyColumns.id_penjualan+" INTEGER NOT NULL, "+MyColumns.id_barang+
            " INTEGER NOT NULL, "+MyColumns.status+" TEXT NOT NULL)";

    //Query yang digunakan untuk mengupgrade Tabel
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+MyColumns.NamaTabel;

    public DatabaseHandler(Context context) {
        super(context, NamaDatabse, null, VersiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insertData(int jumlah, int total_harga, int id_penjualan, int id_barang, String status){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyColumns.jumlah, jumlah);
        values.put(MyColumns.total_harga, total_harga);
        values.put(MyColumns.id_penjualan, id_penjualan);
        values.put(MyColumns.id_barang, id_barang);
        values.put(MyColumns.status, status);

        long result = db.insert(MyColumns.NamaTabel, null, values);

        if (result == -1){
            return false;
        }else{
            return true;
        }
    }


    public List<Keranjang> KeranjangByIdPenjualan(String id_penjualan) {
        List<Keranjang> keranjangList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM "+NamaDatabse+" where "+MyColumns.id_penjualan+ "='"+id_penjualan+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Keranjang keranjang = new Keranjang();
                keranjang.setJumlah(cursor.getInt(0));
                keranjang.setTotal_harga(cursor.getInt(1));
                keranjang.setId_penjualan(cursor.getInt(2));
                keranjang.setId_barang(cursor.getInt(3));
                keranjang.setStatus_penjualan(cursor.getString(4));
                keranjangList.add(keranjang);
            } while (cursor.moveToNext());
        }

        db.close();
        return keranjangList;
    }

}
