package Service;

import Model.SanPham;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class SanPhamService extends SQLServerService {

    public Vector<SanPham> layTatCaSanPham() {
        Vector<SanPham> dsSp = new Vector<>();
        try {
            String sql = "SELECT * FROM SanPham WHERE IsDeleted = 0";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                SanPham sp = new SanPham();
                sp.setMaSp(result.getString("MaSP"));
                sp.setTenSp(result.getString("TenSP"));
                sp.setSoLuong(result.getInt("SoLuong"));
                sp.setDonGia(result.getInt("DonGia"));
                sp.setMaDM(result.getString("MaDM"));
                sp.setIsDeleted(result.getInt("IsDeleted"));
                dsSp.add(sp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dsSp;
    }


    public Vector<SanPham> docSanPhamTheoDanhMuc(String madm) {
        Vector<SanPham> dsSp = new Vector<>();
        try {
            String sql = "SELECT * FROM sanpham WHERE madm = ? AND isdeleted = 0";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, madm);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                SanPham sp = new SanPham();
                sp.setMaSp(result.getString("MaSP"));
                sp.setTenSp(result.getString("TenSP"));
                sp.setSoLuong(result.getInt("SoLuong"));
                sp.setDonGia(result.getInt("DonGia"));
                sp.setMaDM(result.getString("MaDM"));
                sp.setIsDeleted(result.getInt("IsDeleted"));
                dsSp.add(sp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dsSp;
    }


    public int xoaSanPham(String maSP) {
        int result = 0;
        try {
            String sql = "UPDATE sanpham SET isdeleted = 1 WHERE masp = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maSP);
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println("Xóa sản phẩm thành công: " + maSP);
            } else {
                System.out.println("Không tìm thấy sản phẩm để xóa: " + maSP);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return result;
    }

    public Vector<SanPham> timKiemSanPham(String tenSP) {
        Vector<SanPham> vec = new Vector<>();
        try {
            String sql = "SELECT * FROM SanPham WHERE TenSP LIKE ? AND IsDeleted = 0";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + tenSP + "%");
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                SanPham sp = new SanPham();
                sp.setMaSp(result.getString("MaSP"));
                sp.setTenSp(result.getString("TenSP"));
                sp.setSoLuong(result.getInt("SoLuong"));
                sp.setDonGia(result.getInt("DonGia"));
                sp.setMaDM(result.getString("MaDM"));
                sp.setIsDeleted(result.getInt("IsDeleted"));
                vec.add(sp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return vec;
    }

    public int themSanPham(SanPham sp) {
        try {
            String checkMaDM = "SELECT COUNT(*) FROM DanhMuc WHERE MaDM = ? AND IsDeleted = 0";
            PreparedStatement checkStatement = conn.prepareStatement(checkMaDM);
            checkStatement.setString(1, sp.getMaDM());
            ResultSet rs = checkStatement.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                JOptionPane.showMessageDialog(null, "Mã danh mục không tồn tại hoặc đã bị xóa.");
                return -1;
            }

            String sql = "INSERT INTO SanPham (MaSP, TenSP, SoLuong, DonGia, MaDM, IsDeleted) VALUES (?, ?, ?, ?, ?, 0)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, sp.getMaSp());
            preparedStatement.setString(2, sp.getTenSp());
            preparedStatement.setInt(3, sp.getSoLuong());
            preparedStatement.setInt(4, sp.getDonGia());
            preparedStatement.setString(5, sp.getMaDM());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    public int suaSanPham(SanPham sp) {
        int result = 0;
        try {
            String sql = "UPDATE sanpham SET TenSP = ?, SoLuong = ?, DonGia = ?, MaDM = ? WHERE MaSP = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, sp.getTenSp());
            preparedStatement.setInt(2, sp.getSoLuong());
            preparedStatement.setInt(3, sp.getDonGia());
            preparedStatement.setString(4, sp.getMaDM());
            preparedStatement.setString(5, sp.getMaSp());
            result = preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return result;
    }

    public Vector<SanPham> layTatCaSanPhamDaXoa() {
        Vector<SanPham> dsSpDaXoa = new Vector<>();
        try {
            String sql = "SELECT * FROM SanPham WHERE IsDeleted = 1";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                SanPham sp = new SanPham();
                sp.setMaSp(result.getString("MaSP"));
                sp.setTenSp(result.getString("TenSP"));
                sp.setSoLuong(result.getInt("SoLuong"));
                sp.setDonGia(result.getInt("DonGia"));
                sp.setMaDM(result.getString("MaDM"));
                sp.setIsDeleted(result.getInt("IsDeleted"));
                dsSpDaXoa.add(sp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return dsSpDaXoa;
    }

    public int khoiPhucSanPham(String maSP) {
        try {
            String sql = "UPDATE SanPham SET IsDeleted = 0 WHERE MaSP = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maSP);
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int xoaHoanToanSanPham(String maSP) {
        try {
            String sql = "DELETE FROM SanPham WHERE MaSP = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, maSP);
            return preparedStatement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }


}
