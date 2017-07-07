package com.example.android.executive;

public class User {

    public String name;
    public String password;
    public String username;
    public Long  phno;
    public String speciality;

    public User(){

    }


    public User(String name, String password,String username,Long phno) {
        this.name=name;
        this.password=password;
        this.username=username;
        this.phno=phno;
        // ...
    }

    public User(String name, String password,String username,Long phno,String speciality) {
        this.name=name;
        this.password=password;
        this.username=username;
        this.phno=phno;
        this.speciality=speciality;
        // ...
    }

    public String getname(){return name;}

    public String getPassword(){return password;}

    public String getUsername(){return username;}

    public Long getPhno(){return phno;}

    public void setname(String name){this.name=name;}

    public void setPassword(String password){this.password=password;}

    public void setUsername(String username){this.username=username;}

    public  void setPhno(Long phno){this.phno=phno;}

    public void setSpeciality(String speciality){this.speciality=speciality;}

    public String getSpeciality(){return this.speciality;}

}
