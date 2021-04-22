package modele.plateau;

import java.util.HashMap;

//import modele.joueurs.*;
import modele.partie.*;

/**
 * Represents a board that is going to look like a square at the end of a game round.
 * 
 * @author Albert
 * @see Plateau
 *
 */
public class PlateauCarre extends Plateau {
	/**
	 * Instantiates the board
	 */
	public PlateauCarre() {
		cases = new HashMap<Integer[],Carte>();
		System.out.println("Le plateau carre est généré");
	}
	
	/**
	 * Redefines the limit verification function so that it suits to the square geometry.
	 * 
	 * @see Plateau.verifierLimites
	 */
	public boolean verifierLimites(int[] position) {
		boolean d1Ok = false;
		boolean d2Ok = false;
		int d1PosBis = d1Pos, d1NegBis = d1Neg;
		int d2PosBis = d2Pos, d2NegBis = d2Neg;
		int offsetV = Partie.offsetGraphiqueV;
		int offsetH = Partie.offsetGraphiqueH;
		if( position[0] <= d1Pos && position[0]>= d1Neg && position[1]>= d2Neg && position[1] <= d2Pos ) {
			// Si on est dans les limites précedentes
			d1Ok = true;
			d2Ok = true;
		}else {
			// on véfifie les limites sur d1
			
			if(position[0]>= d1Neg && position[0]<= d1Pos) {
				d1Ok =true;
			}
			if (position[0] > d1Pos && (position[0] - d1Neg) < 4 ) {
				// on s'écarte vers le positif mais on reste dans la limite
				d1Ok = true;
				d1Pos = position[0]; 
			}
			if(position[0] < d1Neg && (d1Pos - position[0] ) < 4 ) {
				
				// on s'écarte vers le negatif mais on reste dans les limites
				d1Ok = true;
				d1Neg = position[0];
				// on mets à jour l'offset horizontal
				 offsetH = offsetH -1;
			}
			
			// On vérifie les limites sur d2
			
			if(position[1] >= d2Neg && position[1]<= d2Pos) {
				d2Ok = true;
			}
			
			if (position[1] > d2Pos && (position[1] - d2Neg) < 4 ) {
				// on s'écarte vers la positif mais on reste dans la limite
				d2Ok = true;
				d2Pos = position[1]; 
			}
			if(position[1] < d2Neg && (d2Pos - position[1] ) < 4 ) {
				// on s'écarte vers le négatif mais on reste dans les limites
				d2Ok = true;
				d2Neg = position[1];
				offsetV = offsetV -1;
			}
			
		}
		
		if(d1Ok && d2Ok) {
			Partie.offsetGraphiqueH = offsetH;
			Partie.offsetGraphiqueV= offsetV;
			return true;
		}else {
			// on revient aux limites précédentes
			d1Pos = d1PosBis;
			d2Pos = d2PosBis;
			d1Neg = d1NegBis;
			d2Neg = d2NegBis;
			return false;
		}
	}
	
