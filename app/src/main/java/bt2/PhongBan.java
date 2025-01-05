package bt2;

public class PhongBan {
    private int maPB;
    private String tenPB;
    private int soPhong;

    public PhongBan(int maPB, String tenPB, int soPhong) {
        this.maPB = maPB;
        this.tenPB = tenPB;
        this.soPhong = soPhong;
    }

    public int getMaPB() { return maPB; }
    public void setMaPB(int maPB) { this.maPB = maPB; }

    public String getTenPB() { return tenPB; }
    public void setTenPB(String tenPB) { this.tenPB = tenPB; }

    public int getSoPhong() { return soPhong; }
    public void setSoPhong(int soPhong) { this.soPhong = soPhong; }
}
