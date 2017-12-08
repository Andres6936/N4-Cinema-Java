package edu.jabs.cinema.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * Pane for the management of the cards
 */
public class CardsPane extends JPanel implements ActionListener
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * Commmand create card
     */
    private static final String CREATE = "CREATE";

    /**
     * Command top up card
     */
    private static final String TOP_UP = "TOP_UP";

    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Main class of the GUI
     */
    private CinemaGUI main;

    // -----------------------------------------------------------------
    // Attributes of the GUI
    // -----------------------------------------------------------------

    /**
     * Button create card
     */
    private JButton bttnCreate;

    /**
     * Button top up card
     */
    private JButton bttnTopUp;

    // -----------------------------------------------------------------
    // Constructor Methods
    // -----------------------------------------------------------------

    /**
     * Constructs the pane
     * @param mainM Main class
     */
    public CardsPane( CinemaGUI mainM )
    {
        main = mainM;

        setBorder( new TitledBorder( "Cards Management" ) );
        setLayout( new GridLayout( 1, 2 ) );

        // Button create
        bttnCreate = new JButton( "Create card" );
        bttnCreate.setActionCommand( CREATE );
        bttnCreate.addActionListener( this );
        add( bttnCreate );

        // Button top up
        bttnTopUp = new JButton( "Top up card" );
        bttnTopUp.setActionCommand( TOP_UP );
        bttnTopUp.addActionListener( this );
        add( bttnTopUp );
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Handles the events of the buttons
     * @param e Action that generated the event.
     */
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );
        if( CREATE.equals( command ) )
        {
            main.createCard( );
        }
        else if( TOP_UP.equals( command ) )
        {
            main.topUpCard( );
        }
    }
}
