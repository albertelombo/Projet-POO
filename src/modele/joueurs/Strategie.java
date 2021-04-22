package modele.joueurs;

/**
 * This interface gives the ability to virtual players to implement a game strategy
 * 
 * It is a step in using the Strategy design pattern
 * 
 * @author Albert&Christian
 *
 */
public interface Strategie {
	/**
	 * This method will contain all the details concerning the Ai's behavior.  
	 * 
	 * @param joueur the Virtual player.
	 */
	public void deployerStrategie(Joueur joueur);
}
