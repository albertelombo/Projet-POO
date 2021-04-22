package modele.partie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

import modele.joueurs.Joueur;
import modele.plateau.Plateau;

@SuppressWarnings("deprecation")
public class Partie extends Observable {

	// attributs

	/**
	 * The reference on the current game.
	 */
	private static Partie partie = null;


	public static String RAFPLATEAU = "afficher_plateau";

	public static String DEMANDERMVT = "mouvement";

	public static String VIDERPLATEAU = "vider";

	public static String TIRERCARTE = "tirer";

	public static String SCORE = "regarder";
	public static final Object verrou = new Object();
	public static final Object verrou2 = new Object();
	public static boolean carteChoisie = false;
	public static boolean temoinPris = false;
	public static boolean deplacementEffectue = false;
	public static int indJoueurEnCours = 0;
	/**
	 * A Horizontal offset for the graphical view.
	 */
	public static int offsetGraphiqueH = 4;
	/**
	 * A vertical offset for the graphical view.
	 */
	public static int offsetGraphiqueV = 4;
	public static Partie getInstance() {
		if (Partie.partie == null) {
			Partie.partie = new Partie();
		}
		return Partie.partie;
	}
	
	/**
	 * Text for the score announcement
	 */
	private String texteResultat = "";
	
	/**
	 * The list of players enrolled.
	 */
	private ArrayList<Joueur> joueurs;
	/**
	 * A reference on the board.
	 */
	private Plateau plateau;
	/**
	 * The deck of the game.
	 */
	private Queue<Carte> deck;
	/**
	 * The game scenario.
	 */
	private Variante variante;
	/**
	 * All the cards of the game.
	 */
	private ArrayList<Carte> ensembleCarte;

	/**
	 * The agent handling the score counting.
	 */
	private VisiteurPlateau compteur;
	/**
	 * The hidden card of the game.
	 */
	private Carte hiddenCard;

	private Joueur JoueurEnCours = null;

	/**
	 * The state in which the game is.
	 */
	private StadePartie stade = StadePartie.NEUTRE;

	/**
	 * Instantiates the game.
	 * 
	 * It is private due to the pattern used : Singleton (in French).
	 */
	private Partie() {
		ensembleCarte = creerEnsembleCartes();
		compteur = new Compteur();
	}

	/**
	 * Asks for score counting at the end of a round.
	 */
	public void compterScore() {
		compteur.visiter(plateau);
	}

	/**
	 * Creates all the possible cards.
	 * 
	 * @return a list of all the cards
	 */
	private ArrayList<Carte> creerEnsembleCartes() {
		ArrayList<Carte> ensembleCarte = new ArrayList<Carte>();

		for (int j = 0; j < 3; j++) {
			for (int k = 0; k < 3; k++) {
				for (int l = 0; l < 2; l++) {
					ensembleCarte.add(new Carte(j, k, l));
				}
			}
		}
		return ensembleCarte;
	}

	/**
	 * Sets the parameters of the game.
	 * 
	 * @param plateau  a reference on the board.
	 * @param variante the game scenario.
	 * @param Joueurs  the list of players.
	 */
	public void definirparametres(Plateau plateau, Variante variante, ArrayList<Joueur> Joueurs) {
		this.setJoueurs(Joueurs);
		this.variante = variante;
		this.setPlateau(plateau);
	}

	public VisiteurPlateau getCompteur() {
		return compteur;
	}

	public Queue<Carte> getDeck() {
		return deck;
	}

	public Carte getHiddenCard() {
		return hiddenCard;
	}

	public Joueur getJoueurEnCours() {
		return JoueurEnCours;
	}

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public Plateau getPlateau() {
		return plateau;
	}

	public StadePartie getStade() {
		return stade;
	}

	public String getTexteResultat() {
		return texteResultat;
	}

	public Variante getVariante() {
		return variante;
	}

	/**
	 * Initializes the deck at the beginning of the game.
	 */
	public void initiateDeck() {
		// On initialise le deck
		deck = new LinkedList<Carte>();
		Collections.shuffle(ensembleCarte);
		for (Carte carte : ensembleCarte) {
			deck.add(carte);
		}
	}

	/**
	 * runs the game scenario
	 * 
	 * It is one step of the implementation of the design pattern Strategy.
	 */
	public void piloterpartie() {
		variante.piloterPartie(this);
	}

	public void setCompteur(VisiteurPlateau compteur) {
		this.compteur = compteur;
	}

	public void setDeck(Queue<Carte> deck) {
		this.deck = deck;
	}

	public void setHiddenCard(Carte hiddenCard) {
		this.hiddenCard = hiddenCard;
	}

	public void setJoueurEnCours(Joueur joueurEnCours) {
		JoueurEnCours = joueurEnCours;
	}

	public void setJoueurs(ArrayList<Joueur> joueurs) {
		this.joueurs = joueurs;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public void setStade(StadePartie stade) {
		this.stade = stade;
	}

	public void setTexteResultat(String texteResultat) {
		this.texteResultat = texteResultat;
	}

	public void setVariante(Variante variante) {
		this.variante = variante;
	}

}
