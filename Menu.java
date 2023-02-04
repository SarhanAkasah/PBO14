import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class Menu {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/toko";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;
    static InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    static BufferedReader input = new BufferedReader(inputStreamReader);

    public static void main(String[] args) {

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            while (!conn.isClosed()) {
                showMenu();
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void showMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Insert Data Buku");
        System.out.println("2. Insert Data Peminjam");
        System.out.println("3. Insert Data Petugas");
        System.out.println("4. Show Data All");
        System.out.println("5. Edit Data");
        System.out.println("6. Delete Data");
        System.out.println("7. Delete ALL Data");
        System.out.println("0. Keluar");
        System.out.println("");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = Integer.parseInt(input.readLine());

            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertbuku();
                    break;
                case 2:
                    insertPeminjam();
                    break;
                case 3:
                    insertPetugas();
                    break;
                case 4:
                    showData();
                    break;
                case 5:
                    updateBuku();
                    break;
                case 6:
                    deleteBuku();
                    break;
                default:
                    System.out.println("Pilihan salah!");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void showData() {
        String sql = "SELECT * FROM buku,tbl_peminjam,tbl_petugas";

        try {
            rs = stmt.executeQuery(sql);
            int nomor = 0;
            System.out.println("+--------------------------------+");
            System.out.println("|     DATA PEMINJAMAN BUKU      |");
            System.out.println("+--------------------------------+");

            while (rs.next()) {
                nomor++;
                System.out.println("Nomor:" + nomor);

                int id_buku = rs.getInt("id_buku");
                String nama_buku = rs.getString("nama_buku");
                String jenis_buku = rs.getString("jenis_buku");
                String nama_peminjam = rs.getString("nama_peminjam");
                int id_peminjam = rs.getInt("id_peminjam");
                String alamat_peminjam = rs.getString("alamat_peminjam");
                String nama_petugas = rs.getString("nama_petugas");
                int id_petugas = rs.getInt("id_petugas");
                String alamat_petugas = rs.getString("alamat_petugas");

                System.out.println(String.format("(%d) %s %s -- %s %s %s -- %s %s %s", id_buku, nama_buku,
                        jenis_buku, nama_peminjam, id_peminjam, alamat_peminjam, nama_petugas, id_petugas, alamat_petugas));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void insertbuku() {
        try {
            System.out.print("Masukan id buku: ");
            int id_buku = Integer.parseInt(input.readLine());
            System.out.print("Masukan Nama buku: ");
            String nama_buku = input.readLine();
            System.out.print("Masukan jenis buku: ");
            String jenis_buku = input.readLine();

            String sql = "INSERT INTO buku (id_buku, nama_buku, jenis_buku) VALUE('%s', '%s', '%s')";
            sql = String.format(sql, id_buku, nama_buku, jenis_buku);

            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    static void insertPeminjam() {
        try {
            System.out.print("Masukan id peminjam: ");
            int id_peminjam = Integer.parseInt(input.readLine().trim());
            System.out.print("Masukan nama peminjam: ");
            String nama_peminjam = input.readLine().trim();
            System.out.print("Masukan Alamat Peminjam: ");
            String alamat_peminjam = input.readLine().trim();

            String sql = "INSERT INTO tbl_peminjam (id_peminjam, nama_peminjam, alamat_peminjam,) VALUE('%s', '%s', '%s', '%s')";
            sql = String.format(sql, id_peminjam, nama_peminjam, alamat_peminjam);

            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void insertPetugas() {
        try {
            System.out.print("Masukan id petugas: ");
            int id_petugas = Integer.parseInt(input.readLine().trim());
            System.out.print("Masukan nama petugas: ");
            String nama_petugas = input.readLine();
            System.out.print("Tentukan alamat petugas: ");
            String alamat_petugas = input.readLine();

            String sql = "INSERT INTO tbl_petugas (id_petugas, nama_petugas, alamat_petugas) VALUE('%s', '%s', '%s', '%s')";
            sql = String.format(sql, id_petugas, nama_petugas, alamat_petugas);

            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void updateBuku() {
        try {
            System.out.print("id ID Buku yang mau diedit: ");
            int idbuku = Integer.parseInt(input.readLine());
            System.out.print("Masukan Nama Baru Buku: ");
            String nama_buku = input.readLine().trim();
            System.out.print("Masukan Jenis Buku Baru: ");
            String jenis_buku = input.readLine().trim();

            String sql = "UPDATE buku SET nama_buku='%s', jenis_buku='%s' WHERE id_buku=%s";
            sql = String.format(sql, nama_buku, jenis_buku, idbuku);

            stmt.execute(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void deleteBuku() {
        try {
            System.out.print("ID buku yang mau dihapus: ");
            int idbuku = Integer.parseInt(input.readLine());

            String sql = String.format("DELETE FROM buku WHERE id_buku=%s", idbuku);

            stmt.execute(sql);

            System.out.println("Data telah terhapus...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        static void deleteColumn() {
            try {
                System.out.print("Hapus semua data: ");
    
                String sql1 = "TRUNCATE TABLE supplier";
                String sql2 = "TRUNCATE TABLE tbl_barang";
                String sql3 = "TRUNCATE TABLE tbl_makanan";
    
                stmt.execute(sql1);
                stmt.execute(sql2);
                stmt.execute(sql3);
    
                System.out.println("Data semua telah terhapus...");
            } catch (Exception e) {
                e.printStackTrace();
    }
}
}