package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modele.joueurs.Joueur;
import modele.partie.Carte;
import modele.partie.Partie;

/**
 * This class represents the hand of a player.
 * 
 * @author Albert
 *
 */
@SuppressWarnings("deprecation")
public class ZoneJoueur extends JPanel implements Observer {

	/**
	 * Instantiates the hand of a player.
	 * 
	 * @param main        the hand.
	 * @param victoryCard the victory card.
	 * @param vcIcon      the image of the victory card.
	 */
	public ZoneJoueur(JTable main, JPanel victoryCard, JLabel vcIcon) {
		super();
		this.main = main;
		this.victoryCard = victoryCard;
		this.vcIcon = vcIcon;
	}

	private static final long serialVersionUID = 1L;

	/**
	 * The table representing the hand of a player
	 */
	private JTable main;

	/**
	 * The victory card of a player
	 */
	private JPanel victoryCard;

	/**
	 * The image of a victory card
	 */
	private JLabel vcIcon;

	/**
	 * The ListSelectionModel of the table used for the hand of a players.
	 */
	private ListSelectionModel cellSelectionModel;
	private int indiceChoix = -1;

	/**
	 * Tells if the playeer has chosen a card
	 */
	private boolean carteChoisie = false;
	private int indiceZone;
	/**
	 * The field used to display the player's name.
	 */
	private JTextField nom;

	/**
	 * The player's name.
	 */
	private String nomJoueur = "Joueur";

	/**
	 * Tells if the hand is visible or not
	 */
	private boolean mainVisible = false;

	/**
	 * Tells if the victory card is visible or not.
	 */
	private boolean vCVisible = false;
	JButton afficherMain;

	/**
	 * Create the panel.
	 */
	public ZoneJoueur() {
		setBackground(new Color(47, 79, 79));
		setLayout(null);

		main = new JTable();
		main.setCellSelectionEnabled(true);
		cellSelectionModel = main.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		main.setForeground(Color.PINK);
		main.setFillsViewportHeight(true);
		main.setBackground(Color.WHITE);
		main.setBorder(new LineBorder(new Color(0, 0, 0)));
		main.setRowHeight(55);
		main.setVisible(false);
		for (int i = 0; i < main.getColumnCount(); i++) {
			main.getColumnModel().getColumn(i).setPreferredWidth(55);
		}
		main.setModel(new DefaultTableModel(new Object[][] { { null, null, null }, },
				new String[] { "New column", "New column", "New column" }) {
			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int column) {
				for (int i = 0; i < this.getRowCount(); i++) {
					// The first valid value of a cell of given column is retrieved.
					if (getValueAt(i, column) != null) {
						return getValueAt(i, column).getClass();
					}
				}
				// if no valid value is found, default renderer is returned.
				return super.getColumnClass(column);
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		});
		main.setCellSelectionEnabled(true);
		main.setBounds(35, 43, 225, 55);
		add(main);

		victoryCard = new JPanel();
		victoryCard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				afficherVictoryCard();
			}
		});
		victoryCard.setBounds(337, 38, 60, 60);
		add(victoryCard);
		victoryCard.setLayout(new BorderLayout(0, 0));

		vcIcon = new JLabel();
		vcIcon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				afficherVictoryCard();
			}
		});
		victoryCard.add(vcIcon);

		nom = new JTextField(nomJoueur);
		nom.setHorizontalAlignment(SwingConstants.CENTER);
		nom.setBounds(49, 11, 147, 20);
		add(nom);
		nom.setColumns(10);

		afficherMain = new JButton("Afficher main");
		afficherMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				afficherMain();
			}
		});
		afficherMain.setBounds(278, 10, 119, 23);
		add(afficherMain);

		// L'aspect par défaut

		setDefaultAspect();

	}

	public void setDefaultAspect() {
		String url = "src/cartes/face_cachee.png";
		ImageIcon ii = new ImageIcon(url);
		Image image = ii.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
		ii = new ImageIcon(image);
		for (int i = 0; i < 3; i++) {
			main.getModel().setValueAt(ii, 0, i);
		}

		vcIcon.setIcon(ii);
	}

	public void afficherMain() {
		if (mainVisible == false) {
			main.setVisible(true);
			afficherMain.setText("Cacher main");
			mainVisible = true;
		} else {
			main.setVisible(false);
			afficherMain.setText("Afficher main");
			mainVisible = false;
		}
	}

	public void afficherVictoryCard() {
		if (vCVisible == true) {
			String url = "src/cartes/face_cachee.png";
			ImageIcon ii = new ImageIcon(url);
			Image image = ii.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
			ii = new ImageIcon(image);
			vcIcon.setIcon(ii);
			vCVisible = false;
		} else {
			if (indiceZone < Partie.getInstance().getJoueurs().size()) {
				if (Partie.getInstance().getJoueurs().get(indiceZone).getVictoryCard() != null) {
					Carte vc = Partie.getInstance().getJoueurs().get(indiceZone).getVictoryCard();
					ImageIcon ii = VueGraphique.recupererImage(vc);
					vcIcon.setIcon(ii);
					vCVisible = true;
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (o instanceof Joueur && arg instanceof String) {
			if ((String) arg == Partie.TIRERCARTE) {
				// synchronized(Partie.verrou2) {
				Joueur player = (Joueur) o;
				int indice = 0;
				ImageIcon ii = null;
				for (Carte carte : player.getMain()) {
					ii = VueGraphique.recupererImage(carte);
					main.getModel().setValueAt(ii, 0, indice);
					indice++;

				}
				// }
			}
		}
	}

	public ListSelectionModel getCellSelectionModel() {
		return cellSelectionModel;
	}

	public void setCellSelectionModel(ListSelectionModel cellSelectionModel) {
		this.cellSelectionModel = cellSelectionModel;
	}

	public int getIndiceChoix() {
		return indiceChoix;
	}

	public void setIndiceChoix(int indiceChoix) {
		this.indiceChoix = indiceChoix;
	}

	public boolean isCarteChoisie() {
		return carteChoisie;
	}

	public void setCarteChoisie(boolean carteChoisie) {
		this.carteChoisie = carteChoisie;
	}

	public JTable getMain() {
		return main;
	}

	public void setMain(JTable main) {
		this.main = main;
	}

	public void ajouterMouseListener() {
		main.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (Partie.getInstance().getJoueurEnCours().getIndice() == indiceZone) {
					JTable target = (JTable) e.getSource();
					// int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					setCarteChoisie(true);
					Partie.carteChoisie = true;
					indiceChoix = column;
					System.out.println("indice: " + indiceChoix);
				}
			}
		});
	}

	public int getIndiceZone() {
		return indiceZone;
	}

	public void setIndiceZone(int indiceZone) {
		this.indiceZone = indiceZone;
	}

	public JTextField getNom() {
		return nom;
	}

	public void setNom(JTextField nom) {
		this.nom = nom;
	}

	public JButton getAfficherMain() {
		return afficherMain;
	}

	public void setAfficherMain(JButton afficherMain) {
		this.afficherMain = afficherMain;
	}

	public String getNomJoueur() {
		return nomJoueur;
	}

	public void setNomJoueur(String nomJoueur) {
		this.nomJoueur = nomJoueur;
		this.nom.setText(nomJoueur);
	}

	public boolean isMainVisible() {
		return mainVisible;
	}

	public void setMainVisible(boolean mainVisible) {
		this.mainVisible = mainVisible;
	}
}
