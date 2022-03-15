package comp3350.sceneit.data;

import android.os.Build;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StubAirings {
    private ArrayList<Airing> airings = new java.util.ArrayList<>();

    public StubAirings(Movie selectedMovie) {
        Date date = new java.util.Date();
        for (int i = 0; i < 30; i++) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Date targetDate =  addDays(date, i);
                    airings.add(new Airing((i), selectedMovie.movieId, 0, new java.sql.Date(targetDate.getTime()), 100, 100));
                }
        }
    }

    public ArrayList <Airing> getAirings(){
        return airings;
    }

    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            cal.setTime(date);
        }
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
