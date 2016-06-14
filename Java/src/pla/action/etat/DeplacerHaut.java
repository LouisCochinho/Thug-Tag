package pla.action.etat;

import pla.Personnage;
import pla.action.etat.Action_etat;

/**
 *
 * @author antoi
 */
public class DeplacerHaut extends Action_etat {

	@Override
	public void executer(Personnage p, int delta,int modulo_tore_x, int modulo_tore_y) {
		p.setDirection(0);		
		float depl = (p.getY()-0.1f*delta)%modulo_tore_y;
		p.setY(depl);
		if(p.getY() < 0){
			p.setY(modulo_tore_y - 32);
		}   			
	}

	@Override
	protected void executer(Personnage p, int delta) {
		// TODO Auto-generated method stub
		
	}

}
