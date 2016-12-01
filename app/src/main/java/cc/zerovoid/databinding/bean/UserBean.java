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

    public void setAge(String age) {
        this.age = age;
    }

    private String age;

    public UserBean(String name, String age) {
        this.name = name;
        this.age = age;
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

}
