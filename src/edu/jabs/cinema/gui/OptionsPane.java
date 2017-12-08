package edu.jabs.cinema.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * Pane which contains the buttons for the extension points
 */
public class OptionsPane extends JPanel implements ActionListener
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * Command for the option 1
     */
    private static final String OPTION_1 = "Option1";

    /**
     * Command for the option 2
     */
    private static final String OPTION_2 = "Option2";


    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * A reference of the main class of the GUI
     */
    private CinemaGUI main;

    // -----------------------------------------------------------------
    // GUI Attributes
    // -----------------------------------------------------------------

    /**
     * Button to execute the extension point 1
     */
    public JButton option1Button;

    /**
     * Button to execute the extension point 2
     */
    public JButton option2Button;


    // -----------------------------------------------------------------
    // Constructor Methods
    // -----------------------------------------------------------------
    /**
     * Constructs the pane
     * @param mainWindow is a reference of the main class of the GUI
     */
    public OptionsPane( CinemaGUI mainWindow )
    {
        main = mainWindow;

        option1Button = new JButton( "Option 1" );
        option1Button.setActionCommand( OPTION_1 );
        option1Button.addActionListener( this );
        add( option1Button );

        option2Button = new JButton( "Option 2" );
        option2Button.setActionCommand( OPTION_2 );
        option2Button.addActionListener( this );
        add( option2Button );



    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Executes the actions related to the events
     * @param event is the event of the click over a button
     */
    public void actionPerformed( ActionEvent event )
    {
        String command = event.getActionCommand( );

        if( OPTION_1.equals( command ) )
        {
            main.reqFuncOption1( );
        }
        else if( OPTION_2.equals( command ) )
        {
            main.reqFuncOption2( );
        }

    }

}