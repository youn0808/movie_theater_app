package comp3350.sceneit.presentation;

public interface IMovieClickListener {
    /*
    Interface that listen to item (movie) click in the
    RecyclerView and also keep track of the which recyclerView is
    clicked
 */
    void movieClick(int position, int carouselViewType);
}
