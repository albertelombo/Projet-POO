package modele.partie;

import java.util.*;

import modele.joueurs.*;
import modele.plateau.*;

/**
 * The Compteur is an agent whose role will be to find the scores of the
 * different players at the end of a round.
 * 
 * @author Albert
 *
 */
public class Compteur implements VisiteurPlateau {

	/**
	 * Instantiates the Compteur
	 */
	public Compteur() {

	}

	/**
	 * Defines the method that will be used to find the scores
	 * 
	 * @see VisiteurPlateau.visiter
	 */
	@Override
	public void visiter(Plateau plateau) {
		Partie partie = Partie.getInstance();
		ArrayList<Joueur> joueurs = partie.getJoueurs();
		int score = 0;
		for (Joueur joueur : joueurs) {
			score = compterScore(joueur.getVictoryCard(), plateau);
			joueur.setScoreRound(score);
		}
	}

	/**
	 * Is used to get a line of the board.
	 * 
	 * @param ordonnee is the vertical position of the line in the board.
	 * @param plateau  is a reference to the board.
	 * @return the line as an array.
	 */
	public ArrayList<Carte> recupererLigne(int ordonnee, Plateau plateau) {
		// permet de récuperer une ligne du plateau

		int min = plateau.getD1Neg();
		int max = plateau.getD1Pos();

		ArrayList<Carte> ligne = new ArrayList<Carte>();
		Integer[] pos = new Integer[2];
		Integer[] posi;
		for (int i = min; i <= max; i++) {
			pos[0] = i;
			pos[1] = ordonnee;

			posi = plateau.referencePosi(pos);

			ligne.add(plateau.getCases().get(posi));

		}

		return ligne;
	}

	/**
	 * Is used to get a column of the board.
	 * 
	 * @param abscisse is the horizontal position of the line in the board.
	 * @param plateau  is a reference to the board.
	 * @return the column as an array.
	 */
	public ArrayList<Carte> recupererColonne(int abscisse, Plateau plateau) {
		// permet de récuperer une colonne du plateau

		int min = plateau.getD2Neg();
		int max = plateau.getD2Pos();

		ArrayList<Carte> colonne = new ArrayList<Carte>();
		Integer[] pos = new Integer[2];
		Integer[] posi;
		for (int i = min; i <= max; i++) {
			pos[0] = abscisse;
			pos[1] = i;

			posi = plateau.referencePosi(pos);

			colonne.add(plateau.getCases().get(posi));

		}

		return colonne;
	}

	/**
	 * Evaluates the score given to a specific victory card according to the
	 * geometry of the card on a line or a column.
	 * 
	 * @param liste represents a line or a column.
	 * @param forme is the geometry on the victory card.
	 * @return the number of points granted to the player for the line according to
	 *         the geometry of his victory card.
	 */
	public int pointsForme(ArrayList<Carte> liste, Forme forme) {
		// Donne les points pour une liste de cartes en rapport à une certaine forme
		int nbPts = 0;
		int nbAlignees = 1;
		boolean enchainement = false;
		Carte actuel;
		Carte suivant;
		for (int i = 0; i < liste.size(); i++) {
			actuel = liste.get(i);
			if (i == (liste.size() - 1)) {
				suivant = null;
			} else {
				suivant = liste.get(i + 1);
			}
			if (actuel != null) {
				if (actuel.getForme() == forme) {
					if (suivant != null) {
						if (suivant.getForme() == forme && enchainement == false) {
							enchainement = true;
							nbAlignees++;
						} else if (suivant.getForme() == forme && enchainement) {
							nbAlignees++;
						} else if (suivant.getForme() != forme && enchainement) {
							nbPts += (nbAlignees - 1);
							enchainement = false;
							nbAlignees = 1;
						}
					} else {

						if (enchainement) {
							nbPts += (nbAlignees - 1);
							nbAlignees = 1;
							enchainement = false;
						}
					}
				}
			}
		}
		return nbPts;
	}

