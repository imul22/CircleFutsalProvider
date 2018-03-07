package com.kodecircle.circlefutsalprovider.model;
/**
 * This is the source code of CircleFutsal for Android ver.1.x.x.
 * It is licensed under GNU GPL v.3.
 * You should have received a copy of the license in this archive.
 * <p>
 * See license at :
 * https://github.com/ryanbekhen/circle_futsal/blob/master/LICENSE"
 * Copyright KODE CIRCLE, 2018.
 */

/**
 * @author MUST MULIARDI
 * @version 1.0.1
 * @since 11/02/18
 */
public class User {
    private String name,email,password;

    public User(){

    }
    public User(String name ,String email, String password){
        this.email = email;
        this.name = name;
        this.password = password;

    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

}
