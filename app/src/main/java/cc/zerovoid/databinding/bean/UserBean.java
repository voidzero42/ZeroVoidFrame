package cc.zerovoid.databinding.bean;

/**
 * <p>
 * Created by 吴格非 on 2016-11-23.
 * <p>
 *
 * @author 吴格非
 * @since v1.0.0
 */

public class UserBean {

    private String name;

    private String age;

    private boolean isAdult;

    public UserBean(String name, String age, boolean isAdult) {
        this.name = name;
        this.age = age;
        this.isAdult = isAdult;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }
}
