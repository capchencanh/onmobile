package bt2;

public class NhanVien {
    private String maNV;
    private int maPB;
    private String tenNV;
    private int tuoi;

    public NhanVien(String maNV, int maPB, String tenNV, int tuoi) {
        this.maNV = maNV;
        this.maPB = maPB;
        this.tenNV = tenNV;
        this.tuoi = tuoi;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public int getMaPB() {
        return maPB;
    }

    public void setMaPB(int maPB) {
        this.maPB = maPB;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }
}