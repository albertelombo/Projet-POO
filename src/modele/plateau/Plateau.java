package modele.plateau;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Set;

import modele.partie.Carte;
import modele.partie.Partie;
import modele.partie.VisiteurPlateau;

/**
 * This abstract class will be used to implement the boards of the game
 * 
 * The game will offer two boards :  a rectangular and a square board. The boundarises of the boards are designed to be imaginary 
 * 
 * @author Albert
 *
 */

@SuppressWarnings("deprecation")
public abstract class Plateau extends Observable {

	
	/**
	 * d1Pos is and indicator of the right corner of the board.
	 */
	protected int d1Pos = 0;
	/**
	 * d2Pos is and indicator of the top corner of the board.
	 */
	protected int d2Pos = 0;
	/**
	 * d1Pos is and indicator of the left corner of the board.
	 */
	protected int d1Neg = 0;
	/**
	 * d1Pos is and indicator of the bottom corner of the board.
	 */
	protected int d2Neg = 0;
	/**
	 * centreOccupe tells if the center of the board has been used, especially for the first play.
	 */
	protected boolean centreOccupe = false;
	/**
	 * cases represent the different spaces reserved for the cards.
	 */
	protected HashMap<Integer[], Carte> cases;

	/**
	 * Is used by the board to receive a card.
	 * 
	 * @param carte is the card the player wants to play.
	 * @param position is the place the player wants to put the card.
	 * @return true if the placement of the card is correct.
	 */
	public abstract boolean accepterCarte(Carte carte, int[] position);

	/**
	 * Is used by the board to move a card 
	 * 
	 * @param positionInitiale is the position the card actually is.
	 * @param positionFinale is the place the player wants to put the card.
	 * @return true if the card movement is correct
	 */
	public abstract boolean accepterDeplacementCarte(int[] positionInitiale, int[] positionFinale);

	/**
	 *  Authorizes a visitor to go through the board.
	 *  
	 * @param visiteur is the visitor of the board
	 */
	public void accepterVisite(VisiteurPlateau visiteur) {
		visiteur.visiter(this);
	}

	/**
	 * Displays the board on the console
	 */
	public void afficherPlateau() {
		Integer[] pos = new Integer[2];
		System.out.println(" \n  /***********************************/ \n");
		System.out.println(" On affiche Le plateau");
		for (int j = d2Pos; j >= d2Neg; j--) {
			System.out.println();
			System.out.print("                                   ");
			for (int i = d1Neg; i <= d1Pos; i++) {
				pos[0] = i;
				pos[1] = j;
				Integer[] posi;
				posi = referencePosi(pos);
				if (cases.containsKey(posi)) {
					System.out.print(cases.get(posi) + "      ");
				} else {
					System.out.print("...........");
				}
			}
		}
		System.out.println("\n \n");
	}

	public HashMap<Integer[], Carte> getCases() {
		return cases;
	}

	public int getD1Neg() {
		return d1Neg;
	}

	public int getD1Pos() {
		return d1Pos;
	}

	public int getD2Neg() {
		return d2Neg;
	}

	public int getD2Pos() {
		return d2Pos;
	}

	public boolean isCentreOccupe() {
		return centreOccupe;
	}

	/**
	 * Is used to find a maximum index on the board if we have to remove a card.
	 * 
	 * @param position is the position of card that may be removed.
	 * @param dim is the direction for whom we are looking a maximum. 
	 * @return the maximum index found.
	 */
	public int[] recupererMaxSansCarte(int[] position, int dim) {
		int[] resultat = new int[2];
		int nbCartesSurMax = 0;

		Set<Integer[]> cles;
		cles = cases.keySet();
		Iterator<Integer[]> iterCles = cles.iterator();
		Integer[] pos;
		pos = iterCles.next();
		int max;
		if (dim == 0) {
			max = d1Neg;
		} else {
			max = d2Neg;
		}
		while (iterCles.hasNext()) {
			pos = iterCles.next();
			if (pos[dim] > max && (pos[0] != position[0] || pos[1] != position[1])) {
				max = pos[dim];
				nbCartesSurMax = 1;
			} else if (pos[dim] == max && (pos[0] != position[0] || pos[1] != position[1])) {
				nbCartesSurMax++;
			}

		}
		resultat[0] = max;
		resultat[1] = nbCartesSurMax;
		return resultat;
	}
	
