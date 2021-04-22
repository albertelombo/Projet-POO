package modele.plateau;

import modele.partie.Carte;

/**
 * Is used as an argument transmitted to the graphical view so that it can
 * display the cards on the board
 * 
 * @author Albert
 *
 */
public class PosiCarte {
	/**
	 * The reference of the card.
	 */
	private Carte carte;

	/**
	 * The position of the card.
	 */
	private int[] pos;

	/**
	 * Instantiates the class.
	 * 
	 * @param carte a reference on the card sent.
	 * @param pos   the position of the card sent.
	 */
	public PosiCarte(Carte carte, int[] pos) {
		super();
		this.carte = carte;
		this.pos = pos;
	}

	public Carte getCarte() {
		return carte;
	}

	public int[] getPos() {
		return pos;
	}

	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	public void setPos(int[] pos) {
		this.pos = pos;
	}
}
