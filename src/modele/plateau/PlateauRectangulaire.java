 package modele.plateau;

import java.util.*;

//import modele.joueurs.*;
import modele.partie.*;

/**
 * Represents a board that is going to look like a rectangle at the end of a game round.
 * 
 * @author Albert
 * @see Plateau
 *
 */
public class PlateauRectangulaire extends Plateau{
	
	
	/**
	 * Instantiates a rectangular board.
	 */
	public PlateauRectangulaire() {
		cases = new HashMap<Integer[],Carte>();
		//System.out.println("Le plateau classique est généré");
	}
	
	/**
	 * Defines the validation protocol of a card placement.
	 * 
	 * @see Plateau.verifierConformitePlacement
	 */
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
