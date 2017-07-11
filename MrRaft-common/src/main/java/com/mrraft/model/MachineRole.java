package com.mrraft.model;

/**
 * Created by T460P on 2017/6/28.
 */
public enum MachineRole {

    FOLLOWER("follower"),LEADER("leader"),CANDIDATE("candidate");

    private String role;
    MachineRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
