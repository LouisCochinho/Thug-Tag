/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pla.decor;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author antoi
 */
public class SolEnnemi extends Decor {
    
    public SolEnnemi() {
        super(0);
        try {
            this.image = new Image("res/sol_bleu.png");
        } catch (SlickException e) {
            System.out.println("L'image n'a pas pu etre chargee");
        }
    }
    
}