	/**
	 * Is used to find a minimum index on the board if we have to remove a card.
	 * 
	 * @param position is the position of card that may be removed.
	 * @param dim is the direction for whom we are looking a minimum. 
	 * @return the minimum index found.
	 */
	public int[] recupererMinSansCarte(int[] position, int dim) {
		int[] resultat = new int[2];
		int nbCartesSurMin = 0;

		Set<Integer[]> cles;
		cles = cases.keySet();
		Iterator<Integer[]> iterCles = cles.iterator();
		Integer[] pos;
		pos = iterCles.next();
		int min = position[dim];
		if (dim == 0) {
			min = d1Pos;
		} else {
			min = d2Pos;
		}
		while (iterCles.hasNext()) {
			pos = iterCles.next();
			if (pos[dim] < min && (pos[0] != position[0] || pos[1] != position[1])) {
				min = pos[dim];
				nbCartesSurMin = 1;
			} else if (pos[dim] == min && (pos[0] != position[0] || pos[1] != position[1])) {
				nbCartesSurMin++;
			}

		}
		resultat[0] = min;
		resultat[1] = nbCartesSurMin;

		return resultat;
	}

	/**
	 * Gets the reference of the element in our board that is at a specified position
	 * 
	 * @param pos is the position of the element we want the reference on.
	 * @return the reference of the element or null.
	 */
	public Integer[] referencePosi(Integer[] pos) {
		Integer[] retour = null;
		Set<Integer[]> cles = cases.keySet();
		for (Integer[] posi : cles) {
			if (posi[0] == pos[0] && posi[1] == pos[1]) {
				return posi;
			}
		}

		return retour;
	}

	public void setCases(HashMap<Integer[], Carte> cases) {
		this.cases = cases;
	}

	public void setCentreOccupe(boolean centreOccupe) {
		this.centreOccupe = centreOccupe;
	}

	public void setD1Neg(int val) {
		d1Neg = val;
	}

	// methodes
	public void setD1Pos(int val) {
		d1Pos = val;
	}

	public void setD2Neg(int val) {
		d2Neg = val;
	}

	public void setD2Pos(int val) {
		d2Pos = val;
	}

	/**
	 * Ensures that the card we want to play is next to another card.
	 * 
	 * @param position is the position of the card we want to play.
	 * @return true if the card is next to another card, false if it is not the case
	 */
	public boolean verifierAdjacence(int[] position) {
		// on va parcourir l'ensemble de cases jusqu'à trouver une case adjacente. Le
		// cas échéant la règle est bafouée
		Set<Integer[]> cles;
		cles = cases.keySet();
		boolean adjacenceTrouvee = false;
		// les positions des cartes déjà déposées

		for (Integer[] pos : cles) {
			if ((Math.abs(pos[0] - position[0]) == 1 && pos[1] == position[1])
					|| (Math.abs(pos[1] - position[1]) == 1 && pos[0] == position[0])) {
				// les cartes sont adjacentes
				adjacenceTrouvee = true;
				break;
			}
		}

		if (adjacenceTrouvee) {
			return true;
		} else {
			System.out.println("La carte placée n'est adjacente à aucune carte");
			return false;
		}
	}
	/**
	 * Abstract method that will be used to ensure that a play is valid.
	 * @param position where the player wants to play.
	 * @return true if the play is valid.
	 */
	public abstract boolean verifierConformitePlacement(int[] position);

