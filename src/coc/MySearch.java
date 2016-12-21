/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coc;
import java.awt.BorderLayout; 
import java.awt.Component; 
import java.awt.event.KeyAdapter; 
import java.awt.event.KeyEvent;   
import javax.swing.DefaultListModel; 
import javax.swing.JFrame; 
import javax.swing.JList; 
import javax.swing.JPopupMenu; 
import javax.swing.JScrollPane; 
import javax.swing.JTextField;   
public class MySearch extends JFrame{ 	
    private JTextField txfSearch; 	
    private JList listSearch; 	
    private JScrollPane scrollPane; 	 	
    private JPopupMenu popMenu; 	
    private DefaultListModel model; 	
    public MySearch() { 		 		
        txfSearch = new JTextField(); 		
        listSearch = new JList(); 		
        scrollPane = new JScrollPane(listSearch); 		
        popMenu = new JPopupMenu(); 		 		
        this.setLayout(new BorderLayout()); 		
        this.add(txfSearch, BorderLayout.NORTH); 		
        model = new DefaultListModel(); 		
        
        popMenu.add(scrollPane); 		
        listSearch.setModel(model); 		
        
        model.addElement("Item1"); 		
        model.addElement("Item2"); 		
        
        model.addElement("Item2"); 		
        model.addElement("Item2"); 		
        
        model.addElement("Item2"); 		
        model.addElement("Item2"); 		
        model.addElement("Item2"); 		
        model.addElement("Item2"); 		
        model.addElement("Item2"); 		
        model.addElement("Item2"); 		
        model.addElement("Item2"); 		 		
        txfSearch.addKeyListener(new KeyAdapter() { 			
            public void keyReleased(KeyEvent ke) { 				
                if(ke.getKeyCode() == KeyEvent.VK_DOWN) { 					
                    listSearch.requestFocus(); 					
                    listSearch.setSelectedIndex(0); 					
                    return; 				} 				
                if(!popMenu.isVisible()) { 					
                    popMenu.show((Component)ke.getSource(), txfSearch.getX(),
                            txfSearch.getY()+20); 				} 				
                txfSearch.requestFocus();   			} 		}); 		 		
        this.setSize(400, 200); 		this.setVisible(true); 	} 	 	
    public static void main(String args[]) { 		new MySearch(); 	
    }	   
}
