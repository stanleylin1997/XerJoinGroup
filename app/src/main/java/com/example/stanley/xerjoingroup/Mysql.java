package com.example.stanley.xerjoingroup;

/**
 * Created by Stanley on 2018/3/14.
 */

public class Mysql {

    public String addUserToGroupSql(int uid, int gid){
        String joingroup ="INSERT INTO joingroup (uid,gid) VALUES('"+uid+"','"+gid+"');" ;
        return joingroup;
    }

    public String createNewGroup(String name, String Type, String place, int uid,
                                 String date, int number, int remain){
        String createNewGroup ="INSERT into findgroup(gid, name,type, place, uid, date, number,remain)" +
                "VALUES "+ "(NULL,'"+name+"','"+Type+"','"+place+"','"+uid+"','"+date+"','"+number+"','"+remain+"');";
        return createNewGroup;
    }

    public String getGroup(){
        String selectGroup = "SELECT * FROM findgroup;";
        return  selectGroup;
    }

}
