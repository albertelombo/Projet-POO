package modele.joueurs;

/**
 * This class represents a virtual player: the AI
 * 
 * @author Albert&Christian
 *
 */
public class JoueurVirtuel extends Joueur {

	/**
	 * This is the strategy the virtual player uses to play
	 */
	private Strategie strategie;

	/**
	 * Instantiates the virtual player.
	 * 
	 * @param nom       the name of the virtual player.
	 * @param strategie the strategy of the virtual player.
	 */
	public JoueurVirtuel(String nom, Strategie strategie) {
		super(nom);
		this.strategie = strategie;
	}

	/**
	 * Runs the strategy of the AI
	 * 
	 * @see Joueur
	 */
	public void jouer() {
		strategie.deployerStrategie(this);
	}

}
