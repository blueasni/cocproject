/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coc;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Answer extends JFrame {
public static final int MAXITEMS =100;
JPanel panel  = new JPanel();
JTextField textField = new JTextField(10);
String[] myDataBase = {"Juby","Jaz","Jasmine","Joggy","one","dog", "cat" ,"parot"};
String[] listItems;
JList theList = new JList() ;

public Answer() {
	this.add(panel);
	panel.setPreferredSize(new Dimension(500,300));
	panel.add(textField);
	panel.add(theList);

	textField.addKeyListener(new KeyAdapter() {

		public void keyTyped(KeyEvent ke) {
			String compareString = textField.getText()+ke.getKeyChar();
			listItems = new String[MAXITEMS];
			int counter = 0;
			for (int i = 0 ; i <myDataBase.length ; i++){
				if (counter < MAXITEMS){
					if (myDataBase[i].substring(0, compareString.length()).equals(compareString)){
						listItems[counter] = myDataBase[i];
						counter++;
					}
				}
			}
			theList.setListData(listItems);

		}
	});

}

public static void main(String[] args) {
	final Answer answer = new Answer();
	SwingUtilities.invokeLater(new Runnable() {

		@Override
		public void run() {
			answer.pack();
			answer.setVisible(true);
		}
	});
}

}