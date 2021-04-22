package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modele.partie.Partie;
import modele.partie.StadePartie;
import modele.partie.Variante;

/**
 * This class represents the whole space where the players play.
 * 
 * @author Albert
 *
 */
@SuppressWarnings("deprecation")
public class ZoneDeJeu extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	/**
	 * The board.
	 */
	private PlateauGraphique EspacePlateau;

	/**
	 * The players hands
	 */
	private ZoneJoueur[] zonesJoueurs;

	/**
	 * A space reserved for messages on the game progression.
	 */
	JPanel zoneDeProgression;
	/**
	 * The text of the messages on the game progression.
	 */
	JLabel texteProgression;

	/**
	 * A space for a button used to switch from one round to the next one.
	 */
	JPanel prochainRound;

	/**
	 * A button used to switch from one round to the next one.
	 */
	JButton roundSuivant;

	/**
	 * Create the space.
	 */
	public ZoneDeJeu() {
		setBackground(new Color(165, 42, 42));
		EspacePlateau = new PlateauGraphique();
		EspacePlateau.getTablePlateau().setLocation(0, 0);
		EspacePlateau.setLocation(541, 208);
		EspacePlateau.setSize(683, 500);
		this.add(EspacePlateau);
		this.setLayout(null);
		this.add(EspacePlateau);

		// zones de joueurs

		zonesJoueurs = new ZoneJoueur[3];

		ZoneJoueur z1 = new ZoneJoueur();
		z1.setLocation(45, 248);
		z1.setSize(430, 100);
		ZoneJoueur z2 = new ZoneJoueur();
		z2.setLocation(45, 413);
		z2.setSize(430, 100);
		ZoneJoueur z3 = new ZoneJoueur();
		z3.setLocation(45, 577);
		z3.setSize(430, 100);
		zonesJoueurs[0] = z1;
		zonesJoueurs[1] = z2;
		zonesJoueurs[2] = z3;
		this.add(z1);
		this.add(z2);
		this.add(z3);

		zoneDeProgression = new JPanel();
		zoneDeProgression.setBounds(45, 32, 577, 105);
		add(zoneDeProgression);
		zoneDeProgression.setLayout(new BorderLayout(0, 0));

		texteProgression = new JLabel("Annonces");
		zoneDeProgression.add(texteProgression);

		prochainRound = new JPanel();
		prochainRound.setBounds(707, 32, 164, 105);
		add(prochainRound);
		prochainRound.setLayout(new BorderLayout(0, 0));

		roundSuivant = new JButton("Round Suivant");
		roundSuivant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Partie.getInstance().getStade() == StadePartie.SCORE)
					synchronized (Partie.verrou) {
						Partie.verrou.notify();
					}
			}
		});
		prochainRound.add(roundSuivant);
		int i = 0;
		for (ZoneJoueur zone : zonesJoueurs) {
			this.add(zone);
			zone.setIndiceZone(i);
			if (i < Partie.getInstance().getJoueurs().size())
				zone.setNomJoueur(Partie.getInstance().getJoueurs().get(i).getNom());
			i++;
		}
	}

	public PlateauGraphique getEspacePlateau() {
		return EspacePlateau;
	}

	public void setEspacePlateau(PlateauGraphique espacePlateau) {
		EspacePlateau = espacePlateau;
	}

	public ZoneJoueur[] getZonesJoueurs() {
		return zonesJoueurs;
	}

	public void setZonesJoueurs(ZoneJoueur[] zonesJoueurs) {
		this.zonesJoueurs = zonesJoueurs;
	}

	/**
	 * React when notified by an observable object
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (o instanceof Variante && arg instanceof String) {
			if ((String) arg == Partie.SCORE) {
				texteProgression.setText("Allez regarder les résultats: faut cliquer sur le bouton  pour passer au round suivant");
			}

			if ((String) arg == Partie.DEMANDERMVT) {
				texteProgression.setText(Partie.getInstance().getJoueurEnCours().getNom()
						+ ": Veuillez jouer. \n Commencer par déplacer la carte si nécessaire. \n Si il s'agit du premier tour, jouer sur la case centrale svp.");
			}
		}

	}
}