	/**
	 * Evaluates the score given to a specific victory card according to the fact
	 * that it is filled on a line or a column.
	 * 
	 * @param liste       represents a line or a column.
	 * @param remplissage tells whether the victory card is filled or not.
	 * @return the number of points granted to the player for the line according to
	 *         the parameter of his victory card.
	 */
	public int pointsRemplissage(ArrayList<Carte> liste, Remplissage remplissage) {
		// Donne les points pour une liste de cartes en rapport à une certaine forme
		int nbPts = 0;
		int nbAlignees = 1;
		boolean enchainement = false;
		Carte actuel;
		Carte suivant;
		for (int i = 0; i < liste.size(); i++) {
			actuel = liste.get(i);
			if (i == (liste.size() - 1)) {
				suivant = null;
			} else {
				suivant = liste.get(i + 1);
			}
			if (actuel != null) {
				if (actuel.getRemplissage() == remplissage) {
					if (suivant != null) {
						if (suivant.getRemplissage() == remplissage && enchainement == false) {
							enchainement = true;
							nbAlignees++;
						} else if (suivant.getRemplissage() == remplissage && enchainement) {
							nbAlignees++;
						} else if (suivant.getRemplissage() != remplissage && enchainement) {
							if (nbAlignees >= 3)
								nbPts += (nbAlignees);
							enchainement = false;
							nbAlignees = 1;
						}
					} else {
						if (enchainement) {
							if (nbAlignees >= 3)
								nbPts += (nbAlignees);
							enchainement = false;
							nbAlignees = 1;
						}
					}
				}
			}
		}
		return nbPts;
	}

	/**
	 * Evaluates the score given to a specific victory card according to the color
	 * of the card on a line or a column.
	 * 
	 * @param liste represents a line or a column.
	 * @param color is the color of the victory card.
	 * @return the number of points granted to the player for the line according to
	 *         the color of his victory card.
	 */
	public int pointsCouleur(ArrayList<Carte> liste, Couleur couleur) {
		// Donne les points pour une liste de cartes en rapport à une certaine forme
		int nbPts = 0;
		int nbAlignees = 1;
		boolean enchainement = false;
		Carte actuel;
		Carte suivant;
		for (int i = 0; i < liste.size(); i++) {
			actuel = liste.get(i);
			if (i == (liste.size() - 1)) {
				suivant = null;
			} else {
				suivant = liste.get(i + 1);
			}
			if (actuel != null) {
				if (actuel.getCouleur() == couleur) {
					if (suivant != null) {
						if (suivant.getCouleur() == couleur && enchainement == false) {
							enchainement = true;
							nbAlignees++;
						} else if (suivant.getCouleur() == couleur && enchainement) {
							nbAlignees++;
						} else if (suivant.getCouleur() != couleur && enchainement) {
							if (nbAlignees >= 3)
								nbPts += (nbAlignees + 1);
							nbAlignees = 1;
							enchainement = false;
						}
					} else {
						if (enchainement) {
							if (nbAlignees >= 3)
								nbPts += (nbAlignees + 1);
							nbAlignees = 1;
							enchainement = false;
						}
					}
				}
			}
		}
		return nbPts;
	}

	/**
	 * Evaluates the total number of points of a player once the board is filed.
	 * 
	 * @param victoryCard the victory card of the player
	 * @param plateau     a reference on the board
	 * @return
	 */
	public int compterScore(Carte victoryCard, Plateau plateau) {
		int score = 0;
		int xmin = plateau.getD1Neg();
		int xmax = plateau.getD1Pos();
		int ymin = plateau.getD2Neg();
		int ymax = plateau.getD2Pos();

		// on additione les scores sur les lignes

		ArrayList<Carte> liste;
		for (int i = ymin; i <= ymax; i++) {
			liste = recupererLigne(i, plateau);
			score += pointsForme(liste, victoryCard.getForme());
			score += pointsRemplissage(liste, victoryCard.getRemplissage());
			score += pointsCouleur(liste, victoryCard.getCouleur());
		}

		// On additionne les scores sur les colonnes

		for (int i = xmin; i <= xmax; i++) {
			liste = recupererColonne(i, plateau);
			score += pointsForme(liste, victoryCard.getForme());
			score += pointsRemplissage(liste, victoryCard.getRemplissage());
			score += pointsCouleur(liste, victoryCard.getCouleur());
		}
		return score;
	}
}
