package com.g1ee0k.brainstorm;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by geek on 5/9/16.
 */
public class FirebaseToLocal implements Runnable {
    public HashMap formulaMap;

    FirebaseToLocal(){

    }

    FirebaseToLocal(HashMap formulaMap){
        this.formulaMap = formulaMap;
    }

    @Override
    public void run() {
        helper.FormulasMap.clear();
        for (int i = 0; i < formulaMap.size(); i++) {
            String key = (String) formulaMap.keySet().toArray()[i];
            helper.FormulasMap.put(key, (ArrayList<String>) formulaMap.get(key));
        }
    }
}
