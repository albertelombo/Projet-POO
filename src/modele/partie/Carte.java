package modele.partie;

/**
 * Represents a card in the game
 * 
 * @author Albert
 *
 */
public class Carte {
	// attributs
	/**
	 * Is the geometry of the card. It can be a square, a circle or a triangle.
	 */
	private Forme forme;

	/**
	 * Is the color of the card that can be red green or blue.
	 */
	private Couleur couleur;

	/**
	 * Tells whether the shape is filled or empty
	 */
	private Remplissage remplissage;

	/**
	 * Instantiates the card.
	 * 
	 * @param forme       gives the geometry of the card.
	 * @param couleur     gives the color of the car.
	 * @param remplissage tells whether the shape is filled or empty.
	 */
	public Carte(int forme, int couleur, int remplissage) {
		Forme[] formes = Forme.values();
		Couleur[] couleurs = Couleur.values();
		Remplissage[] remplissages = Remplissage.values();
		this.forme = formes[forme];
		this.couleur = couleurs[couleur];
		this.remplissage = remplissages[remplissage];
	}

	public Couleur getCouleur() {

		return couleur;
	}

	public Forme getForme() {
		return forme;
	}

	public Remplissage getRemplissage() {
		return remplissage;
	}

	public String toString() {
		StringBuffer chaine = new StringBuffer();
		// Forme
		if (forme == Forme.CARRE) {
			chaine.append("Ca");
		} else if (forme == Forme.CERCLE) {
			chaine.append("Ce");
		} else {
			chaine.append("Tr");
		}

		// Couleur

		if (couleur == Couleur.BLEU) {
			chaine.append("-B");
		} else if (couleur == Couleur.ROUGE) {
			chaine.append("-R");
		} else {
			chaine.append("-V");
		}

		// Remplissage
		if (remplissage == Remplissage.PLEIN) {
			chaine.append("-P");
		} else {
			chaine.append("-V");
		}
		return chaine.toString();

	}
}
