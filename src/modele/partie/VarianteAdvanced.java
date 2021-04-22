package modele.partie;

import java.util.Observable;
import java.util.Queue;

import modele.joueurs.*;

/**
 * This class represents the advanced ShapeUp game scenario.
 * 
 * It is observable by the graphical user interface.
 * 
 * @author Albert&Christian
 *
 */
@SuppressWarnings("deprecation")
public class VarianteAdvanced extends Observable implements Variante{

	/**
	 * Instantiates the game scenario.
	 */
	public VarianteAdvanced() {

	}

	/**
	 * This method defines the game scenario as asked by the interface.
	 * 
	 * @param Partie a reference on the game.
	 * @see Variante
	 */
	public void piloterPartie(Partie partie) {
		int nbJoueurs = partie.getJoueurs().size();
		System.out.println("On a " + nbJoueurs + "Joueurs");

		// on initialise le premier joueur
		int premierJ = 0;
		for (int i = 0; i < 4; i++) {

			System.out.println("Round " + (i + 1));

			if ((premierJ + 1) > nbJoueurs) {
				premierJ = 0;
			}

			// On intialise les variables
			partie.getPlateau().getCases().clear();

			this.setChanged();
			this.notifyObservers(Partie.VIDERPLATEAU);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			partie.getPlateau().setD1Pos(0);
			partie.getPlateau().setD1Neg(0);
			partie.getPlateau().setD2Pos(0);
			partie.getPlateau().setD2Neg(0);
			Partie.offsetGraphiqueH = 4;
			Partie.offsetGraphiqueV = 4;
			partie.getPlateau().setCentreOccupe(false);
			piloterRound(partie, premierJ, nbJoueurs);

			premierJ++;
		}

		// On promulgue les resultats

		System.out.println(
				"/***************************************************************************************************/");
		System.out.println("                           Les résultats");
		int scoreMax = 0;
		int nbJAequo = 0;
		for (Joueur joueur : partie.getJoueurs()) {
			if (joueur.getPoints() > scoreMax) {
				scoreMax = joueur.getPoints();
				nbJAequo = 0;
			} else if (joueur.getPoints() == scoreMax) {
				nbJAequo++;
			}
		}

		for (Joueur joueur : partie.getJoueurs()) {
			if (joueur.getPoints() == scoreMax && nbJAequo == 0) {
				System.out.println(joueur.getNom() + " gagne la partie avec " + scoreMax + " points");

			} else if (joueur.getPoints() == scoreMax && nbJAequo > 0) {
				System.out.println(joueur.getNom() + " est premier ex aequo avec " + scoreMax + " points");

			} else {
				System.out.println(joueur.getNom() + " perd avec " + joueur.getPoints() + "points");
			}
		}

	}

	/**
	 * This method defines the way a round goes on as asked by the interface.
	 * 
	 * @param Partie a reference on the game.
	 * @see Variante
	 */
	@Override
	public void piloterRound(Partie partie, int premierJ, int nbJoueurs) {
		int cursor = premierJ;
		partie.initiateDeck();
		Queue<Carte> deck = partie.getDeck();

		//définir la hidden Card
		partie.setHiddenCard(deck.remove());
		// attribuer les cartes aux joueurs

		for(Joueur joueur:partie.getJoueurs()) {
			for(int i=1;i<=3;i++) {
				joueur.tirerCarte(deck);
			}
		}
		while(verifierFin(partie) == false) {

			/***************************************/

			Partie.temoinPris = false;
			Partie.deplacementEffectue = false;
			Partie.carteChoisie = false;

			//	 On affiche le plateau avant chaque mouvement
			this.setChanged();
			this.notifyObservers(Partie.RAFPLATEAU);

			// On fait jouer les joueurs

			// ON définit le joueur en cours 

			if(cursor%nbJoueurs == 0) {
				partie.setJoueurEnCours(partie.getJoueurs().get(0));
			}else if(cursor%nbJoueurs == 1) {
				partie.setJoueurEnCours(partie.getJoueurs().get(1));
			}else if(cursor%nbJoueurs == 2) {
				partie.setJoueurEnCours(partie.getJoueurs().get(2));
			}


			if(partie.getJoueurEnCours() instanceof JoueurVirtuel) {
				partie.getJoueurEnCours().jouer();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {

				synchronized(Partie.verrou) {
					partie.setStade(StadePartie.MOUVEMENT);
					this.setChanged();
					this.notifyObservers(Partie.DEMANDERMVT);
					try {
						Partie.verrou.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					partie.setStade(StadePartie.NEUTRE);
				}
			}
			// le joueur va tirer une carte

			if(! deck.isEmpty()) partie.getJoueurEnCours().tirerCarte(deck);
			cursor++;
		}

		// attribuer les victory cards à la fin de la partie

		for(Joueur joueur:partie.getJoueurs()) {
			joueur.setVictoryCard(joueur.getMain().remove(0));
		}
		// On compte le score

		partie.getPlateau().afficherPlateau();
		for(Joueur joueur:partie.getJoueurs()) {
			System.out.println(joueur.getNom() + " VictoryCard: " + joueur.getVictoryCard());
		}

		partie.compterScore();

		// On affiche le score de chaque joueur .

		for(Joueur joueur:partie.getJoueurs()) {
			System.out.println(joueur.getNom() + " :" + joueur.getScoreRound());
		}

		// On définit le /les gagnants de la manche
		int scoreMax = 0;
		int nbJAequo=0;
		for(Joueur joueur:partie.getJoueurs()) {
			if(joueur.getScoreRound() > scoreMax) {
				scoreMax = joueur.getScoreRound() ;
				nbJAequo =0;
			}else if(joueur.getScoreRound() == scoreMax) {
				nbJAequo++;
			}
		}
		
		// On affiche le nombre de points gagnes grace à la manche
		System.out.println("/***************************************************************************************************/");
		System.out.println("                           Les résultats du round ")	;
		StringBuffer bf = new StringBuffer();
		for(Joueur joueur:partie.getJoueurs()) {
			if(joueur.getScoreRound() == scoreMax && nbJAequo==0) {
				System.out.println(joueur.getNom()+ " gagne 3 points ");
				bf.append("\n"+ joueur.getNom() + " gagne 3 points  ");
				int pts;
				pts = joueur.getPoints();
				pts += 3 ;
				joueur.setPoints(pts);
			}else if(joueur.getScoreRound() == scoreMax && nbJAequo > 0) {
				System.out.println(joueur.getNom()+ " gagne 3 points ");
				bf.append("\n"+ joueur.getNom() + " gagne 3 points  ");
				int pts;
				pts = joueur.getPoints();
				pts += 1 ;
				joueur.setPoints(pts);
			}else {
				System.out.println(joueur.getNom()+ " ne gagne rien ");
				bf.append("\n"+ joueur.getNom() + " ne gagne rien  ");
			}
		}
		partie.setTexteResultat(bf.toString());

		// On invite les joueurs à regarder le score

		synchronized(Partie.verrou) {
			partie.setStade(StadePartie.SCORE);
			this.setChanged();
			this.notifyObservers(Partie.SCORE);
			try {
				Partie.verrou.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			partie.setStade(StadePartie.NEUTRE);
		}

		// On remet les scores à 0 pour le round suivant

		for(Joueur joueur:partie.getJoueurs()) {
			joueur.setScoreRound(0);
		}

	}

	public boolean verifierFin(Partie partie) {
		boolean fin = true;
		for(Joueur joueur:partie.getJoueurs()) {
			if(joueur.getMain().size() >1) {
				fin = false;
				break;
			}
		}
		return fin;
	}

}
