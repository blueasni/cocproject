package coc;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;

import com.sun.java.swing.plaf.motif.MotifComboBoxUI;
import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;
import java.text.DateFormat;
import java.util.Date;

/**
 * @version 1.0 11/02/2000
 */
//////////////////////////////////////////////////////////////
public class DateComboBox extends JComboBox {

    protected SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setSelectedItem(Object item) {
	// Could put extra logic here or in renderer when item is instanceof Date, Calendar, or String
        // Dont keep a list ... just the currently selected item
        removeAllItems(); // hides the popup if visible
        addItem(item);
        super.setSelectedItem(item);
    }

    public void updateUI() {
        ComboBoxUI cui = (ComboBoxUI) UIManager.getUI(this);
        if (cui instanceof MetalComboBoxUI) {
            cui = new MetalDateComboBoxUI();
        } else if (cui instanceof MotifComboBoxUI) {
            cui = new MotifDateComboBoxUI();
        } else if (cui instanceof WindowsComboBoxUI) {
            cui = new WindowsDateComboBoxUI();
        }else cui = new WindowsDateComboBoxUI();
        setUI(cui);
    }

    // Inner classes are used purely to keep DateComboBox component in one file
    //////////////////////////////////////////////////////////////
    // UI Inner classes -- one for each supported Look and Feel
    //////////////////////////////////////////////////////////////
    class MetalDateComboBoxUI extends MetalComboBoxUI {

        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    class WindowsDateComboBoxUI extends WindowsComboBoxUI {

        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    class MotifDateComboBoxUI extends MotifComboBoxUI {

        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    //////////////////////////////////////////////////////////////
    // DatePopup inner class
    //////////////////////////////////////////////////////////////
    class DatePopup implements ComboPopup, MouseMotionListener,
            MouseListener, KeyListener, PopupMenuListener {

        protected JComboBox comboBox;
        protected Calendar calendar;
        protected JPopupMenu popup;
        protected JLabel monthLabel;
        protected JPanel days = null;
        protected SimpleDateFormat monthFormat = new SimpleDateFormat("MMM yyyy");

        protected Color selectedBackground;
        protected Color selectedForeground;
        protected Color background;
        protected Color foreground;

        public DatePopup(JComboBox comboBox) {
            this.comboBox = comboBox;
            calendar = Calendar.getInstance();
            //popup.setBounds(comboBox.getWidth(), comboBox.getWidth(), comboBox.getWidth(), comboBox.getWidth());
            // check Look and Fee
            //background = UIManager.getColor("ComboBox.background");
            //foreground = UIManager.getColor("ComboBox.foreground");
            //selectedBackground = UIManager.getColor("ComboBox.selectionBackground");
            //selectedForeground = UIManager.getColor("ComboBox.selectionForeground");
            background=Color.GREEN;
            foreground=Color.BLUE;
            selectedBackground=Color.orange;
            selectedForeground=Color.red;
            initializePopup();
        }

	//========================================
        // begin ComboPopup method implementations
        //
        public void show() {
            try {
                // if setSelectedItem() was called with a valid date, adjust the calendar
                calendar.setTime(dateFormat.parse(comboBox.getSelectedItem().toString()));
            } catch (Exception e) {
            }
            updatePopup();
            popup.show(comboBox, 0, comboBox.getHeight());
        }

        public void hide() {
            popup.setVisible(false);
        }

        protected JList list = new JList();

        public JList getList() {
            return list;
        }

        public MouseListener getMouseListener() {
            return this;
        }

        public MouseMotionListener getMouseMotionListener() {
            return this;
        }

        public KeyListener getKeyListener() {
            return this;
        }

        public boolean isVisible() {
            return popup.isVisible();
        }

        public void uninstallingUI() {
            popup.removePopupMenuListener(this);
        }

	//
        // end ComboPopup method implementations
        //======================================
	//===================================================================
        // begin Event Listeners
        //
	// MouseListener
        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        // something else registered for MousePressed

        public void mouseClicked(MouseEvent e) {
            if (!SwingUtilities.isLeftMouseButton(e)) {
                return;
            }
            if (!comboBox.isEnabled()) {
                return;
            }
            if (comboBox.isEditable()) {
                comboBox.getEditor().getEditorComponent().requestFocus();
            } else {
                comboBox.requestFocus();
            }
            togglePopup();
        }

        protected boolean mouseInside = false;

        public void mouseEntered(MouseEvent e) {
            mouseInside = true;
        }

        public void mouseExited(MouseEvent e) {
            mouseInside = false;
        }

        // MouseMotionListener
        public void mouseDragged(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }

        // KeyListener
        public void keyPressed(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE
                    || e.getKeyCode() == KeyEvent.VK_ENTER) {
                togglePopup();
            }
        }

        /**
         * Variables hideNext and mouseInside are used to hide the popupMenu by
         * clicking the mouse in the JComboBox
         */
        public void popupMenuCanceled(PopupMenuEvent e) {
        }
        protected boolean hideNext = false;

        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            hideNext = mouseInside;
        }

        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }

	//
        // end Event Listeners
        //=================================================================
	//===================================================================
        // begin Utility methods
        //
        protected void togglePopup() {
            if (isVisible() || hideNext) {
                hide();
            } else {
                show();
            }
            hideNext = false;
        }

	//
        // end Utility methods
        //=================================================================
        // Note *** did not use JButton because Popup closes when pressed
        protected JLabel createUpdateButton(final int field, final int amount) {
            final JLabel label = new JLabel();
            final Border selectedBorder = new EtchedBorder();
            final Border unselectedBorder = new EmptyBorder(selectedBorder.getBorderInsets(new JLabel()));
            label.setBorder(unselectedBorder);
            label.setForeground(foreground);
            label.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    calendar.add(field, amount);
                    updatePopup();
                }

