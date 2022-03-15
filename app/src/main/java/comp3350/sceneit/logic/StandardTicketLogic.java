package comp3350.sceneit.logic;

import comp3350.sceneit.data.Movie;

public final class StandardTicketLogic implements TicketLogicInterface {
    private StandardTicketLogic() {
    }

    private static int MAX_TICKET_NUM = 10;
    private static int TICKET_COST = 10;

    /**
     * Calculates cost of all selected movies in OrderActivity
     *
     * @param numOfTickets: num of tickets selected
     * @return cost of all selected movie tickets
     */
    public static int totalOrderPrice(int numOfTickets) {
        return numOfTickets * TICKET_COST;
    }

    /**
     * Calculates ticket price given a specific movie
     * @return $10...... for this iteration all movies cost $10.
     */
    public static int getTicketPrice() {
        return TICKET_COST;
    }

    public static int getMaxTickets(){
        return MAX_TICKET_NUM;
    }

    public static boolean ticketOverage(int ordered){
        return (ordered > MAX_TICKET_NUM);
    }
}
