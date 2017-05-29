package com.example.app;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

/**
 * Created by DBW on 2017/5/28.
 *
 */

public class LitePalUtil {

    public static void init(){
        Connector.getDatabase();
    }

    public static void save( Sticker sticker){

        if (DataSupport.isExist(Sticker.class,"position=?", String.valueOf(sticker.getPosition()))){
            update(sticker);
        }else {
            sticker.save();
        }
    }

    public static List<Sticker> getAll(){

        return DataSupport.findAll(Sticker.class);

    }

    public static boolean update(Sticker sticker){
        int col =  sticker.updateAll("position=?", String.valueOf(sticker.getPosition()));
        return col!=0;
    }

    public static boolean delete(Sticker sticker){
        int col = sticker.delete();
        return col != 0;
    }
    public static boolean deleteAll(){

        int col = DataSupport.deleteAll(Sticker.class);
        return col!=0;
    }

    public static void setToDefault(String column){

        Sticker sticker  = new Sticker();
        sticker.setToDefault(column);
        sticker.updateAll();

    }

}
