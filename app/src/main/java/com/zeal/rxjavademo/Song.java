package com.zeal.rxjavademo;

/**
 * @作者 廖伟健
 * @创建时间 2017/2/21 10:48
 * @描述 ${TODO} 
 */

public class Song {
    public long id;
    public String name;
    public long singerId;

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", singerId=" + singerId +
                '}';
    }
}
