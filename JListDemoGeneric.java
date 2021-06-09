/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionListener;

/* ListDemo.java requires no other files. */
public class JListDemoGeneric extends JPanel implements ActionListener {
    
    // define a JList and a DefaultListModel
    JList<String> list;
    DefaultListModel<String> listModel;

    //Add and Remove
    JButton remove, add;
    JTextField text;

    public JListDemoGeneric() {
        super(new BorderLayout());

        // create initial listModel
        listModel = new DefaultListModel<>();
        listModel.addElement("Raquel Acedo");
        listModel.addElement("Raúl Acedo");
        listModel.addElement("Jose Luis Acedo");
        listModel.addElement("Miriam Benedicto");

        //Create the list and put it in a scroll pane.
        list = new JList<>(listModel);
        JScrollPane listScrollPane = new JScrollPane(list);
        //list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //list.setLayoutOrientation(JList.VERTICAL);
        //list.setVisibleRowCount(10);

        add(listScrollPane, BorderLayout.CENTER);

        //Add and Remove
        add = new JButton("Add");
        remove = new JButton("Remove");
        text = new JTextField(10);

        JPanel addRemove = new JPanel(); //new GridLayout(1, 3) -- tamaño constante
        addRemove.setLayout(new BoxLayout(addRemove, BoxLayout.LINE_AXIS));

        addRemove.add(text);
        addRemove.add(add);
        addRemove.add(remove);

        add(addRemove, BorderLayout.PAGE_END);

        text.addActionListener(this);
        add.addActionListener(this);
        remove.addActionListener(this);

        /*add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if(list.contains(text.getText()) == false) {
                    int index = list.getSelectedIndex();
                    if(index == -1) {
                        index = 0;
                    } else {
                        index++;
                    }
                    list.insertElementAt(text.getText(), index);
                    listModel.addElement(text.getText());
                }
            }
        });*/
        
    }

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        String text = text.getText();

        //Remove
        if(source == remove) {
            for(int i=0; i<listModel.size(); i++) {
                if(listModel.get(i).contains(text)) {
                    listModel.remove(i);
                    break;
                }
            }
        } else {
            for(int i=0; i<listModel.size(); i++) {
                if(listModel.get(i).equals(text)) {
                    break;
                }
            }
            if(i == listModel.size()) {
                listModel.addElement(text);
            }
        }
        int indexR = list.getSelectedIndex();
        listModel.remove(indexR);

        int size = listModel.getSize();

        if(size == 0) {
            remove.setEnabled(false);
        } else {
            if(indexR == size) { //listModel.getSize()
                indexR--;
            }
            list.setSelectedIndex(indexR);
            list.ensureIndexIsVisible(indexR);
        }
        //Add
        String name = text.getText();

        if(name.equals("") || list.alreadyInList(name)) {
            Toolkit.getDefaultToolkit().beep();
            text.requestFocusInWindow();
            text.selectAll();
            return;
        }

        int indexA = list.getSelectedIndex();

        if(indexA == -1) {
            indexA = 0;
        } else {
            indexA++;
        }
        
        listModel.insertElementAt(text.getText(), indexA);

        text.requestFocusInWindow();
        text.setText("");

        list.setSelectedIndex(indexA);
        list.ensureIndexIsVisible(indexA);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Set the look and feel.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);
        
        //Create and set up the window.
        JFrame frame = new JFrame("List Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        

        //Create and set up the content pane.
        //Forma 1
        JListDemoGeneric list = new JListDemoGeneric();
        frame.getContentPane().add(list);
        //Forma 2
        //frame.setContentPane(new JListDemoGeneric());
        //Forma 3
        /*JComponent newContentPane = new JLIstDemoGeneric();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);*/


        //Display the window.
        frame.setSize(new Dimension(400, 400));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}