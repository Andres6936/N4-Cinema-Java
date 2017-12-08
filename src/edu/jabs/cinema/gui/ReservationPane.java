package edu.jabs.cinema.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import edu.jabs.cinema.domain.*;

/**
 * Pane for the management of the reservation's data
 */
public class ReservationPane extends JPanel implements ActionListener
{
    // -----------------------------------------------------------------
    // Constants
    // -----------------------------------------------------------------

    /**
     * Command add seat
     */
    private static final String ADD = "ADD";

    /**
     * Command pay cash
     */
    private static final String PAY_CASH = "PAY_CASH";

    /**
     * Command pay with card
     */
    private static final String PAY_CARD = "PAY_CARD ";

    /**
     * Command cancel
     */
    private static final String CANCEL = "CANCEL";

    /**
     * Command store reservation
     */
    private static final String STORE = "STORE";

    /**
     * Command load reservation
     */
    private static final String LOAD = "LOAD";

    /**
     * Command change type of seat
     */
    private static final String CHANGE_TYPE = "CHANGE_TYPE";

    /**
     * Comando chage type of row
     */
    private static final String CHANGE_ROW = "CHANGE_ROW";

    // -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------

    /**
     * Main class of the GUI
     */
    private CinemaGUI main;

    /**
     * Cinema the information is retrieved from
     */
    private Cinema cinema;

    // -----------------------------------------------------------------
    // Attributes of the GUI
    // -----------------------------------------------------------------

    /**
     * Type of seat
     */
    private JComboBox cboTypesSeat;

    /**
     * Row of the seat
     */
    private JComboBox cboRow;

    /**
     * Number of the seat within the row
     */
    private JComboBox cboNumber;

    /**
     * Buton add seat
     */
    private JButton btnAddSeat;

    /**
     * Button pay in cash
     */
    private JButton btnPayCash;

    /**
     * Button pay with card
     */
    private JButton btnPayCard;

    /**
     * Button Cancel
     */
    private JButton btnCancel;

    /**
     * Button store
     */
    private JButton btnStore;

    /**
     * Button load reservation
     */
    private JButton btnLoad;

    /**
     * List of seats that have been booked
     */
    private JList lstSeats;

    /**
     * List Scroll
     */
    private JScrollPane scrollList;

    /**
     * Booked seats label
     */
    private JLabel bookedSeatsLabel;

    /**
     * Select seat label
     */
    private JLabel selectSeatLabel;

    /**
     * Total amount of Money
     */
    private JLabel moneyLabel;

    // -----------------------------------------------------------------
    // Constructor methods
    // -----------------------------------------------------------------

