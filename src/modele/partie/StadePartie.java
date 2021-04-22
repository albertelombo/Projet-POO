package modele.partie;

/**
 * Gives the state of the game
 * 
 * Either the state is neutral (Neutre) , either the game is waiting for a
 * player to play (Movement), either it is the time to give the scores (Score)
 * 
 * @author Albert
 *
 */
public enum StadePartie {
	NEUTRE, MOUVEMENT, SCORE
}
