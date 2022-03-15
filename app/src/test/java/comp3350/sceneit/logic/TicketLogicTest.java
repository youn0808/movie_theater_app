package comp3350.sceneit.logic;

import org.junit.Test;

import comp3350.sceneit.data.DatabaseManager;
import comp3350.sceneit.data.Movie;
import comp3350.sceneit.data.PostgresDatabaseManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;



public class TicketLogicTest {
    @Test
    public void ticketPriceCalculation(){
        assertEquals(StandardTicketLogic.totalOrderPrice(5),50);
        assertEquals(PremiumTicketLogic.totalOrderPrice(5),75);

    }

    @Test
    public void totalTicketPriceCalculation(){
        assertEquals(StandardTicketLogic.getTicketPrice(),10);
        assertEquals(PremiumTicketLogic.getTicketPrice(),15);
    }

    @Test
    public void ticketOverageCalculation(){
        assertTrue(StandardTicketLogic.ticketOverage(11));
        assertFalse(StandardTicketLogic.ticketOverage(9));
        assertFalse(StandardTicketLogic.ticketOverage(10));

        assertTrue(StandardTicketLogic.ticketOverage(11));
        assertFalse(StandardTicketLogic.ticketOverage(9));
        assertFalse(StandardTicketLogic.ticketOverage(10));
    }

}
