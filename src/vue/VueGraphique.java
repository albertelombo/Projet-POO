package vue;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controleur.ControleurVueGraphique;
import modele.joueurs.Joueur;
import modele.joueurs.JoueurReel;
import modele.joueurs.JoueurVirtuel;
import modele.joueurs.Strategie;
import modele.joueurs.StrategieVAdvancedRandom;
import modele.joueurs.StrategieVClassique;
import modele.joueurs.StrategieVClassiqueRandom;
import modele.partie.Carte;
import modele.partie.Partie;
import modele.partie.Variante;
import modele.partie.VarianteAdvanced;
import modele.partie.VarianteClassique;
import modele.plateau.Plateau;
import modele.plateau.PlateauCarre;
import modele.plateau.PlateauRectangulaire;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * This class represents the whole graphical user interface.
 * 
 * @author Albert
 *
 */
@SuppressWarnings("deprecation")
public class VueGraphique implements Observer {

	/**
	 * The frame containing the view.
	 */
	private JFrame Vue;

	private static Scanner sc;

	/**
	 * The space where players play.
	 */
	private ZoneDeJeu EspaceJeu;
	
	/**
	 * Text zone where the results will be displayed
	 */
	private JTextField espaceResultats;
	
	/**
	 * Heading of the results
	 */
	private JTextField En_tete_resultats;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		sc = new Scanner(System.in);

		Partie partie = Partie.getInstance();
		Variante variante;
		System.out.println("Quelle variante voulez vous: 1 pour la classique et 2 pour AdvancedShapeUp");
		int choixVar = sc.nextInt();
		sc.nextLine();

		if (choixVar == 1) {
			variante = new VarianteClassique();
		} else {
			variante = new VarianteAdvanced();
		}
		// Joueurs

		System.out.println("Combien de joueurs souhaitez-vous ?");
		int nbJoueurs = sc.nextInt();
		sc.nextLine();
		ArrayList<Joueur> joueurs = new ArrayList<Joueur>();

		for (int i = 0; i < nbJoueurs; i++) {
			int choix;
			String nom;
			Strategie strategie;
			System.out.println("Quel est le nom du nouveau joueur? ");
			nom = sc.nextLine();
			System.out.println("Quel type de joueur voulez-vous: \n -1 pour réél; \n -2 pour virtuel");
			choix = sc.nextInt();
			sc.nextLine();
			if (choix == 1) {
				joueurs.add(new JoueurReel(nom));
			} else {
				if (choixVar == 1) {
					System.out.println("Quelle strategie voulez vous pour le joueur virtuelle: \n"
							+ "-1 pour l'aléatoire \n -2 pour la cohérente ");
					int choix2 = sc.nextInt();
					sc.nextLine();
					if (choix2 == 1) {
						strategie = new StrategieVClassiqueRandom();
					} else {
						strategie = new StrategieVClassique();
					}
				} else {
					strategie = new StrategieVAdvancedRandom();
				}
				joueurs.add(new JoueurVirtuel(nom, strategie));
			}
		}
		// on attribue aux joueurs leurs indices
		int indice = 0;
		for (Joueur joueur : joueurs) {
			joueur.setIndice(indice);
			indice++;
		}

		// Plateau
		Plateau plateau;
		System.out.println("Quelle forme de plateau choisissz vous: \n "
				+ "-1 pour le plateau rectangulaire; \n -2 pour le plateau carré");
		int choix;
		choix = sc.nextInt();
		sc.nextLine();
		if (choix == 1) {
			plateau = new PlateauRectangulaire();
		} else {
			plateau = new PlateauCarre();
		}

		// Vue

		partie.definirparametres(plateau, variante, joueurs);

		// *****************************************************************//
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VueGraphique window = new VueGraphique(Partie.getInstance());
					@SuppressWarnings("unused")
					ControleurVueGraphique controleur = new ControleurVueGraphique(window);
					window.Vue.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

//***********************************************************************//

		@SuppressWarnings("unused")
		VueConsole console = new VueConsole();
		partie.piloterpartie();

