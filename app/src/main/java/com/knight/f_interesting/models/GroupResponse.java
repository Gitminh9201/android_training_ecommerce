package com.knight.f_interesting.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GroupResponse {
    @SerializedName("count")
    private int count;

    @SerializedName("status")
    private int status;

    @SerializedName("groups")
    private List<Group> groups;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public GroupResponse(int count, int status, List<Group> groups) {
        this.count = count;
        this.status = status;
        this.groups = groups;
    }
}
