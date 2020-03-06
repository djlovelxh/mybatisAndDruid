package dj.mybatis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dj on 2020/2/22.
 */
@Data
public class User implements Serializable {
    private Integer id;
    private String  name;
    private Date birthday;
    private String  address;
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                '}';
    }


}
