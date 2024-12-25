package Model;

public class SanPham {
    private String maSp;
    private String tenSp;
    private int soLuong;
    private int donGia;
    private String maDM;
    private int isDeleted;

    public SanPham(String maSp, String tenSp, int soLuong, int donGia, String maDM, int isDeleted) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.maDM = maDM;
        this.isDeleted = isDeleted;
    }

    public SanPham(String maSP, String tenSP, int soLuong, int donGia) {
        this(maSP, tenSP, soLuong, donGia,"",0);
    }

    public SanPham() {

    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getMaDM() {
        return maDM;
    }

    public void setMaDM(String maDM) {
        this.maDM = maDM;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