                public void mouseEntered(MouseEvent e) {
                    label.setBorder(selectedBorder);
                }

                public void mouseExited(MouseEvent e) {
                    label.setBorder(unselectedBorder);
                }
            });
            return label;
        }

        protected void initializePopup() {
            JPanel header = new JPanel(); // used Box, but it wasn't Opaque
            header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
            header.setBackground(background);
            header.setOpaque(true);

            JLabel label;
            label = createUpdateButton(Calendar.YEAR, -1);
            label.setText("<<");
            label.setToolTipText("Previous Year");
            label.setBackground(Color.BLUE);
            
            header.add(Box.createHorizontalStrut(12));
            header.add(label);
            header.add(Box.createHorizontalStrut(12));

            label = createUpdateButton(Calendar.MONTH, -1);
            label.setText("<");
            label.setToolTipText("Previous Month");
            header.add(label);

            monthLabel = new JLabel("", JLabel.CENTER);
            monthLabel.setForeground(foreground);
            header.add(Box.createHorizontalGlue());
            header.add(monthLabel);
            header.add(Box.createHorizontalGlue());

            label = createUpdateButton(Calendar.MONTH, 1);
            label.setText(">");
            label.setToolTipText("Next Month");
            header.add(label);

            label = createUpdateButton(Calendar.YEAR, 1);
            label.setText(">>");
            label.setToolTipText("Next Year");
            label.setBackground(Color.BLUE);

            header.add(Box.createHorizontalStrut(12));
            header.add(label);
            header.add(Box.createHorizontalStrut(12));

            popup = new JPopupMenu();
            popup.setBorder(BorderFactory.createLineBorder(Color.blue));
            popup.setLayout(new BorderLayout());
            popup.setBackground(Color.yellow);
            popup.addPopupMenuListener(this);
            popup.setBounds(0, 0, 100, 100);
            popup.add(BorderLayout.NORTH, header);
        }

        // update the Popup when either the month or the year of the calendar has been changed
        protected void updatePopup() {
            monthLabel.setText(monthFormat.format(calendar.getTime()));
            if (days != null) {
                popup.remove(days);
            }
            days = new JPanel(new GridLayout(0, 7));
            days.setBackground(Color.white);
            days.setOpaque(true);

            Calendar setupCalendar = (Calendar) calendar.clone();
            setupCalendar.set(Calendar.DAY_OF_WEEK, setupCalendar.getFirstDayOfWeek());
            for (int i = 0; i < 7; i++) {
                int dayInt = setupCalendar.get(Calendar.DAY_OF_WEEK);
                JLabel label = new JLabel();
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setForeground(Color.red);
                if (dayInt == Calendar.SUNDAY) {
                    label.setText("S");
                } else if (dayInt == Calendar.MONDAY) {
                    label.setText("M");
                } else if (dayInt == Calendar.TUESDAY) {
                    label.setText("T");
                } else if (dayInt == Calendar.WEDNESDAY) {
                    label.setText("W");
                } else if (dayInt == Calendar.THURSDAY) {
                    label.setText("T");
                } else if (dayInt == Calendar.FRIDAY) {
                    label.setText("F");
                } else if (dayInt == Calendar.SATURDAY) {
                    label.setText("S");
                }
                
                days.add(label);
                setupCalendar.roll(Calendar.DAY_OF_WEEK, true);
            }

            setupCalendar = (Calendar) calendar.clone();
            setupCalendar.set(Calendar.DAY_OF_MONTH, 1);
            int first = setupCalendar.get(Calendar.DAY_OF_WEEK);
            for (int i = 0; i < (first - 1); i++) {
                days.add(new JLabel(""));
            }
            for (int i = 1; i <= setupCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                final int day = i;
                final JLabel label = new JLabel(String.valueOf(day));
                
                 label.setForeground(foreground);
                //System.out.println("currrent date: "+zz);
                label.setHorizontalAlignment(JLabel.CENTER);
                
                label.addMouseListener(new MouseListener() {
                    public void mousePressed(MouseEvent e) {
                    }

                    public void mouseClicked(MouseEvent e) {
                    }

                    public void mouseReleased(MouseEvent e) {
                        label.setOpaque(false);
                        label.setBackground(background);
                        label.setForeground(foreground);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        comboBox.setSelectedItem(dateFormat.format(calendar.getTime()));
			    // hide();
                        // hide is called with setSelectedItem() ... removeAll()
                        comboBox.requestFocus();
                    }

                    public void mouseEntered(MouseEvent e) {
                        label.setOpaque(true);
                        label.setBackground(selectedBackground);
                        label.setForeground(selectedForeground);
                    }

                    public void mouseExited(MouseEvent e) {
                        label.setOpaque(false);
                        label.setBackground(background);
                        label.setForeground(foreground);
                    }
                });
                /*DateFormat dFormat = new SimpleDateFormat("dd");
                Date date = new Date();
                String cDate=dFormat.format(date);
                if(!cDate.equals(String.valueOf(day)))label.setBackground(Color.GREEN);*/
                days.add(label);
            }

            popup.add(BorderLayout.CENTER, days);
            popup.pack();
        }
    }

    //////////////////////////////////////////////////////////////
    // This is only included to provide a sample GUI
    //////////////////////////////////////////////////////////////
    public static void main(String args[]) {
        JFrame f = new JFrame();
        Container c = f.getContentPane();
        c.setLayout(new FlowLayout());
        c.add(new JLabel("Date 1:"));
        c.add(new DateComboBox());
        c.add(new JLabel("Date 2:"));
        DateComboBox dcb = new DateComboBox();
        dcb.setEditable(true);
        c.add(dcb);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setSize(500, 200);
        f.show();
    }

}