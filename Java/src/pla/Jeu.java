package pla;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import pla.ihm.Map;
import pla.util.Musique;

public class Jeu extends BasicGameState {
	private Map map = new Map(); // carte du jeu
	private List<Personnage> personnages = new ArrayList<Personnage>(); // Liste
																		// des
	public static final int ID = 1;										// personnages
	
	private GameContainer gc; // conteneur
	private int camX, camY;
	private final static int DEPLACEMENT = 15;
	Musique musique;
	// private static final int PAUSE = 25; // temps de latence

	// private float zoom = 0.1f;
	

	/*
	 * private float z1 = 0.01f; private float z2 = 0.01f;
	 */

	public Jeu() {

		personnages = new ArrayList<Personnage>();
	}

	public void ajouterPersonnage(Personnage p) {
		if (p != null) {
			this.personnages.add(p);
		} else {
			System.out.println("Le personnage que vous voulez ajouter dans la liste des personnages est vide");
		}
	}

	public void supprimerPersonnage(Personnage p) {
		if (p != null && this.personnages.contains(p)) {
			this.personnages.remove(p);
		} else {
			System.out.println("Le personnage que vous voulez supprimer n'est pas dans la liste ou est nul");
		}
	}

	// Initialise le contenu du jeu, charge les graphismes, la musique, etc..
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {

		this.gc = gc;
		this.map.init();
		ajouterPersonnage(new Personnage("res/thugBleu.png", 420.f, 420.f, 2, 0, 64, 64, new Automate(), Color.blue));
		ajouterPersonnage(new Personnage("res/thugRouge.png", 320.f, 320.f, 1, 0, 64, 64, new Automate(), Color.green));

		// Marche pas => Revoir sprite policier
		ajouterPersonnage(new Personnage("res/Bernard.png", 200.f, 200.f, 3, 0, 64, 64, new Automate(), Color.green));

		for (Personnage p : personnages) {
			p.init();
			//this.map.placerAutoRandom(personnages);
			this.map.placerAutomate(p.getAutomate(), p.getCouleur(), gc.getGraphics());

		}
		// this.map.placerPersonnageRandom(personnages);
		//sound = new Music("res/thug.ogg");
		//musique = new Musique();
		
	}

	// Affiche le contenu du jeu
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		g.translate(camX, camY);
		this.map.afficher();
		for (Personnage p : personnages) {
			p.afficher(g);
		}
	}

	// Met � jour les �l�ments de la sc�ne en fonction du delta temps
	// survenu.
	// C'est ici que la logique du jeu est enferm�e.
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		for (Personnage p : personnages) {
			deplacerPersonnage(p, delta);
		}

		

		if (gc.getInput().isKeyPressed(Input.KEY_M) && gc.isMusicOn()) {
			musique.resumeJeu();
		}
		if (gc.getInput().isKeyPressed(Input.KEY_S)) {
			musique.stopJeu();
		}
		if (gc.getInput().isKeyPressed(Input.KEY_P)) {
			musique.pauseJeu();
		}	
		if (gc.getInput().isKeyDown(Input.KEY_UP)) {
				camY+=DEPLACEMENT;
		}
		if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
			camY-=DEPLACEMENT;
		} 
		if(gc.getInput().isKeyDown(Input.KEY_RIGHT)){
			camX-= DEPLACEMENT;
		}
		if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
			camX+=DEPLACEMENT;
		} 
		
	}

	// Arreter correctement le jeu en appuyant sur ECHAP
	@Override
	public void keyReleased(int key, char c) {

		if (Input.KEY_ESCAPE == key) {
			gc.exit();
		}
	}

	public void deplacerPersonnage(Personnage p, int delta) {

		ArrayList<Integer> indexPossibles = new ArrayList<Integer>();
		int etatCourantId = p.getAutomate().getEtatCourant().getId();
		boolean conditionVerifiee;
		Random r = new Random();
		int indexChoisi = 0;

		for (int i = 0; i < p.getAutomate().getNbLignes(); i++) {
			Condition c = p.getAutomate().getTabCondition()[i][etatCourantId];
			conditionVerifiee = true;


			if (!c.estVerifiee(p, map)) {
				conditionVerifiee = false;

			}

			if (conditionVerifiee) {
				indexPossibles.add(i);
			}
		}

		if (!indexPossibles.isEmpty()) {
			// Prendre un index au hasard dans la liste
			indexChoisi = indexPossibles.get(r.nextInt(indexPossibles.size()));	
			p.getAutomate().setEtatCourant(p.getAutomate().getTabEtatSuivant()[indexChoisi][etatCourantId]);
		}
		else{
			p.getAutomate().setEtatCourant(p.getAutomate().getEtatInitial());
		}	
		p.deplacer(delta);
	}
	
	@Override
	public int getID() {
		return ID;
	}
		
	
}
