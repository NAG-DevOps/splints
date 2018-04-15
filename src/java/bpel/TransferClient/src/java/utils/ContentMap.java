/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

/**
 *
@author soen487-w18-team03
 */
public class ContentMap implements Serializable{
    private String map;
  
    public ContentMap()
    {
    }
    public ContentMap(String map) {
        this.map=map;
    }
    
    public String getMap()
    {
        return map;
    }
    
    public void setMap(String map)
    {
        this.map=map;
    }
}