		partie.piloterpartie();
	}

	/**
	 * Create the graphical view.
	 */
	public VueGraphique(Partie partie) {
		initialize();

		// On rajoute la vue comme observateur des éléments

		// Plateau

		partie.getPlateau().addObserver(this.EspaceJeu.getEspacePlateau());
		if (partie.getVariante() instanceof VarianteClassique) {
			VarianteClassique variante = (VarianteClassique) partie.getVariante();
			variante.addObserver(this.EspaceJeu.getEspacePlateau());
			variante.addObserver(this.EspaceJeu);
			variante.addObserver(this);
		}else if(partie.getVariante() instanceof VarianteAdvanced) {
			VarianteAdvanced variante = (VarianteAdvanced) partie.getVariante();
			variante.addObserver(this.EspaceJeu.getEspacePlateau());
			variante.addObserver(this.EspaceJeu);
			variante.addObserver(this);
		}

		// Les zones de jeu
		int ind = 0;
		for (Joueur player : partie.getJoueurs()) {
			player.addObserver(EspaceJeu.getZonesJoueurs()[ind]);
			ind++;
		}
	}

	/**
	 * Initialize the contents of the view.
	 */
	private void initialize() {
		Vue = new JFrame();
		Vue.setBounds(100, 100, 1282, 920);
		Vue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Vue.getContentPane().setLayout(new BorderLayout(0, 0));
		// Les pages de la vue
		JPanel Contenu = new JPanel();
		Vue.getContentPane().add(Contenu);
		Contenu.setLayout(new CardLayout(0, 0));

		// La page où l'on joue
		EspaceJeu = new ZoneDeJeu();
		Contenu.add(EspaceJeu, "aire_jeu");

		// La page ou on affiche les résultats

		JPanel Resultats = new JPanel();
		Resultats.setBackground(Color.CYAN);
		Contenu.add(Resultats, "result");
		Resultats.setLayout(null);
		
		espaceResultats = new JTextField();
		espaceResultats.setBounds(370, 150, 476, 312);
		Resultats.add(espaceResultats);
		espaceResultats.setColumns(10);
		
		En_tete_resultats = new JTextField();
		En_tete_resultats.setHorizontalAlignment(SwingConstants.CENTER);
		En_tete_resultats.setText("Ici sont affich\u00E9s les r\u00E9sultats");
		En_tete_resultats.setBounds(450, 66, 319, 30);
		Resultats.add(En_tete_resultats);
		En_tete_resultats.setColumns(10);

		JPanel Navigation = new JPanel();
		Navigation.setBackground(Color.ORANGE);
		Vue.getContentPane().add(Navigation, BorderLayout.NORTH);
		CardLayout cl = (CardLayout) Contenu.getLayout();
		cl.show(Contenu, "aire_jeu");

		JButton btnNewButton = new JButton("Partie");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) Contenu.getLayout();
				cl.show(Contenu, "aire_jeu");
			}
		});
		Navigation.add(btnNewButton);

		JButton btnNewButton_2 = new JButton("Zone des r\u00E9sultats");
		Navigation.add(btnNewButton_2);
		
		btnNewButton_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CardLayout cl = (CardLayout) Contenu.getLayout();
				cl.show(Contenu, "result");
			}
		});
	}

	/**
	 * React when an observable element sends a notification.
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		if (o instanceof Variante && arg instanceof String) {
			if ((String) arg == Partie.SCORE) {
				espaceResultats.setText(Partie.getInstance().getTexteResultat());
			}
		}

	}

	/**
	 * Fetch the image of a card into an icon.
	 * 
	 * @param carte the card.
	 * @return the image icon of the card.
	 */
	public static ImageIcon recupererImage(Carte carte) {
		ImageIcon ii;
		String nom = carte.toString();
		StringBuffer chemin = new StringBuffer();
		chemin.append("src/cartes/");
		chemin.append(nom);
		chemin.append(".jpg");
		String url = chemin.toString();
		ii = new ImageIcon(url);
		Image image = ii.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ii = new ImageIcon(image);
		return ii;
	}

	public ZoneDeJeu getEspaceJeu() {
		return EspaceJeu;
	}

	public void setEspaceJeu(ZoneDeJeu espaceJeu) {
		EspaceJeu = espaceJeu;
	}
}
