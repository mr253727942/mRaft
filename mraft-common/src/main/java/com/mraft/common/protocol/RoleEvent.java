package com.mraft.common.protocol;

/**
 * Created by wenan.mr on 2018/2/26.
 *
 * @author wenan.mr
 * @date 2018/02/26
 */
public enum RoleEvent {

    CANDIDATE_EVET("candidateEvent"),FOLLOWER_EVENT("followerEvent"),LEADER_EVENT("leaderEvent");

    private String role;

    RoleEvent(String role){
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
