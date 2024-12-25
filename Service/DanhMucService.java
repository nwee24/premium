package Service;

import Model.DanhMuc;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DanhMucService extends SQLServerService {

    public Vector<DanhMuc> layTatCaDanhMuc() {
        Vector<DanhMuc> dsDanhMuc = new Vector<>();
        try {
            String sql = "SELECT * FROM DanhMuc WHERE IsDeleted = 0";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                DanhMuc dm = new DanhMuc();
                dm.setMaDM(result.getString("MaDM"));
                dm.setTenDM(result.getString("TenDM"));
                dm.setIsDelete(result.getInt("IsDeleted"));
                dsDanhMuc.add(dm);
                System.out.println("DanhMuc: " + dm.getMaDM() + ", " + dm.getTenDM());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Số lượng danh mục: " + dsDanhMuc.size());
        return dsDanhMuc;
    }



    public Vector<DanhMuc> DocToanBoDanhMuc() {
        Vector<DanhMuc> vec = new Vector<DanhMuc>();
        try {
            String sql = "select * from DanhMuc where IsDeleted =0";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                DanhMuc dm = new DanhMuc();
                dm.setMaDM(result.getString(1));
                dm.setTenDM(result.getString(2));
                dm.setIsDelete(0);
                vec.add(dm);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vec;
    }

    public int themDanhMuc(DanhMuc dm) {
        int result = 0;
        try {
            String sql = "INSERT INTO DanhMuc VALUES (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, dm.getMaDM());
            preparedStatement.setString(2, dm.getTenDM());
            preparedStatement.setInt(3, 0);
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int capNhatDanhMuc(DanhMuc danhMuc) {
        try {
            String sql = "UPDATE DanhMuc SET TenDM= ? WHERE MaDM= ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, danhMuc.getTenDM());
            preparedStatement.setString(2, danhMuc.getMaDM());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int xoaDanhMuc(String maDM) {
        int result = 0;
        try {
            // Đánh dấu tất cả sản phẩm thuộc danh mục này là bị xóa
            String sqlUpdateSanPham = "UPDATE SanPham SET IsDeleted = 1 WHERE MaDM = ?";
            PreparedStatement preparedStatementUpdateSanPham = conn.prepareStatement(sqlUpdateSanPham);
            preparedStatementUpdateSanPham.setString(1, maDM);
            preparedStatementUpdateSanPham.executeUpdate();

            // Đánh dấu danh mục là bị xóa
            String sql = "UPDATE DanhMuc SET IsDeleted = 1 WHERE MaDM = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maDM);
            result = preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    public Vector<DanhMuc> timKiemDanhMuc(String tenDM) {
        Vector<DanhMuc> vec = new Vector<>();
        try {
            String sql = "SELECT * FROM DanhMuc WHERE TenDM LIKE ? AND IsDeleted = 0";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "%" + tenDM + "%");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                DanhMuc dm = new DanhMuc();
                dm.setMaDM(result.getString(1));
                dm.setTenDM(result.getString(2));
                dm.setIsDelete(0);
                vec.add(dm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vec;
    }

    public Vector<DanhMuc> layTatCaDanhMucDaXoa() {
        Vector<DanhMuc> dsDanhMucDaXoa = new Vector<>();
        try {
            String sql = "SELECT * FROM DanhMuc WHERE IsDeleted = 1";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                DanhMuc dm = new DanhMuc();
                dm.setMaDM(result.getString("MaDM"));
                dm.setTenDM(result.getString("TenDM"));
                dm.setIsDelete(result.getInt("IsDeleted"));
                dsDanhMucDaXoa.add(dm);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dsDanhMucDaXoa;
    }

    public int khoiPhucDanhMuc(String maDM) {
        try {
            String sql = "UPDATE DanhMuc SET IsDeleted = 0 WHERE MaDM = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maDM);
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int xoaHoanToanDanhMuc(String maDM) {
        try {
            String sql = "DELETE FROM DanhMuc WHERE MaDM = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maDM);
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }



}
