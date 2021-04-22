package vue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

import modele.partie.Partie;
import modele.partie.StadePartie;
import modele.partie.Variante;
import modele.partie.VarianteAdvanced;
import modele.partie.VarianteClassique;

/**
 * This class represents the console. The user must choose either to use it,
 * either to use the graphical view.
 * 
 * @author Albert
 *
 */
@SuppressWarnings("deprecation")
public class VueConsole implements Observer, Runnable {

	public static String PROMPT = ">";
	public static String QUITTER = "Quit";

	/**
	 * An instance of the game.
	 */
	private Partie partie;

	/**
	 * Instantiates the console.
	 */
	public VueConsole() {
		partie = Partie.getInstance();
		if (partie.getVariante() instanceof VarianteClassique) {
			((VarianteClassique) partie.getVariante()).addObserver(this);
		} else {
			((VarianteAdvanced) partie.getVariante()).addObserver(this);
		}
		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * Reaction when notified by an observable object.
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

		if (o instanceof Variante && arg instanceof String) {
			if (((String) arg) == Partie.RAFPLATEAU) {
				afficherPlateau();
			} else if (((String) arg) == Partie.DEMANDERMVT) {
				System.out.println(partie.getJoueurEnCours().getNom() + ": Taper 1 pour jouer sur la console ");
			}
		}

	}

	/**
	 * Display the board
	 */
	public void afficherPlateau() {
		partie.getPlateau().afficherPlateau();
	}

	/**
	 * Read the entries
	 * 
	 * @return
	 */
	private String lireChaine() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String resultat = null;
		try {
			System.out.print(VueConsole.PROMPT);
			resultat = br.readLine();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return resultat;
	}

	/**
	 * Ask a player to play
	 * 
	 * @param p a refernce on the game.
	 */
	public void faireJouer(Partie p) {
		synchronized (Partie.verrou) {
			p.getJoueurEnCours().jouer();
			Partie.verrou.notify();
		}
	}

	/**
	 * run the tread of the console.
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

		String saisie = null;
		boolean quitter = false;

		System.out.println("Taper " + VueConsole.QUITTER + " pour quitter.");

		do {
			saisie = this.lireChaine();

			if (saisie != null) {
				if (saisie.equals(VueConsole.QUITTER) == true) {
					quitter = true;
				} else if (saisie.equals("1") == true && partie.getStade() == StadePartie.MOUVEMENT) {
					faireJouer(partie);
				} else {
					System.out.println("Commande non reconnue...");
				}
			}
		} while (quitter == false);
		// System.exit(0);

	}
}
