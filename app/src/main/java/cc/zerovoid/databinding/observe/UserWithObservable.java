package cc.zerovoid.databinding.observe;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.zerovoid.zerovoidframe.BR;


/**
 * Created by orangelife on 16/12/5.
 */

public class UserWithObservable extends BaseObservable {

    private String name;
    private String age;
    private boolean isAdult;

    public UserWithObservable(String name, String age, boolean isAdult) {
        this.name = name;
        this.age = age;
        this.isAdult = isAdult;
    }

    @Bindable
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
        notifyPropertyChanged(BR.user);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.user);
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

}
