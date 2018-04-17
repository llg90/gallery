package com.wuhan.gallery.bean;

/*
"id": 13,
        "username": "jim",
        "password": "123456",
        "email": "317458345@qq.com",
        "telephone": "13297963867",
        "icon": "3I9J9C20180416154258.jpg",
        "createdate": "2018-04-16T07:43:12.000Z",
        "logindate": "2018-04-16T07:43:12.000Z"
*/
public class UserBean {
    private int id;
    private String username;
    private String email;
    private String telephone;
    private String icon;
    private String createdate;
    private String logindate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getLogindate() {
        return logindate;
    }

    public void setLogindate(String logindate) {
        this.logindate = logindate;
    }
}
