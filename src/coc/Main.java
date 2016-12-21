package coc;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;

public class Main {

  public static void main(String[] args) {

    final JFrame frame = new JFrame();

    frame.setLayout(new BorderLayout());

    final JTextField textFieldA = new HintTextField("A hint here");
    final JTextField textFieldB = new HintTextField("Another hint here");

    frame.add(textFieldA, BorderLayout.NORTH);
    frame.add(textFieldB, BorderLayout.CENTER);
    JButton btnGetText = new JButton("Get text");

    btnGetText.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String message = String.format("textFieldA='%s', textFieldB='%s'",
            textFieldA.getText(), textFieldB.getText());
        JOptionPane.showMessageDialog(frame, message);
      }
    });

    frame.add(btnGetText, BorderLayout.SOUTH);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.pack();
  }
}
class HintTextField extends JTextField implements FocusListener {

  private final String hint;
  private boolean showingHint;

  public HintTextField(final String hint) {
    super(hint);
    this.hint = hint;
    this.showingHint = true;
    super.addFocusListener(this);
  }

  @Override
  public void focusGained(FocusEvent e) {
    if(this.getText().isEmpty()) {
      super.setText("");
      showingHint = false;
    }
  }
  @Override
  public void focusLost(FocusEvent e) {
    if(this.getText().isEmpty()) {
      super.setText(hint);
      showingHint = true;
    }
  }

  @Override
  public String getText() {
    return showingHint ? "" : super.getText();
  }
}