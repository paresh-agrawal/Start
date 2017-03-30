package com.example.paresh.start;

/**
 * Created by root on 23/3/17.
 */

public class UserDetails {

    private String name;
    private String phone_number;
    private String email;
    private String room_no;

    public UserDetails(String name, String phone_number, String email,String room_no){
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.room_no = room_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoom_no(){return room_no;}

    public void setRoom_no(String room_no){this.room_no = room_no;}
}