	// placement de cartes
	/**
	 * Makes sure that the card placement is not out of the board's boundaries.
	 * 
	 * @param position is the position where the player wants to put the card
	 * @return true if the position is in the boundaries.
	 */
	public boolean verifierLimites(int[] position) {
		boolean d1Ok = false;
		boolean d2Ok = false;
		int d1PosBis = d1Pos, d1NegBis = d1Neg;
		int d2PosBis = d2Pos, d2NegBis = d2Neg;
		int offsetV = Partie.offsetGraphiqueV;
		int offsetH = Partie.offsetGraphiqueH;
		if (position[0] <= d1Pos && position[0] >= d1Neg && position[1] >= d2Neg && position[1] <= d2Pos) {
			// Si on est dans les limites précedentes
			d1Ok = true;
			d2Ok = true;
		} else {
			// on véfifie les limites sur d1
			if ((d2Pos - d2Neg) < 3) {

				// la deuxième dimension n'a pas excédé 3
				if (position[0] >= d1Neg && position[0] <= d1Pos) {
					d1Ok = true;
				}
				if (position[0] > d1Pos && (position[0] - d1Neg) < 5) {
					// on s'écarte vers le positif mais on reste dans la limite
					d1Ok = true;
					d1Pos = position[0];
				}
				if (position[0] < d1Neg && (d1Pos - position[0]) < 5) {

					// on s'écarte vers le negatif mais on reste dans les limites
					d1Ok = true;
					d1Neg = position[0];

					// on mets à jour l'offset horizontal
					offsetH = offsetH - 1;
				}
			} else {
				// la deuxième dimension a excédé 3
				if (position[0] >= d1Neg && position[0] <= d1Pos) {
					d1Ok = true;
				}
				if (position[0] > d1Pos && (position[0] - d1Neg) < 3) {
					// on s'écarte vers le positif mais on reste dans la limite
					d1Ok = true;
					d1Pos = position[0];
				}
				if (position[0] < d1Neg && (d1Pos - position[0]) < 3) {
					// on s'écarte vers le négatif mais on reste dans les limites
					d1Ok = true;
					d1Neg = position[0];

					// on mets à jour l'offset horizontal
					offsetH = offsetH - 1;
				}
			}

			// On vérifie les limites sur d2

			if ((d1Pos - d1Neg) < 3) {
				// la premiere dimension n'a pas excédé 3
				if (position[1] >= d2Neg && position[1] <= d2Pos) {
					d2Ok = true;
				}
				if (position[1] > d2Pos && (position[1] - d2Neg) < 5) {
					// on s'écarte vers la positif mais on reste dans la limite
					d2Ok = true;
					d2Pos = position[1];
				}
				if (position[1] < d2Neg && (d2Pos - position[1]) < 5) {
					// on s'écarte vers le négatif mais on reste dans les limites
					d2Ok = true;
					d2Neg = position[1];

					// on mets à jour l'offset vertical
					offsetV = offsetV - 1;
				}
			} else {
				// la première dimension a excédé 3

				if (position[1] >= d2Neg && position[1] <= d2Pos) {
					d2Ok = true;
				}
				if (position[1] > d2Pos && (position[1] - d2Neg) < 3) {
					// on s'écarte vers la drote mais on reste dans la limite
					d2Ok = true;
					d2Pos = position[1];
				}
				if (position[1] < d2Neg && (d2Pos - position[1]) < 3) {
					// on s'écarte vers la gauche mais on reste dans les limites
					d2Ok = true;
					d2Neg = position[1];

					// on mets à jour l'offset vertical
					offsetV = offsetV - 1;
				}
			}
		}

		if (d1Ok && d2Ok) {
			Partie.offsetGraphiqueH = offsetH;
			Partie.offsetGraphiqueV = offsetV;
			return true;
		} else {
			// on revient aux limites précédentes
			d1Pos = d1PosBis;
			d2Pos = d2PosBis;
			d1Neg = d1NegBis;
			d2Neg = d2NegBis;
			return false;
		}
	}
	/**
	 * Abstract method that will be used to ensure that a card movement is valid.
	 * 
	 * @param positionInitiale the initial position of the card.
	 * @param positionFinale the position where the player wants to put the card
	 * @return true if the card movement is valid.
	 */
	public boolean verifierLimitesDeplacement(int[] positionInitiale, int[] positionFinale) {
		int offsetV = Partie.offsetGraphiqueV;
		int offsetH = Partie.offsetGraphiqueH;
		if (positionInitiale[0] > d1Neg && positionInitiale[0] < d1Pos && positionInitiale[1] > d2Neg
				&& positionInitiale[1] < d2Pos) {
			if (verifierLimites(positionFinale)) {
				return true;
			}
		} else {
			boolean d1Ok = false;
			boolean d2Ok = false;

			// pour la direction 1
			if (positionInitiale[0] == d1Neg) {
				// si la carte est sur la position minimale actuelle
				if (positionFinale[0] == positionInitiale[0]) {
					// pas de mouvement sur d1
					d1Ok = true;
				} else if (positionFinale[0] < positionInitiale[0]) {
					// mouvement sur d1 vers l'arriere
					if (verifierLimites(positionFinale))
						d1Ok = true;

				} else {
					// mouvement sur d1 vers l'avant
					// on récupère le minimum sur d1 sans la carte à bouger
					int[] minProv = recupererMinSansCarte(positionInitiale, 0);

					// on passe temporairement le minimum au minimum sans la carte à déplacer si la
					// carte est seule au minimum sur d1

					if (minProv[1] > 0) {
						if (minProv[0] != d1Neg)
							Partie.offsetGraphiqueH = Partie.offsetGraphiqueH + Math.abs(d1Neg - minProv[0]);
						d1Neg = minProv[0];
					}
					// on vérifie les limites dans ces conditions provisoires
					if (verifierLimites(positionFinale)) {
						// il est correct: on conserve ces données
						d1Ok = true;
					} else {
						// il est hors limites: on ne déplacera pas la carte
						d1Neg = positionInitiale[0];
						Partie.offsetGraphiqueH = Partie.offsetGraphiqueH - Math.abs(d1Neg - minProv[0]);
					}
				}
			} else {
				// On est sur le maximum
				// si la carte est sur la position minimale actuelle
				if (positionFinale[0] == positionInitiale[0]) {
					// pas de mouvement sur d1
					d1Ok = true;
				} else if (positionFinale[0] > positionInitiale[0]) {
					// mouvement sur d1 vers l'avant
					if (verifierLimites(positionFinale))
						d1Ok = true;

				} else {
					// mouvement sur d1 vers l'arriere
					// on récupère le maximum sur d1 sans la carte à bouger
					int[] maxProv = recupererMaxSansCarte(positionInitiale, 0);

					// on passe temporairement le maximum au maximum sans la carte à déplacer si la
					// carte est seule au maxmum sur d1

					if (maxProv[1] > 0)
						d1Pos = maxProv[0];

					// on vérifie les limites dans ces conditions provisoires
					if (verifierLimites(positionFinale)) {
						// il est correct: on conserve ces données
						d1Ok = true;
					} else {
						// il est hors limites: on ne déplacera pas la carte
						d1Pos = positionInitiale[0];

					}
				}
			}
			// pour la direction 2
			if (positionInitiale[1] == d2Neg) {
				// si la carte est sur la position minimale actuelle
				if (positionFinale[1] == positionInitiale[1]) {
					// pas de mouvement sur d2
					d2Ok = true;
				} else if (positionFinale[1] < positionInitiale[1]) {
					// mouvement sur d2 vers l'arriere
					if (verifierLimites(positionFinale))
						d2Ok = true;

				} else {
					// mouvement sur d2 vers l'avant
					// on récupère le minimum sur d2 sans la carte à bouger
					int[] minProv = recupererMinSansCarte(positionInitiale, 1);

					// on passe temporairement le minimum au minimum sans la carte à déplacer si la
					// carte est seule au minimum sur d1

					if (minProv[1] > 0) {
						if (minProv[0] != d2Neg)
							Partie.offsetGraphiqueV = Partie.offsetGraphiqueV + Math.abs(d2Neg - minProv[0]);
						d2Neg = minProv[0];
					}
					// on vérifie les limites dans ces conditions provisoires
					if (verifierLimites(positionFinale)) {
						// il est correct: on conserve ces données
						d2Ok = true;
					} else {
						// il est hors limites: on ne déplacera pas la carte
						d2Neg = positionInitiale[0];
						Partie.offsetGraphiqueV = Partie.offsetGraphiqueV + Math.abs(d2Neg - minProv[0]);
					}
				}
			} else {
				// On est sur le maximum
				// si la carte est sur la position minimale actuelle
				if (positionFinale[1] == positionInitiale[1]) {
					// pas de mouvement sur d2
					d2Ok = true;
				} else if (positionFinale[1] > positionInitiale[1]) {
					// mouvement sur d2 vers l'avant
					if (verifierLimites(positionFinale))
						d2Ok = true;

				} else {
					// mouvement sur d2 vers l'arriere
					// on récupère le maximum sur d2 sans la carte à bouger
					int[] maxProv = recupererMaxSansCarte(positionInitiale, 1);

					// on passe temporairement le maximum au maximum sans la carte à déplacer si la
					// carte est seule au maxmum sur d1

					if (maxProv[1] > 0)
						d2Pos = maxProv[0];
					// on vérifie les limites dans ces conditions provisoires
					if (verifierLimites(positionFinale)) {
						// il est correct: on conserve ces données
						d2Ok = true;
					} else {
						// il est hors limites: on ne déplacera pas la carte
						d2Pos = positionInitiale[0];
					}
				}
			}
			if (d1Ok && d2Ok) {
				return true;
			} else {
				Partie.offsetGraphiqueH = offsetH;
				Partie.offsetGraphiqueV = offsetV;
			}
		}
		System.out.println("Le déplacement enfreint les limites de l'aire de jeu");
		return false;
	}

	/**
	 * Ensures that a player does not put a card on another one.
	 * 
	 * @param position the position where the player wants to play.
	 * @return true if the player doesn't put a card on another one.
	 */
	public boolean verifierNonSuperposition(int[] position) {
		// on va parcourir le plateau et s'arrêter si la position coincide

		boolean superposition = false;
		Set<Integer[]> cles;
		cles = cases.keySet();
		Iterator<Integer[]> iterCles;
		iterCles = cles.iterator();
		Integer[] pos; // les positions des cartes déjà déposées
		while (iterCles.hasNext() && superposition == false) {
			pos = iterCles.next();
			if (pos[0] == position[0] && pos[1] == position[1]) {
				superposition = true;
			}
		}
		if (superposition) {
			System.out.println("Vous avez essayé de poser une carte sur un emplacement occupé ");
			return false;
		} else {
			return true;
		}
	}
}
