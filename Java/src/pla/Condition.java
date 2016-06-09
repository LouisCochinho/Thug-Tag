/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pla;

import java.util.ArrayList;

/**
 *
 * @author antoi
 */
public class Condition {
    private final ArrayList<ConditionSimple> conditions;
    
    public Condition() {
        conditions = new ArrayList<ConditionSimple>();
    }
    
    public void addCondition(ConditionSimple c) {
        conditions.add(c);
    }
    
    public int nombreConditions() {
        return conditions.size();
    }
    
    public ConditionSimple getCondition(int i) {
        return conditions.get(i);
    }
    
    @Override
    public String toString() {
        String s = "";
        if(conditions.size() > 0) {
            s = conditions.get(0).toString();
            for(int i=1; i<conditions.size(); i++) {
                s += " && " + conditions.get(i);
            }
        }
        return s;
    }
}