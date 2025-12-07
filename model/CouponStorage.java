
import java.util.ArrayList;
import java.util.List;

public class CouponStorage {
    private static List<Coupon> coupons = new ArrayList<>();

    public static void addCoupon(Coupon coupon) {
        coupons.add(coupon);
    }

    public static Coupon find(String code) {
        for (Coupon c : coupons) {
            if (c.getCode().equalsIgnoreCase(code)) return c;
        }
        return null;
    }

    public static List<Coupon> getCoupons() {
        return coupons;
    }
}
