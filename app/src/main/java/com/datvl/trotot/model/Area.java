package com.datvl.trotot.model;

public class Area {
    private int id;
    private String name;
    private String gps;
    private int is_save;
    private String time;

    public Area(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Area(int id, String name, String gps) {
        this.id = id;
        this.name = name;
        this.gps = gps;
    }

    public Area(int id, String name, int is_save) {
        this.id = id;
        this.name = name;
        this.is_save = is_save;
    }

    public int getIs_save() {
        return is_save;
    }

    public void setIs_save(int is_save) {
        this.is_save = is_save;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    @Override
    public String toString() {
        return name;
    }
}
