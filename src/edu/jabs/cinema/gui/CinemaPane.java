package edu.jabs.cinema.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import edu.jabs.cinema.domain.*;

/**
 * Displays the cinema
 */
public class CinemaPane extends JPanel
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * Color available seats in the LOW_ROWS section (front)
     */
    private static final Color COLOR_AVAILABLE_LOW_ROWS = Color.WHITE;

    /**
     * Color  available seats in the UPPER_ROWS section (preferential)
     */
    private static final Color COLOR_AVAILABLE_UPPER_ROWS = Color.LIGHT_GRAY;

    /**
     * Color booked seats
     */
    private static final Color COLOR_BOOKED = Color.YELLOW;

    /**
     * Color sold seat
     */
    private static final Color COLOR_SOLD = Color.RED;

    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Seats in the cinema
     */
    private Seat[] seats;

    // -----------------------------------------------------------------
    // Attributes of the GUI
    // -----------------------------------------------------------------

    /**
     * Labels of the seats
     */
    private JLabel[] seatsLabels;

    /**
     * Label of the screen
     */
    private JLabel screenLabel;

    // -----------------------------------------------------------------
    // Constructor Methods
    // -----------------------------------------------------------------

    /**
     * Constructs the pane with the seats
     * @param seatsS Seats in the cinema
     */
    public CinemaPane( Seat[] seatsS )
    {
        seats = seatsS;
        setLayout( new GridBagLayout( ) );
        setBorder( new TitledBorder( "Cinema" ) );

        // Paint the seats
        seatsLabels = new JLabel[seats.length];
        int row = 2;
        int number = 0;
        for( int i = 0; i < seats.length; i++ )
        {
            Seat seat = seats[ i ];
            if( seat.getNumber( ) < 10 )
            {
                seatsLabels[ i ] = new JLabel( Character.toString( seat.getRow( ) ) + "0" + Integer.toString( seat.getNumber( ) ) );
            }
            else
            {
                seatsLabels[ i ] = new JLabel( Character.toString( seat.getRow( ) ) + Integer.toString( seat.getNumber( ) ) );
            }
            seatsLabels[ i ].setBackground( getSeatColor( seat ) );
            seatsLabels[ i ].setFont( seatsLabels[ i ].getFont( ).deriveFont( ( float )10 ) );
            seatsLabels[ i ].setPreferredSize( new Dimension( 22, 22 ) );
            seatsLabels[ i ].setBorder( new LineBorder( Color.GRAY ) );
            seatsLabels[ i ].setOpaque( true );
            seatsLabels[ i ].setHorizontalAlignment( JLabel.CENTER );

            //Locates the seats
            GridBagConstraints position = new GridBagConstraints( number, row, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 1, 1, 1, 1 ), 0, 0 );
            add( seatsLabels[ i ], position );
            number++;
            if( number == Cinema.SEATS_PER_ROW )
            {
                row++;
                number = 0;
            }
        }

        // Paints the screen
        screenLabel = new JLabel( "SCREEN" );
        screenLabel.setHorizontalAlignment( JLabel.CENTER );
        screenLabel.setBackground( Color.CYAN );
        screenLabel.setOpaque( true );
        screenLabel.setBorder( new LineBorder( Color.GRAY ) );
        screenLabel.setPreferredSize( new Dimension( ( Cinema.SEATS_PER_ROW - 4 ) * 22, 30 ) );
        GridBagConstraints position = new GridBagConstraints( 2, 0, ( Cinema.SEATS_PER_ROW - 4 ), 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 5, 5, 50, 5 ), 0, 0 );
        add( screenLabel, position );
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Repaints the seats of the cinema
     */
    public void repaintP( )
    {
        for( int i = 0; i < seats.length; i++ )
        {
            Seat seat = seats[ i ];
            seatsLabels[ i ].setBackground( getSeatColor( seat ) );
        }
    }

    /**
     * Returns the color of the specified seat
     * @param seat Seat which color is being calculated
     * @return Color of the seat
     */
    private Color getSeatColor( Seat seat )
    {
        if( seat.isAvailable( ) )
        {
            if( seat.isLowerSeat( ) )
            {
                return COLOR_AVAILABLE_LOW_ROWS;
            }
            else
            {
                return COLOR_AVAILABLE_UPPER_ROWS;
            }
        }
        else if( seat.isBooked( ) )
        {
            return COLOR_BOOKED;
        }
        else
        {
            return COLOR_SOLD;
        }
    }
}
