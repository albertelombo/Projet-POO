package modele.joueurs;

import java.util.Scanner;

import modele.partie.Carte;
import modele.partie.Partie;
import modele.plateau.Plateau;

/**
 * This class represents a real player.
 * 
 * @author Albert
 * @see Joueur
 *
 */
public class JoueurReel extends Joueur {
	Scanner sc = new Scanner(System.in);

	/**
	 * Instantiates a real player.
	 * 
	 * @param nom the player's name.
	 */
	public JoueurReel(String nom) {
		super(nom);
	}

	/**
	 * Defines the differents steps leading to a real player's play as required by
	 * the parent class
	 * 
	 * @see Joueur
	 */
	@Override
	public void jouer() {

		Partie partie = Partie.getInstance();
		Plateau plateau = partie.getPlateau();
		// Queue<Carte> deck = partie.getDeck();
		System.out.println(
				"/***********************************************************************************************************************/  \n \n");

		System.out.println(nom
				+ " veuillez choisir comment vous voulez jouer: \n 1 pour poser une carte; \n 2 pour poser puis déplacer une carte; "
				+ "\n 3 pour déplacer puis poser une carte");

		int option = sc.nextInt();
		sc.nextLine();
		if (option == 1) {
			// if(! deck.isEmpty()) tirerCarte(deck);
			faireDeposerCarte(plateau);
		} else if (option == 2) {
			// if(! deck.isEmpty()) tirerCarte(deck);
			faireDeposerCarte(plateau);
			faireDeplacerCarte(plateau);

		} else {
			faireDeplacerCarte(plateau);
			// if(! deck.isEmpty()) tirerCarte(deck);
			faireDeposerCarte(plateau);
		}
	}

	/**
	 * Guides the real player so that he can put a card on the board.
	 * 
	 * @param plateau the board of the game.
	 */
	public void faireDeposerCarte(Plateau plateau) {
		afficherMain();

		boolean placementReussi = false;
		afficherMain();
		System.out.println(" Rentrer l'indice de la carte que vous voulez jouer: ");
		int choix = sc.nextInt();
		Carte carte = main.get(choix);
		while (!placementReussi) {

			System.out.println("Rentrer l'abscisse de votre carte avec pour référence la carte la plus à gauche");
			int x = sc.nextInt();
			sc.nextLine();
			System.out.println("Rentrer l'ordonnée de votre carte avec pour référence la carte la plus en bas");
			int y = sc.nextInt();
			sc.nextLine();
			int[] position = new int[2];
			position[0] = x;
			position[1] = y;
			if (poserCarte(plateau, carte, position)) {
				placementReussi = true;
			} else {
				System.out.println("Vous allez recommencer le placement de la carte");
			}
		}

		main.remove(carte);
	}

	/**
	 * Guides the player so that he can move a card on the board.
	 * 
	 * @param plateau
	 */
	public void faireDeplacerCarte(Plateau plateau) {

		boolean reprendre = true;
		int choix = 0;
		while (reprendre) {
			System.out
					.println("Rentrer l'abscisse de la carte à déplacer avec pour référence la carte la plus à gauche");
			int x = sc.nextInt();
			sc.nextLine();
			System.out.println("Rentrer l'ordonnée de la carte à déplacer avec pour référence la carte la plus en bas");
			int y = sc.nextInt();
			sc.nextLine();
			int[] positionInitiale = new int[2];
			positionInitiale[0] = x;
			positionInitiale[1] = y;
			System.out.println("Rentrer la nouvelle abscisse");
			x = sc.nextInt();
			sc.nextLine();
			System.out.println("Rentrer la nouvelle ordonnée");
			y = sc.nextInt();
			sc.nextLine();
			int[] positionFinale = new int[2];
			positionFinale[0] = x;
			positionFinale[1] = y;
			if (deplacerCarte(plateau, positionInitiale, positionFinale)) {
				reprendre = false;
			} else {
				System.out.println(
						"Votre déplacement a échoué. \n Rentrer 1 si vous voulez reprendre, un autre entier sinon.");
				choix = sc.nextInt();
				sc.nextLine();
				if (choix != 1) {
					reprendre = false;
				}
			}
		}
	}

}
