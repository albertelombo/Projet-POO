package modele.joueurs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Queue;

import modele.partie.Carte;
import modele.partie.Partie;
import modele.plateau.Plateau;

/**
 * This abstract class will be used for representing a player of the game.
 * 
 * @author Albert
 *
 */
@SuppressWarnings("deprecation")
public abstract class Joueur extends Observable {

	/**
	 * The name of the player.
	 */
	protected String nom;

	/**
	 * The victory card of the player.
	 */
	protected Carte victoryCard = null;

	/**
	 * The player's hand.
	 */
	protected ArrayList<Carte> main;

	/**
	 * The player's score on the round.
	 */
	protected int scoreRound = 0;

	/**
	 * The number of points of the player through the game.
	 */
	protected int points = 0;

	/**
	 * The player's number in the list of players;
	 */
	protected int indice = -1;

	/**
	 * Instantiates a player.
	 * 
	 * @param nom the name of the player.
	 */
	public Joueur(String nom) {
		this.nom = nom;
		main = new ArrayList<Carte>();
	}

	/**
	 * Consists in moving a card on the board.
	 * 
	 * @param plateau          a reference of the board.
	 * @param positionInitiale the initial position of the card.
	 * @param positionFinale   the position where the player wants to put the card.
	 * @return
	 */
	public boolean deplacerCarte(Plateau plateau, int[] positionInitiale, int[] positionFinale) {
		if (plateau.accepterDeplacementCarte(positionInitiale, positionFinale)) {
			return true;
		}
		return false;
	}

	public int getIndice() {
		return indice;
	}

	public ArrayList<Carte> getMain() {
		return main;
	}

	public String getNom() {
		return this.nom;
	}

	public int getPoints() {
		return points;
	}

	public int getScoreRound() {
		return scoreRound;
	}

	public Carte getVictoryCard() {
		return victoryCard;
	}

	/**
	 * This abstract method will define how the player will play, depending he is a
	 * real or virtual player.
	 */
	public abstract void jouer();

	/**
	 * Consists in putting a card on the board
	 * 
	 * @param plateau  a reference of the board.
	 * @param carte    the card the player wants to put.
	 * @param position the position where the player puts the card.
	 * @return true if the placement went through without problem.
	 */
	public boolean poserCarte(Plateau plateau, Carte carte, int[] position) {
		if (plateau.accepterCarte(carte, position)) {
			return true;
		}
		return false;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void setScoreRound(int scoreRound) {
		this.scoreRound = scoreRound;
	}

	public void setVictoryCard(Carte carte) {
		this.victoryCard = carte;
	}

	/**
	 * Consists in taking a card from the deck.
	 * 
	 * @param deck the deck we take a card on.
	 */
	public void tirerCarte(Queue<Carte> deck) {
		Carte carte = deck.remove();
		main.add(carte);
		this.setChanged();
		this.notifyObservers(Partie.TIRERCARTE);
	}
	
	/**
	 * Displays the player's hand on the console.
	 */
	public void afficherMain() {
		if (main.isEmpty()) {
			System.out.println("La main est vide");
		} else {
			Iterator<Carte> iterMain;
			iterMain = main.iterator();
			int i = 0;
			while (iterMain.hasNext()) {
				System.out.println("Indice: " + i + "    Carte: " + iterMain.next());
				i++;
			}
		}
	}

}
