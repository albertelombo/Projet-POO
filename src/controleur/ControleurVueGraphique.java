package controleur;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import modele.joueurs.Joueur;
import modele.partie.Carte;
import modele.partie.Partie;
import modele.partie.StadePartie;
import vue.PlateauGraphique;
import vue.VueGraphique;
import vue.ZoneJoueur;

/**
 * This class is the controller of our program.
 * 
 * @author Albert&Christian
 *
 */
public class ControleurVueGraphique {
	
	/**
	 * A reference on the graphical view.
	 */
	private VueGraphique interfaceG;
	
	/**
	 * A reference on the game
	 */
	private Partie partie;
	
	/**
	 * Instantiate the controller.
	 * 
	 * @param interfaceG
	 */
	public ControleurVueGraphique(VueGraphique interfaceG) {
		super();
		this.interfaceG = interfaceG;
		partie = Partie.getInstance();
		// Listeners sur les zones de jeu
		
			ZoneJoueur[] zones  = interfaceG.getEspaceJeu().getZonesJoueurs();
			for(ZoneJoueur zone: interfaceG.getEspaceJeu().getZonesJoueurs()) {
				zone.ajouterMouseListener();
			}
				
		// essai avec le clic de souris
				
				
			
			
			// Listeners sur le plateau
			PlateauGraphique EspacePlateau = interfaceG.getEspaceJeu().getEspacePlateau();
			
			// avec le clic de souris
			EspacePlateau.getTablePlateau().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// poser une carte
					JTable target = (JTable)e.getSource();
				      int row = target.getSelectedRow();
				      int column = target.getSelectedColumn();
				      Joueur player = partie.getJoueurEnCours();
				      if(partie.getStade() == StadePartie.MOUVEMENT && Partie.temoinPris == false && Partie.carteChoisie == true) {
				    	  synchronized (Partie.verrou) {
				    		  Partie.temoinPris = true;
				    		  Partie.carteChoisie = false;
				    		  
				        	  int indice_carte = zones[player.getIndice()].getIndiceChoix();
				        	  player.afficherMain();
				        	  Carte carte = player.getMain().get(indice_carte);
				        	  int[] position = new int[2];
				        	  position[0] = column  - Partie.offsetGraphiqueH;
				        	  position[1] = 8 - row  - Partie.offsetGraphiqueV;
				        	  if(player.poserCarte(partie.getPlateau(), carte, position)){
				        			  player.getMain().remove(carte);
				        			  EspacePlateau.setEmplacementChoisi(false);
				        	  }else {
				        		  Partie.carteChoisie = true;
				        		  Partie.temoinPris = false;
				        	  }
				    	  }
				      }else if(Partie.deplacementEffectue == false) {
				    	  // déplacer une carte
				    	  if(partie.getStade() == StadePartie.MOUVEMENT && Partie.carteChoisie == false && EspacePlateau.isEmplacementChoisi() == false) {
				    		  int[] position = new int[2];
				    		  position[0] = column  - Partie.offsetGraphiqueH;
				    		  position[1] = 8 - row  - Partie.offsetGraphiqueV;
				    		  EspacePlateau.setPosI(position);
				    		  EspacePlateau.setEmplacementChoisi(true);
				    	  }else if(partie.getStade() == StadePartie.MOUVEMENT && Partie.carteChoisie == false && EspacePlateau.isEmplacementChoisi() == true) {
				    		  int[] position = new int[2];
				    		  position[0] = column  - Partie.offsetGraphiqueH;
				    		  position[1] = 8 - row  - Partie.offsetGraphiqueV;
				    		  EspacePlateau.setPosF(position);
				    		  player.deplacerCarte(partie.getPlateau(), EspacePlateau.getPosI(), EspacePlateau.getPosF());
				    		  EspacePlateau.setEmplacementChoisi(false);
				    		  EspacePlateau.setPosF(null);
				    		  EspacePlateau.setPosI(null);
				    		  Partie.deplacementEffectue = true;
				    		  
				    	  }
				      }
				}
			});
		}
	

	public VueGraphique getInterfaceG() {
		return interfaceG;
	}

	public void setInterfaceG(VueGraphique interfaceG) {
		
		
		this.interfaceG = interfaceG;
	}
		
}
