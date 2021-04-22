package modele.partie;

/**
 * Defines an interface for the game scenarios.
 * 
 * It is one step in using the design pattern Strategy.
 * 
 * @author Albert&Christian
 *
 */
public interface Variante {
	/**
	 * The method will have to define in detail the game scenario.
	 * 
	 * @param partie a reference on the game
	 */
	public void piloterPartie(Partie partie);

	/**
	 * The method will have to define in detail how a round goes on.
	 * 
	 * @param partie    a reference on the game.
	 * @param premierJ  the index on the first player to play.
	 * @param nbJoueurs the number of players.
	 */
	public void piloterRound(Partie partie, int premierJ, int nbJoueurs);
}
