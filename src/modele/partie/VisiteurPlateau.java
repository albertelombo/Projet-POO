package modele.partie;

import modele.plateau.Plateau;

/**
 * This interface implies the ability to visit a board in the context of the
 * design pattern Visitor.
 * 
 * @author Albert
 *
 */
public interface VisiteurPlateau {
	/**
	 * This feature will have to be defined by the visitors. It will contain their
	 * work while going through the board.
	 * 
	 * @param plateau the board
	 */
	public void visiter(Plateau plateau);
}
