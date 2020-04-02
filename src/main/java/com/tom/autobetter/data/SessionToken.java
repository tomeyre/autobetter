package com.tom.autobetter.data;

public class SessionToken {

    private static SessionToken sessionToken;
    private String ssoId;

    public static SessionToken getInstance(){
        if(sessionToken == null){
            sessionToken = new SessionToken();
        }
        return sessionToken;
    }

    private SessionToken(){
    }

    public String getSsoId() {
        return ssoId;
    }

    public void setSsoId(String ssoId) {
        this.ssoId = ssoId;
    }
}
