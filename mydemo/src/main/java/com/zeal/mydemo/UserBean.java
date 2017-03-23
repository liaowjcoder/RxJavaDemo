package com.zeal.mydemo;

public class UserBean {

    private String email;
    private String imageUrl;
    private String perName;
    private String code;
    private String msg;
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    private String userId;

    private int resId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", perName='" + perName + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", sex='" + sex + '\'' +
                ", userId='" + userId + '\'' +
                ", resId=" + resId +
                '}';
    }
}
