package modele.joueurs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import modele.partie.Partie;
import modele.plateau.Plateau;

/**
 * This class represents the strategy used in the Advanced ShapeUp scenario
 * 
 * @author Albert&Christian
 *
 */
public class StrategieVAdvancedRandom implements Strategie {

	/**
	 * The list of available position son the board.
	 */
	private ArrayList<Integer[]> positionsPossibles;

	/**
	 * The definition of the AI's behavior as requested by the Stategie interface.
	 * 
	 * @see Strategie
	 */
	@Override
	public void deployerStrategie(Joueur joueur) {
		// TODO Auto-generated method stub
		Partie partie = Partie.getInstance();
		Plateau plateau = partie.getPlateau();
		positionsPossibles = recupererPositionsPossibles();
		joueur.afficherMain();
		Integer[] meilleureOption = choisirMeilleureOption(joueur);
		int[] imeilleureOption = new int[2];
		imeilleureOption[0] = meilleureOption[0] - plateau.getD1Neg(); // adaptation aux indices de choix du joueur réel
		imeilleureOption[1] = meilleureOption[1] - plateau.getD2Neg(); // idem

		System.out.println(joueur.getNom() + ": Je pose une carte ");
		joueur.poserCarte(partie.getPlateau(), joueur.getMain().get(0), imeilleureOption);
		joueur.getMain().remove(0);

	}

	public boolean presentDans(Integer[] position, ArrayList<Integer[]> liste) {
		for (Integer[] pos : liste) {
			if (pos[0] == position[0] && pos[1] == position[1])
				return true;
		}
		return false;
	}

	/**
	 * Fetchs the available positions on the board.
	 * 
	 * @return the list of available positions.
	 */
	public ArrayList<Integer[]> recupererPositionsPossibles() {
		ArrayList<Integer[]> positionsPossibles = new ArrayList<Integer[]>();
		Partie partie = Partie.getInstance();
		Plateau plateau = partie.getPlateau();
		// on récupère les constantes du plateau pour ne pas les modifier

		int d1Pos = plateau.getD1Pos();
		int d1Neg = plateau.getD1Neg();
		int d2Pos = plateau.getD2Pos();
		int d2Neg = plateau.getD2Neg();
		int offsetV = Partie.offsetGraphiqueV;
		int offsetH = Partie.offsetGraphiqueH;
		Set<Integer[]> positions = plateau.getCases().keySet();

		for (Integer[] posi : positions) {

			int[] ipos = new int[2];
			// pour chaque case on au plus quatre espaces adjacents
			// à gauche
			Integer[] posg = posi.clone();
			// Integer[]inter = new Integer[2];
			Integer[] pos;
			posg[0] = posg[0] - 1;
			ipos[0] = posg[0];
			ipos[1] = posg[1];
			pos = posg;
			posg = plateau.referencePosi(posg);
			if (posg == null) {
				if (plateau.verifierNonSuperposition(ipos)) {
					if (!presentDans(pos, positionsPossibles))
						if (plateau.verifierLimites(ipos)) {
							positionsPossibles.add(pos);

							// on remet les limites du tableau

							plateau.setD1Neg(d1Neg);
							plateau.setD1Pos(d1Pos);
							plateau.setD2Neg(d2Neg);
							plateau.setD2Pos(d2Pos);
						}
				}
			}

			// à droite
			Integer[] posd = posi.clone();
			posd[0] = posd[0] + 1;
			ipos[0] = posd[0];
			ipos[1] = posd[1];
			pos = posd;
			posd = plateau.referencePosi(posd);
			if (posd == null) {
				if (plateau.verifierNonSuperposition(ipos)) {
					if (!presentDans(pos, positionsPossibles))
						if (plateau.verifierLimites(ipos)) {
							positionsPossibles.add(pos);

							// on remet les limites du tableau

							plateau.setD1Neg(d1Neg);
							plateau.setD1Pos(d1Pos);
							plateau.setD2Neg(d2Neg);
							plateau.setD2Pos(d2Pos);
						}
				}
			}

			// en haut

			Integer[] posh = posi.clone();
			posh[1] = posh[1] + 1;
			ipos[0] = posh[0];
			ipos[1] = posh[1];
			pos = posh;
			posh = plateau.referencePosi(posh);
			if (posh == null) {
				if (plateau.verifierNonSuperposition(ipos)) {
					if (!presentDans(pos, positionsPossibles))
						if (plateau.verifierLimites(ipos)) {
							positionsPossibles.add(pos);

							// on remet les limites du tableau

							plateau.setD1Neg(d1Neg);
							plateau.setD1Pos(d1Pos);
							plateau.setD2Neg(d2Neg);
							plateau.setD2Pos(d2Pos);
						}
				}
			}

			// en bas

			Integer[] posb = posi.clone();
			posb[1] = posb[1] - 1;
			ipos[0] = posb[0];
			ipos[1] = posb[1];
			pos = posb;
			posb = plateau.referencePosi(posb);
			if (posb == null) {
				if (plateau.verifierNonSuperposition(ipos)) {
					if (!presentDans(pos, positionsPossibles))
						if (plateau.verifierLimites(ipos)) {
							positionsPossibles.add(pos);

							// on remet les limites du tableau

							plateau.setD1Neg(d1Neg);
							plateau.setD1Pos(d1Pos);
							plateau.setD2Neg(d2Neg);
							plateau.setD2Pos(d2Pos);
						}
				}
			}

		}

		Collections.shuffle(positionsPossibles);
		Partie.offsetGraphiqueH = offsetH;
		Partie.offsetGraphiqueV = offsetV;
		return positionsPossibles;
	}

	/**
	 * The player choses the best option between the available positions.
	 * 
	 * @param joueur the player.
	 * @return the chosen position.
	 */
	public Integer[] choisirMeilleureOption(Joueur joueur) {
		Integer[] meilleureOption = null;
		Partie partie = Partie.getInstance();
		Plateau plateau = partie.getPlateau();
		if (plateau.getCases().isEmpty()) {
			meilleureOption = new Integer[2];
			meilleureOption[0] = 0;
			meilleureOption[1] = 0;
		} else {
			Collections.shuffle(positionsPossibles);
			meilleureOption = positionsPossibles.get(0);

		}
		System.out.println("Je choisis l'option: " + (meilleureOption[0] - plateau.getD1Neg()) + ";"
				+ (meilleureOption[1] - plateau.getD2Neg()));
		return meilleureOption;
	}

}