    /**
     * Constructors the pane
     * @param mainM Main class
     * @param cinemaC Cinema
     */
    public ReservationPane( CinemaGUI mainM, Cinema cinemaC )
    {
        main = mainM;
        cinema = cinemaC;

        setLayout( new GridBagLayout( ) );

        // Total amount of money
        moneyLabel = new JLabel( "Total amount in cash : $0" );

        // Combo Number
        cboNumber = new JComboBox( );
        cboNumber.setPreferredSize( new Dimension( 150, 20 ) );

        // Combo Row
        cboRow = new JComboBox( );
        cboRow.setActionCommand( CHANGE_ROW );
        cboRow.addActionListener( this );
        cboRow.setPreferredSize( new Dimension( 150, 20 ) );

        // Combo Type
        cboTypesSeat = new JComboBox( );
        cboTypesSeat.setActionCommand( CHANGE_TYPE );
        cboTypesSeat.addActionListener( this );
        cboTypesSeat.addItem( "General(Lower section)" );
        cboTypesSeat.addItem( "Preferential(Upper section)" );
        cboTypesSeat.setPreferredSize( new Dimension( 180, 20 ) );

        // Button add seat
        btnAddSeat = new JButton( "Add seat" );
        btnAddSeat.setActionCommand( ADD );
        btnAddSeat.addActionListener( this );
        btnAddSeat.setPreferredSize( new Dimension( 140, 25 ) );

        // Button pay in cash
        btnPayCash = new JButton( "Pay in cash" );
        btnPayCash.setActionCommand( PAY_CASH );
        btnPayCash.addActionListener( this );
        btnPayCash.setPreferredSize( new Dimension( 140, 25 ) );

        // Button pay with card
        btnPayCard = new JButton( "Pay with card" );
        btnPayCard.setActionCommand( PAY_CARD );
        btnPayCard.addActionListener( this );
        btnPayCard.setPreferredSize( new Dimension( 140, 25 ) );

        // Button Cancel
        btnCancel = new JButton( "Delete" );
        btnCancel.setActionCommand( CANCEL );
        btnCancel.addActionListener( this );
        btnCancel.setPreferredSize( new Dimension( 140, 25 ) );

        // Button Store reservation
        btnStore = new JButton( "Store reservation" );
        btnStore.setActionCommand( STORE );
        btnStore.addActionListener( this );
        btnStore.setPreferredSize( new Dimension( 140, 25 ) );

        // Button load reservation
        btnLoad = new JButton( "Load reservation" );
        btnLoad.setActionCommand( LOAD );
        btnLoad.addActionListener( this );
        btnLoad.setPreferredSize( new Dimension( 140, 25 ) );

        // List of booked seats
        lstSeats = new JList( );
        lstSeats.setAutoscrolls( true );
        scrollList = new JScrollPane( lstSeats );
        scrollList.setPreferredSize( new Dimension( 120, 100 ) );

        // Labels
        bookedSeatsLabel = new JLabel( "Booked seats:" );
        selectSeatLabel = new JLabel( "Select a seat:" );

        // Adds the elements

        JPanel moneyPane = new JPanel( );
        moneyPane.setBorder( new TitledBorder( "Money" ) );
        moneyPane.add( moneyLabel );

        GridBagConstraints position = new GridBagConstraints( 0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 3, 3, 3, 3 ), 0, 0 );
        add( moneyPane, position );

        // Seats pane
        JPanel seatsPane = new JPanel( new GridBagLayout( ) );
        seatsPane.setBorder( new TitledBorder( "Reservation seats" ) );

