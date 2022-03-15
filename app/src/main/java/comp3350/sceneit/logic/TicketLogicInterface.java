package comp3350.sceneit.logic;

import comp3350.sceneit.data.Movie;

public interface TicketLogicInterface {

    /**
     * Calculates cost of all selected movies in OrderActivity
     * @return cost of all selected tickets
     */
    static int totalOrderPrice() {
        return 0;
    }

    /**
     * Calculates ticket price given a specific movie
     * @return return ticket cost given a movie .
     *
     */
    static int calculateTicketPrice() {
        return 0;
    }

    /**
     * Calculates ticket price given a specific movie
     * @return max tickets to be processed in one window
     */
    static int getMaxTickets() {
        return 0;
    }

}
