package edu.scausxxg.contacts.data;

/**
 * 联系人信息类
 */
public class User {
    private  String name; //用户名
    private  String mobile;//手机号码
    private  String qq;  //QQ
    private  int _id=-1;//数据库主键id

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