        position = new GridBagConstraints( 0, 0, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 3, 3, 3, 3 ), 0, 0 );
        seatsPane.add( selectSeatLabel, position );

        position = new GridBagConstraints( 0, 1, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 3, 3, 3, 3 ), 0, 0 );
        seatsPane.add( cboTypesSeat, position );

        position = new GridBagConstraints( 0, 3, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 3, 3, 3, 3 ), 0, 0 );
        seatsPane.add( cboRow, position );

        position = new GridBagConstraints( 0, 5, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 3, 3, 3, 3 ), 0, 0 );
        seatsPane.add( cboNumber, position );

        position = new GridBagConstraints( 2, 7, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 3, 3, 3, 3 ), 0, 0 );
        seatsPane.add( btnAddSeat, position );

        position = new GridBagConstraints( 2, 0, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 3, 3, 3, 3 ), 0, 0 );
        seatsPane.add( bookedSeatsLabel, position );

        position = new GridBagConstraints( 2, 1, 2, 6, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 3, 3, 3, 3 ), 0, 0 );
        seatsPane.add( scrollList, position );

        position = new GridBagConstraints( 0, 1, 1, 4, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 3, 3, 3, 3 ), 0, 0 );
        add( seatsPane, position );

        // Panel Reservation
        JPanel reservationPane = new JPanel( new GridBagLayout( ) );
        reservationPane.setBorder( new TitledBorder( "Reservation" ) );

        position = new GridBagConstraints( 0, 8, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 3, 3, 3, 3 ), 0, 0 );
        reservationPane.add( btnCancel, position );

        position = new GridBagConstraints( 1, 8, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 3, 3, 3, 3 ), 0, 0 );
        reservationPane.add( btnStore, position );

        position = new GridBagConstraints( 2, 8, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 3, 3, 3, 3 ), 0, 0 );
        reservationPane.add( btnLoad, position );

        position = new GridBagConstraints( 0, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.CENTER, new Insets( 3, 3, 3, 3 ), 0, 0 );
        add( reservationPane, position );

        // Panel Payments
        JPanel paymentsPane = new JPanel( new GridLayout( 1, 2 ) );
        paymentsPane.setBorder( new TitledBorder( "Pay reservation" ) );

        paymentsPane.add( btnPayCash );

        paymentsPane.add( btnPayCard );

        position = new GridBagConstraints( 0, 6, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets( 3, 3, 3, 3 ), 0, 0 );
        add( paymentsPane, position );
    }

    // -----------------------------------------------------------------
    // Methods
    // -----------------------------------------------------------------

    /**
     * Repaints the reservations
     * @param currentReservation Current reservation
     */
    public void repaintP( Reservation currentReservation )
    {
        // Repaints the reservation
        lstSeats.removeAll( );
        ArrayList seats = currentReservation.getSeats( );
        String[] seatsList = new String[seats.size( )];
        for( int i = 0; i < seats.size( ); i++ )
        {
            Seat seat = ( Seat )seats.get( i );
            seatsList[ i ] = Character.toString( seat.getRow( ) ) + Integer.toString( seat.getNumber( ) );
        }
        lstSeats.setListData( seatsList );

        // Reloads seats
        reloadSeats( );

        // Total amount of money
        moneyLabel.setText( "Total amount in cash: $" + cinema.getTotalMoney( ) );
    }

    /**
     * Reloads rows
     */
    private void reloadRows( )
    {
        // Wipes all items away
        cboRow.removeAllItems( );
        cboNumber.removeAllItems( );

        // Selects the type of the seat
        String seatType;
        if( cboTypesSeat.getSelectedItem( ) != null )
        {
            if( cboTypesSeat.getSelectedItem( ).equals( "General(Lower section)" ) )
            {
                seatType = Seat.LOWER_SEAT;
            }
            else
            {
                seatType = Seat.UPPER_SEAT;
            }

            // Finds the seats and adds them
            char[] rows = cinema.getRows( seatType );
            for( int i = 0; i < rows.length; i++ )
            {
                cboRow.addItem( new Character( rows[ i ] ) );
            }
        }
    }

    /**
     * Reloads the seats for the selected type, and the selected row
     */
    private void reloadSeats( )
    {
        try
        {
            cboNumber.removeAllItems( );
            if( cboRow.getSelectedItem( ) != null )
            {
                char row = ( ( Character )cboRow.getSelectedItem( ) ).charValue( );
                ArrayList seats = cinema.getAvailableSeats( row );
                for( int i = 0; i < seats.size( ); i++ )
                {
                    Seat seat = ( Seat )seats.get( i );
                    cboNumber.addItem( new Integer( seat.getNumber( ) ) );
                }
            }
        }
        catch( Exception e )
        {
            JOptionPane.showMessageDialog( this, "Error while loading the seats: " + e.getMessage( ), "Error", JOptionPane.ERROR_MESSAGE );
        }
    }


    /**
     * Handles the events of the buttons
     * @param e Action that generated the event.
     */
    public void actionPerformed( ActionEvent e )
    {
        String command = e.getActionCommand( );

        if( CHANGE_TYPE.equals( command ) )
        {
            reloadRows( );
        }
        else if( CHANGE_ROW.equals( command ) )
        {
            reloadSeats( );
        }
        else if( ADD.equals( command ) )
        {
            try
            {
                char row = ( ( Character )cboRow.getSelectedItem( ) ).charValue( );
                int number = ( ( Integer )cboNumber.getSelectedItem( ) ).intValue( );
                main.addSeat( row, number );
            }
            catch( Exception e2 )
            {
                JOptionPane.showMessageDialog( this, "You should choose a valid seat.", "Error", JOptionPane.ERROR_MESSAGE );
            }
        }
        else if( PAY_CASH.equals( command ) )
        {
            main.payCash( );
        }
        else if( PAY_CARD.equals( command ) )
        {
            main.payCard( );
        }
        else if( CANCEL.equals( command ) )
        {
            main.cancelReservation( );
        }
        else if( STORE.equals( command ) )
        {
            main.store( );
        }
        else if( LOAD.equals( command ) )
        {
            main.load( );
        }
    }
}
