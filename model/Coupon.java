
import java.util.Date;

public class Coupon {
    private String code;
    private double percentOff;
    private Date expirationDate;

    public Coupon(String code, double percentOff, Date expirationDate) {
        this.code = code;
        this.percentOff = percentOff;
        this.expirationDate = expirationDate;
    }

    public String getCode() {
        return code;
    }

    public double getPercentOff() {
        return percentOff;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }
}