package sunkeding.com.aidlstudy.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: skd
 * @date 2018/10/7
 * @Desc StudentBean
 */
public class StudentBean implements Parcelable {

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    private String name;
    private int age;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.age);
    }

    @Override
    public String toString() {
        return "StudentBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public StudentBean() {
    }

    protected StudentBean(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    public static final Creator<StudentBean> CREATOR = new Creator<StudentBean>() {
        @Override
        public StudentBean createFromParcel(Parcel source) {
            return new StudentBean(source);
        }

        @Override
        public StudentBean[] newArray(int size) {
            return new StudentBean[size];
        }
    };
}
