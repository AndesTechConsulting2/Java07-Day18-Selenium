package org.andestech.learning.rfb18.g2;

public class Login {

    public String getLogin() {
        return login;
    }

    private String login;
    // id, группа (роль), права, ....

    public Login(String login, String password) {
        this.login = login;
        this.password = password;
    }

    private String password;

    public String getPassword(){return password;}


}
