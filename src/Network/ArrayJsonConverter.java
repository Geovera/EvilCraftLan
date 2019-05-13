/*
 * Copyright (C) 2019 csc190
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package Network;

import EvilCraft.Sprite;
import EvilCraft.StaticObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author csc190
 */
public class ArrayJsonConverter<T> {
    static Gson gson = new GsonBuilder().serializeNulls().registerTypeAdapter(Sprite.class, new InterfaceAdapter()).setLenient().create();
    static Type listOfSprite = new TypeToken<List<Sprite>>(){}.getType();
    static Type listOfStatic = new TypeToken<List<StaticObject>>(){}.getType();
    
    public enum ArrayType{SPRITE, STATIC};
    
    public String convertToJson(ArrayList<T> array, ArrayType type){
        if(type.equals(ArrayType.SPRITE))
            return gson.toJson(array, listOfSprite);
        else{
            return gson.toJson(array, listOfStatic);
        }
    }
    
    public ArrayList<T> convertToString(String json, ArrayType type) throws Exception{
        if(type.equals(ArrayType.SPRITE)){
            //System.out.println(json);
            return gson.fromJson(json, listOfSprite);
        }
        else
            return gson.fromJson(json, listOfStatic);
    }
}
