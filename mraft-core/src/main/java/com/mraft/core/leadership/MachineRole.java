package com.mraft.core.leadership;

/**
 * Created by wenan.mr on 2017/11/24.
 *
 * @author wenan.mr
 * @date 2017/11/24
 */
public enum MachineRole {
    CANDIDATE("candidate"),FOLLOWER("follower"),LEADER("leader");

    private String role;

    MachineRole(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
