package edu.jabs.cinema.gui;

import java.awt.*;

import javax.swing.*;

import edu.jabs.cinema.domain.*;

/**
 * This is the main class of the GUI.
 */
public class CinemaGUI extends JFrame
{
    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Main class of the domain model
     */
    private Cinema cinema;

    /**
     * Current reservation that manages the GUI
     */
    private Reservation currentReservation;

    // -----------------------------------------------------------------
    // Attributes of the GUI
    // -----------------------------------------------------------------

    /**
     * Pane with the data of the reservation
     */
    private ReservationPane reservationPane;

    /**
     * Pane for the management of the cards
     */
    private CardsPane cardsPane;

    /**
     * Pane of the extensions
     */
    private OptionsPane optionsPane;

    /**
     * Pane where the cinema is displayed
     */
    private CinemaPane cinemaPane;

    /**
     * Pane with the image of the cinema
     */
    private ImagePane imagePane;

    // -----------------------------------------------------------------
    // Constructor Methods
    // -----------------------------------------------------------------

    /**
     * Creates the main class of the GUI
     */
    public CinemaGUI( )
    {
        // Creates the main class of the domain
        cinema = new Cinema( );
        currentReservation = new Reservation( );

        // Organizes the main pane
        setLayout( new BorderLayout( ) );
        setTitle( "Management Cinema" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        // Cards an reservations pane
        cardsPane = new CardsPane( this );
        reservationPane = new ReservationPane( this, cinema );
        JPanel contLeft = new JPanel( new BorderLayout( ) );
        contLeft.add( reservationPane, BorderLayout.CENTER );
        contLeft.add( cardsPane, BorderLayout.SOUTH );
        add( contLeft, BorderLayout.WEST );

        // Cinema and extensions pane
        cinemaPane = new CinemaPane( cinema.getSeats( ) );
        optionsPane = new OptionsPane( this );
        JPanel contRight = new JPanel( new BorderLayout( ) );
        contRight.add( cinemaPane, BorderLayout.CENTER );
        contRight.add( optionsPane, BorderLayout.SOUTH );
        add( contRight, BorderLayout.EAST );

        // Image pane
        imagePane = new ImagePane( );
        add( imagePane, BorderLayout.NORTH );
        pack( );
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Creation of a new card in the system
     */
    public void createCard( )
    {
        String idStr = JOptionPane.showInputDialog( this, "Id of the customer:", "Id", JOptionPane.QUESTION_MESSAGE );
        if( idStr != null )
        {
            try
            {
                int id = Integer.parseInt( idStr );
                cinema.createCard( id );
                JOptionPane.showMessageDialog( this, "The card was created. Balance: " + cinema.getCardBalance( id ), "OK", JOptionPane.INFORMATION_MESSAGE );
            }
            catch( NumberFormatException e )
            {
                JOptionPane.showMessageDialog( this, "Id should be numeric", "Error", JOptionPane.ERROR_MESSAGE );
            }
            catch( Exception e )
            {
                JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    /**
     * Tops up a card in the system
     */
    public void topUpCard( )
    {
        String idStr = JOptionPane.showInputDialog( this, "Id of the customer:", "Id", JOptionPane.QUESTION_MESSAGE );
        if( idStr != null )
        {
            try
            {
                int id = Integer.parseInt( idStr );
                cinema.topUpCard( id );
                JOptionPane.showMessageDialog( this, "The card was topped up. New balance: " + cinema.getCardBalance( id ), "OK", JOptionPane.INFORMATION_MESSAGE );
            }
            catch( NumberFormatException e )
            {
                JOptionPane.showMessageDialog( this, "Id should be numeric", "Error", JOptionPane.ERROR_MESSAGE );
            }
            catch( Exception e )
            {
                JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    /**
     * Cancels the current reservation
     */
    public void cancelReservation( )
    {
        int result = JOptionPane.showConfirmDialog( this, "The current reservation will be deleted. Are you sure?", "New", JOptionPane.YES_NO_OPTION );
        if( result == JOptionPane.YES_OPTION )
        {
            // Cancels the reservation
            cinema.cancelReservation( currentReservation );
            currentReservation = new Reservation( );

            // Repaints
            reservationPane.repaintP( currentReservation );
            cinemaPane.repaintP( );
        }
    }

    /**
     * Pays the current reservation with a card and creates a new reservation
     */
    public void payCard( )
    {
        String idStr = JOptionPane.showInputDialog( this, "Id of the customer:", "Id", JOptionPane.QUESTION_MESSAGE );
        if( idStr != null )
        {
            try
            {
                int id = Integer.parseInt( idStr );
                cinema.payCardReservation( currentReservation, id );
                JOptionPane.showMessageDialog( this, "The reservation was paid off. New balance: " + cinema.getCardBalance( id ), "OK", JOptionPane.INFORMATION_MESSAGE );
                currentReservation = new Reservation( );
                reservationPane.repaintP( currentReservation );
                cinemaPane.repaintP( );
            }
            catch( NumberFormatException e )
            {
                JOptionPane.showMessageDialog( this, "Id should be numeric", "Error", JOptionPane.ERROR_MESSAGE );
            }
            catch( Exception e )
            {
                JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
    }

    /**
     * Pays the current reservation with cash and creates a new reservation
     */
    public void payCash( )
    {
        try
        {
            cinema.payReservationCash( currentReservation );
            JOptionPane.showMessageDialog( this, "The reservation was paid off. Total Paid: " + currentReservation.getSumReservation( ), "OK", JOptionPane.INFORMATION_MESSAGE );
            currentReservation = new Reservation( );
            reservationPane.repaintP( currentReservation );
            cinemaPane.repaintP( );
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Loads a reservation given an id number
     */
    public void load( )
    {
        int result = JOptionPane.showConfirmDialog( this, "This transaction will delete the current reservation if it has not been stored.\r\n Do you wish to continue?", "Confirmation", JOptionPane.YES_NO_OPTION );
        if( result == JOptionPane.YES_OPTION )
        {
            String idStr = JOptionPane.showInputDialog( this, "Id of the customer:", "Id", JOptionPane.QUESTION_MESSAGE );
            if( idStr != null )
            {
                try
                {
                    int id = Integer.parseInt( idStr );
                    Reservation reservationTemp = cinema.getReservation( id );
                    if( !cinema.isSaved( currentReservation ) )
                    {
                        currentReservation.cancel( );
                    }
                    currentReservation = reservationTemp;
                    reservationPane.repaintP( currentReservation );
                    cinemaPane.repaintP( );
                }
                catch( NumberFormatException e )
                {
                    JOptionPane.showMessageDialog( this, "Id should be numeric", "Error", JOptionPane.ERROR_MESSAGE );
                }
                catch( Exception e )
                {
                    JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
                }
            }
        }
    }

    /**
     * Stores the current reservation in the system
     */
    public void store( )
    {
        if( !cinema.isSaved( currentReservation ) )
        {
            String idStr = JOptionPane.showInputDialog( this, "Id of the customer:", "Id", JOptionPane.QUESTION_MESSAGE );
            if( idStr != null )
            {
                try
                {
                    int id = Integer.parseInt( idStr );
                    cinema.saveReservation( id, currentReservation );
                    JOptionPane.showMessageDialog( this, "The reservation was stored.", "OK", JOptionPane.INFORMATION_MESSAGE );
                    currentReservation = new Reservation( );
                    reservationPane.repaintP( currentReservation );
                    cinemaPane.repaintP( );
                }
                catch( NumberFormatException e )
                {
                    JOptionPane.showMessageDialog( this, "Id should be numeric", "Error", JOptionPane.ERROR_MESSAGE );
                }
                catch( Exception e )
                {
                    JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
                }
            }
        }
        else
        {
            JOptionPane.showMessageDialog( this, "The reservation is already stored in the system.", "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    /**
     * Ads a seat to the reservation
     * @param row Row of the seat
     * @param number Number of the seat within the row
     */
    public void addSeat( char row, int number )
    {
        try
        {
            Seat seat = cinema.getSeat( row, number );
            currentReservation.addSeat( seat );
            reservationPane.repaintP( currentReservation );
            cinemaPane.repaintP( );
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
    }

    // -----------------------------------------------------------------
    // Extension Methods
    // -----------------------------------------------------------------

    /**
     * Asks for the calculation of a result
     */
    public void reqFuncOption1( )
    {
        String result = cinema.method1( );
        JOptionPane.showMessageDialog( this, result, "Answer", JOptionPane.INFORMATION_MESSAGE );
    }

    /**
     * Asks for the calculation of a result
     */
    public void reqFuncOption2( )
    {
        String result = cinema.method2( );
        JOptionPane.showMessageDialog( this, result, "Answer", JOptionPane.INFORMATION_MESSAGE );
    }
    // -----------------------------------------------------------------
    // Main program
    // -----------------------------------------------------------------

    /**
     * This method executes the application, creating a new GUI.
     * @param args. No parameters are required
     */
    public static void main( String[] args )
    {
        CinemaGUI gui = new CinemaGUI( );
        gui.setVisible( true );
    }
}