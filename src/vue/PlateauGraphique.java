package vue;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modele.partie.Partie;
import modele.partie.Variante;
import modele.plateau.Plateau;
import modele.plateau.PosiCarte;

/**
 *  This class represents the graphical board.
 *  
 * @author Albert&Christian
 *
 */
@SuppressWarnings("deprecation")
public class PlateauGraphique extends JPanel implements Observer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * This is the table used for the cards spaces
	 */
	private JTable TablePlateau;
	
	/**
	 * This is the selection model of our table
	 */
	private ListSelectionModel cellSelectionModel;
	
	/**
	 * This boolean tells if space is chosen by the user
	 */
	private boolean emplacementChoisi = false;
	
	/**
	 * The initial position for a card's movement
	 */
	private int[] posI =  null;
	
	/**
	 * The final position for a card's movement
	 */
	private int[] posF = null;

	/**
	 * Create the board.
	 */
	public PlateauGraphique() {
		this.setBackground(new Color(124, 252, 0));
		initialiserTableau();
		setLayout(null);
		this.add(TablePlateau);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(458, 523, -284, -16);
		add(scrollPane);

	}
	
	/**
	 * Initialize the component
	 */
	public void initialiserTableau() {
		TablePlateau = new JTable();
		TablePlateau.setBounds(53, 11, 675, 495);
		TablePlateau.setColumnSelectionAllowed(true);
		TablePlateau.setCellSelectionEnabled(true);
		cellSelectionModel = TablePlateau.getSelectionModel();
	    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		TablePlateau.setBorder(new LineBorder(new Color(0, 0, 0)));
		TablePlateau.setRowHeight(55);
		for( int i = 0; i < TablePlateau.getColumnCount();i++) {
			TablePlateau.getColumnModel().getColumn(i).setPreferredWidth(55);
		}
		TablePlateau.setForeground(UIManager.getColor("textHighlight"));
		TablePlateau.setBackground(Color.WHITE);
		// initialisons les données
	
		TablePlateau.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null},
				},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		) {
			 /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Class<? extends Object> getColumnClass(int column)
	            {
				for(int i = 0; i < this.getRowCount(); i++)
	            {
	                //The first valid value of a cell of given column is retrieved.
	                if(getValueAt(i,column) != null)
	                {
	                    return getValueAt(i, column).getClass();
	                }
	            }
	            //if no valid value is found, default renderer is returned.
	            return super.getColumnClass(column);
	            }
				
			@Override
			public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			}
		});
	}
	
	/**
	 * Clear the spaces of the table
	 */
	public void viderPlateau() {
	synchronized(Partie.verrou) {
			// on veut vider l'aire de jeu après un round
			for(int i=0;i<9;i++) {
				for(int j=0;j<9;j++) {
					TablePlateau.getModel().setValueAt(null, j, i);
				}
			}
		}
	}

	public JTable getTablePlateau() {
		return TablePlateau;
	}

	public void setTablePlateau(JTable tablePlateau) {
		TablePlateau = tablePlateau;
	}
	
	/**
	 * React when notified by an observable object
	 */
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(o instanceof Plateau && arg instanceof PosiCarte) {
			// afficher un carte après qu'on l'ait posée
			synchronized(Partie.verrou) {
			
				PosiCarte posiCarte = (PosiCarte)arg;
				ImageIcon ii = VueGraphique.recupererImage(posiCarte.getCarte());
				int row = 8- ((posiCarte.getPos()[1] - Partie.getInstance().getPlateau().getD2Neg()) + Partie.offsetGraphiqueV);
				int col =  (posiCarte.getPos()[0]- Partie.getInstance().getPlateau().getD1Neg()) +Partie.offsetGraphiqueH;
				TablePlateau.getModel().setValueAt(ii,row,col);
				Partie.verrou.notify();
			}
		}
		
		if (o instanceof Plateau && arg instanceof PosiCarte[]) {
			synchronized(Partie.verrou) {
				PosiCarte[] posiCartes = (PosiCarte[])arg;
				ImageIcon ii = VueGraphique.recupererImage(posiCartes[0].getCarte());
				// on retire la carte de sa position actuelle
				int row = 8- ((posiCartes[0].getPos()[1] - Partie.getInstance().getPlateau().getD2Neg()) + Partie.offsetGraphiqueV);
				int col =  (posiCartes[0].getPos()[0]- Partie.getInstance().getPlateau().getD1Neg()) +Partie.offsetGraphiqueH;
				TablePlateau.getModel().setValueAt(null,row,col);
				
				// on place la carte à sa nouvelleposition
				row = 8- ((posiCartes[1].getPos()[1] - Partie.getInstance().getPlateau().getD2Neg()) + Partie.offsetGraphiqueV);
				col =  (posiCartes[1].getPos()[0]- Partie.getInstance().getPlateau().getD1Neg()) +Partie.offsetGraphiqueH;
				TablePlateau.getModel().setValueAt(ii,row,col);
			}
		}
		
		if(o instanceof Variante && arg instanceof String) {
			
			if((String)arg == Partie.VIDERPLATEAU) {
					viderPlateau();
				
			}
		}
		
	}

	public ListSelectionModel getCellSelectionModel() {
		return cellSelectionModel;
	}

	public void setCellSelectionModel(ListSelectionModel cellSelectionModel) {
		this.cellSelectionModel = cellSelectionModel;
	}

	public boolean isEmplacementChoisi() {
		return emplacementChoisi;
	}

	public void setEmplacementChoisi(boolean emplacementChoisi) {
		this.emplacementChoisi = emplacementChoisi;
	}

	public int[] getPosI() {
		return posI;
	}

	public void setPosI(int[] posI) {
		this.posI = posI;
	}

	public int[] getPosF() {
		return posF;
	}

	public void setPosF(int[] posF) {
		this.posF = posF;
	}
}
