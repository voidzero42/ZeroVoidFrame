package cc.zerovoid.house.model;

/**
 * Created by Administrator on 2015/11/11.
 */
public class HouseBean {
    /** 房屋信息 */
    private String houseInfo;
    /** 亲属列表 */
    private String family;
    /** 租客列表 */
    private String tenant;
    /** 身份（0户主，1亲属，2租客） */
    private String identity;

    public static final String ROLE_TYPE_OWNER = "户主";
    public static final String ROLE_TYPE_FAMILY = "家属";
    public static final String ROLE_TYPE_TENANT = "租客";

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
