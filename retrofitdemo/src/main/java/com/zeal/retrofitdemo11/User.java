package com.zeal.retrofitdemo11;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/28 19:44
 * @描述 ${TODO} 
 */
public class User {
    public String username;
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
