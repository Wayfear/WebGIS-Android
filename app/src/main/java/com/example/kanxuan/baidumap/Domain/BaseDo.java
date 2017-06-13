package com.example.kanxuan.baidumap.Domain;

import java.io.Serializable;
import java.security.Timestamp;

/**
 * Created by kanxuan on 2017/6/8.
 */

public class BaseDo implements Serializable {

    protected Integer id;
    protected Timestamp create_time;
    protected Timestamp update_time;

    public BaseDo() {
    }

    public BaseDo(Integer id, Timestamp create_time, Timestamp update_time) {
        this.id = id;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }
}