	/**
	 * Defines the validation protocol of a card placement.
	 * 
	 * @see Plateau.verifierConformitePlacement
	 */
	@Override
	public boolean verifierConformitePlacement(int[] position) {
		// on remet les valeurs à l'échelle
		
		position[0] = position[0] + d1Neg;
		position[1] = position[1] + d2Neg;
		if(position[0] == 0 && position[1] == 0 && centreOccupe == false) {
			centreOccupe = true;
			return true;
		}
		if( verifierNonSuperposition(position)) {
			if(verifierAdjacence(position)) {
				if(verifierLimites(position)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Redefines the limit verification function for a card movement so that it fits with the square geometry.
	 * 
	 * @see Plateau.verifierLimitesDeplacement
	 */
	public boolean verifierLimitesDeplacement(int[] positionInitiale, int[] positionFinale) {
		int offsetV = Partie.offsetGraphiqueV;
		int offsetH = Partie.offsetGraphiqueH;
		if(positionInitiale[0]> d1Neg && positionInitiale[0]<d1Pos && positionInitiale[1] > d2Neg && positionInitiale[1]< d2Pos) {
			if(verifierLimites(positionFinale)) {
				return true;
			}
		}else {
			boolean d1Ok = false;
			boolean d2Ok = false;
			
			// pour la direction 1
			if(positionInitiale[0] == d1Neg) {
				//si la carte est sur la position minimale actuelle
				if( positionFinale[0] == positionInitiale[0]) {
					// pas de mouvement sur d1
					d1Ok = true;
				}else if(positionFinale[0] < positionInitiale[0]) {
					// mouvement sur d1 vers l'arriere
					if(verifierLimites(positionFinale)) d1Ok = true;
					
				}else {
					// mouvement sur d1 vers l'avant
					// on récupère le minimum sur d1 sans la carte à bouger
					int[] minProv = recupererMinSansCarte(positionInitiale, 0);
					
					// on passe temporairement le minimum au minimum sans la carte à déplacer si la carte est seule au minimum sur d1
					
					if(minProv[1]>0) {
						if(minProv[0] != d1Neg)Partie.offsetGraphiqueH= Partie.offsetGraphiqueH + Math.abs(d1Neg - minProv[0]);
						d1Neg = minProv[0];
					}
					// on vérifie les limites dans ces conditions provisoires
					if(verifierLimites(positionFinale)) {
						// il est correct: on conserve ces données
						d1Ok = true;
					}else {
						// il est hors limites: on ne déplacera pas la carte
						d1Neg = positionInitiale[0];
						Partie.offsetGraphiqueH= Partie.offsetGraphiqueH - Math.abs(d1Neg - minProv[0]);
					}
				}
			}else {
				// On est sur le maximum
				//si la carte est sur la position minimale actuelle
				if( positionFinale[0] == positionInitiale[0]) {
					// pas de mouvement sur d1
					d1Ok = true;
				}else if(positionFinale[0] > positionInitiale[0]) {
					// mouvement sur d1 vers l'avant
					if(verifierLimites(positionFinale)) d1Ok = true;
					
				}else {
					// mouvement sur d1 vers l'arriere
					// on récupère le maximum sur d1 sans la carte à bouger
					int[] maxProv = recupererMaxSansCarte(positionInitiale, 0);
					
					// on passe temporairement le maximum au maximum sans la carte à déplacer si la carte est seule au maxmum sur d1
					
					if(maxProv[1]>0) d1Pos = maxProv[0];
					
					// on vérifie les limites dans ces conditions provisoires
					if(verifierLimites(positionFinale)) {
						// il est correct: on conserve ces données
						d1Ok = true;
					}else {
						// il est hors limites: on ne déplacera pas la carte
						d1Pos = positionInitiale[0];
						
					}
				}
			}
			// pour la direction 2
			if(positionInitiale[1] == d2Neg) {
				//si la carte est sur la position minimale actuelle
				if( positionFinale[1] == positionInitiale[1]) {
					// pas de mouvement sur d2
					d2Ok = true;
				}else if(positionFinale[1] < positionInitiale[1]) {
					// mouvement sur d2 vers l'arriere
					if(verifierLimites(positionFinale)) d2Ok = true;
					
				}else {
					// mouvement sur d2 vers l'avant
					// on récupère le minimum sur d2 sans la carte à bouger
					int[] minProv = recupererMinSansCarte(positionInitiale, 1);
					
					// on passe temporairement le minimum au minimum sans la carte à déplacer si la carte est seule au minimum sur d1
					
					if(minProv[1]>0) { 
					if(minProv[0] != d2Neg)Partie.offsetGraphiqueV = Partie.offsetGraphiqueV + Math.abs(d2Neg - minProv[0]);
						d2Neg = minProv[0];
					}
					// on vérifie les limites dans ces conditions provisoires
					if(verifierLimites(positionFinale)) {
						// il est correct: on conserve ces données
						d2Ok = true;
					}else {
						// il est hors limites: on ne déplacera pas la carte
						d2Neg = positionInitiale[0];
						Partie.offsetGraphiqueV = Partie.offsetGraphiqueV - Math.abs(d2Neg - minProv[0]);
					}
				}
			}else {
				// On est sur le maximum
				//si la carte est sur la position minimale actuelle
				if( positionFinale[1] == positionInitiale[1]) {
					// pas de mouvement sur d2
					d2Ok = true;
				}else if(positionFinale[1] > positionInitiale[1]) {
					// mouvement sur d2 vers l'avant
					if(verifierLimites(positionFinale)) d2Ok = true;
					
				}else {
					// mouvement sur d2 vers l'arriere
					// on récupère le maximum sur d2 sans la carte à bouger
					int[] maxProv = recupererMaxSansCarte(positionInitiale, 1);
					
					// on passe temporairement le maximum au maximum sans la carte à déplacer si la carte est seule au maxmum sur d1
					
					if(maxProv[1]>0) d2Pos = maxProv[0];
					// on vérifie les limites dans ces conditions provisoires
					if(verifierLimites(positionFinale)) {
						// il est correct: on conserve ces données
						d2Ok = true;
					}else {
						// il est hors limites: on ne déplacera pas la carte
						d2Pos = positionInitiale[0];
					}
				}
			}
			if(d1Ok && d2Ok) {
				return true;
			}else {
				Partie.offsetGraphiqueH = offsetH;
				Partie.offsetGraphiqueV = offsetV;
			}
		}
		System.out.println("Le déplacement enfreint les limites de l'aire de jeu");
		return false;
	}

	/**
	 * Defines the abstract method of same name specified by parent class for accepting a card placement.
	 * 
	 * @see Plateau.accepterCarte
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean accepterCarte(Carte carte, int[] position) {
		if(verifierConformitePlacement(position)) {
			Integer[] pos = new Integer[2];
			pos[0] = position[0];
			pos[1] = position[1];
			cases.put(pos, carte);
			PosiCarte posiCarte = new PosiCarte(carte,position);
			System.out.println("Le positionnement de la carte a reussi");
			this.setChanged();
			this.notifyObservers(posiCarte);
			synchronized(Partie.verrou){
				return true;
			}
		}else {
			System.out.println("Le positionnement de la carte n'a pas pu être effectué");
			return false;
		}
	}
	
	/**
	 * Defines the abstract method of same name specified by parent class for accepting a card movement.
	 * 
	 * @see Plateau.accepterCarte
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean accepterDeplacementCarte(int[] positionInitiale, int[] positionFinale) {
		// on remet les valeurs à l'échelle
		
		positionInitiale[0] = positionInitiale[0] + d1Neg;
		positionInitiale[1] = positionInitiale[1] + d2Neg;
		
		positionFinale[0] = positionFinale[0] + d1Neg;
		positionFinale[1] = positionFinale[1] + d2Neg;
		if(verifierNonSuperposition(positionFinale)) {
			if( verifierLimitesDeplacement(positionInitiale,positionFinale)) {
				// on peut effectuer le déplaacement
				Integer[] posInitiale = new Integer[2];
				Integer[] posI;
				Integer[] posFinale = new Integer[2] ;
				Carte carte;
				posInitiale[0] = positionInitiale[0];
				posInitiale[1] = positionInitiale[1];
				
				posFinale[0] = positionFinale[0];
				posFinale[1] = positionFinale[1];
				posI = referencePosi(posInitiale);
				carte = cases.get(posI);
				
				cases.remove(posI);
				cases.put(posFinale, carte);
				PosiCarte posiCarteI = new PosiCarte(carte,positionInitiale);
				PosiCarte posiCarteF = new PosiCarte(carte,positionFinale);
				PosiCarte[] posiCartes = new PosiCarte[2];
				posiCartes[0] = posiCarteI;
				posiCartes[1] = posiCarteF;
				this.setChanged();
				this.notifyObservers(posiCartes);
				synchronized(Partie.verrou){
					return true;
				}
				
			}
		}
		return false;
	}

}
