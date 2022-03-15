package comp3350.sceneit.presentation;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import comp3350.sceneit.R;
import comp3350.sceneit.data.Airing;
import comp3350.sceneit.data.exceptions.DatabaseAccessException;
import comp3350.sceneit.data.DatabaseManager;
import comp3350.sceneit.data.Movie;
import comp3350.sceneit.data.PostgresDatabaseManager;
import comp3350.sceneit.data.StubAirings;
import comp3350.sceneit.logic.StandardTicketLogic;

public class OrderActivity extends AppCompatActivity {


    //UI Variables
    private int price, numOfTickets;
    private Movie selectedMovie;
    private DatabaseManager dbm;
    private ArrayList<Airing> airings = new ArrayList<>();
    private ArrayList<Movie> movies = new ArrayList<>();
    private String selectedTheater = "NULL", selectedMovieTitle = "NULL", selectedMovieRating = "NULL";
    private String[] airingTimes = {};
    private StubAirings stubAirings;


    //UI XML linked Variables
    private ToggleButton[] airingButtons;
    private EditText eTCalender, eTNumOfTickets;
    private ImageView ivMovieImg;
    private TextView tvPrice, tvMovieTitle, tvTheatre, tvDescription;
    private Button orderButton;

    //constants
    private final int MAX_TICKETS = 10;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Bundle b = getIntent().getExtras();
        selectedMovieTitle = b.getString("movieTitle");
        selectedMovieRating = b.getString("movieRating");
        selectedTheater = b.getString("theater");


        //Set all variables representing UI XML components
        eTCalender = findViewById(R.id.editTextCalender);
        eTNumOfTickets = (EditText) findViewById(R.id.editTextNumberofTickets);
        tvPrice = (TextView) findViewById(R.id.textViewPrice);
        tvMovieTitle = (TextView) findViewById(R.id.textViewTitle);
        tvTheatre = (TextView) findViewById(R.id.textViewTheatre);
        ivMovieImg = (ImageView) findViewById(R.id.imageViewMovieImg);
        tvDescription = (TextView) findViewById(R.id.textViewDescription);
        orderButton = (Button) findViewById(R.id.buttonOrderTickets);

        //Initialize database manager
        dbm = new PostgresDatabaseManager();

        // Try to access database and grab selected movie, based on title.
        try {
            movies = dbm.getMovies();
            selectedMovie = getSelectedMovie(selectedMovieTitle);

        } catch (DatabaseAccessException e) {
            e.printStackTrace();
        }

        // Apply attributes of selected movie to the XML components
        String posterLocation = selectedMovie.getPoster_url();
        int imageResourceID = getResources().getIdentifier(posterLocation, null, getPackageName());
        Drawable moviePosterDrawable = getResources().getDrawable(imageResourceID);
        ivMovieImg.setImageDrawable(moviePosterDrawable);
        tvMovieTitle.setText(selectedMovie.getTitle());
        tvDescription.setText(selectedMovie.getDescription());
        //tvTheatre.setText("TEST CHANGE TO BUNDLE WHEN POSSIBLE");//TODO DELETE THIS
        tvTheatre.setText(selectedTheater);


        //Initialize calender and watcher for calender
        calenderHandler(eTCalender);

        //Initialize ticket number input handler and watchers for such.
        ticketInputHandler();

    }

    /**
     * Initializes ticket input handler. After text is changed in the number of tickets input (eTNumOfTickets),
     * this method updates the display for the overall cost of the ticket by making calls to the logic layer.
     * This method also calls checkOrderButtonConditions(), as those conditions may change over the course of execution
     */
    private void ticketInputHandler() {
       // eTNumOfTickets.setRawInputType(Configuration.KEYBOARD_12KEY);
        eTNumOfTickets.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        TextWatcher textWatcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    numOfTickets = Integer.parseInt(s.toString());

                    if (StandardTicketLogic.ticketOverage(numOfTickets)) {
                        eTNumOfTickets.removeTextChangedListener(this);
                        numOfTickets = StandardTicketLogic.getMaxTickets();
                        s.replace(0,s.length(),Integer.toString(numOfTickets));
                        eTNumOfTickets.addTextChangedListener(this);
                    }

                } catch (NumberFormatException e) {
                    numOfTickets = 0;
                }
                price = StandardTicketLogic.totalOrderPrice(numOfTickets);
                tvPrice.setText("$" + StandardTicketLogic.totalOrderPrice(numOfTickets));
                if (airingButtons != null)
                    checkOrderButtonConditions();
            }
        };
        eTNumOfTickets.addTextChangedListener(textWatcher1);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    /**
     * calenderHandler handles the Calender widget. After a date has been set,. ie. onDateSet(),
     * this method calls to getAirings and intitialize airings buttons.
     *
     * @param eText
     */
    protected void calenderHandler(EditText eText) {
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener((View.OnClickListener) v -> {
            eText.setEnabled(false);
            LinearLayout ll = (LinearLayout) findViewById(R.id.toggleButtons);
            ll.removeAllViews();
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);
            // date picker dialog
                DatePickerDialog picker = new DatePickerDialog(OrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Date date = cldr.getTime();
                    eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    TextView tv = (TextView) findViewById(R.id.textViewShowings);
                    tv.setText("Showings for " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        eText.setShowSoftInputOnFocus(false);
                    }
                    eText.setInputType(InputType.TYPE_NULL);
                    eText.setFocusable(false);
                    try {
                        airingTimes = getAirings(monthOfYear, dayOfMonth);
                    } catch (DatabaseAccessException throwables) {
                        throwables.printStackTrace();
                    }
                    initializeAiringsButtons(airingTimes);
                }
            }, year, month, day);
            initializeAiringsButtons(airingTimes);
            eText.setEnabled(true);
            picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            picker.getDatePicker().setMaxDate(System.currentTimeMillis() + 2592000000L);
            picker.show();
        });
    }

    /**
     * This method initializes airings buttons based off string[] of airing times, these are toggle buttons and only one at a time can be "ON".
     * Airings buttons also have the ticket price located on them, so a call to logic layer is used (TicketLogic), to calc ticket price.
     *
     * @param airingTimes
     */
    protected void initializeAiringsButtons(String[] airingTimes) {

        LinearLayout ll = (LinearLayout) findViewById(R.id.toggleButtons);
        if (airingButtons != null){
           ll.removeAllViews();
        }

        airingButtons = new ToggleButton[airingTimes.length];

        for (int i = 0; i < airingTimes.length; i++) {
            airingButtons[i] = createCustomButton(airingTimes[i] + "\n$" + StandardTicketLogic.getTicketPrice(), getResources().getDrawable(R.drawable.button_border_off));
            airingButtons[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    toggleOffButtons(airingButtons);//toggle off all buttons
                    buttonView.setChecked(isChecked);//toggle on the calling button (ensures only one is on at a time)
                    toggleButtonImage(buttonView);//Toggles correct button image
                    checkOrderButtonConditions();//The order button conditions can change here, so we must call this.
                }
            });

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 10, 0);
            airingButtons[i].setLayoutParams(params);
            ll.addView(airingButtons[i]);
        }
    }

    /**
     * This method creates the custom button programmatically  with text and background.
     * Text is the text to be displayed on button,
     * drawable is drawable background
     *
     * @param text
     * @param background
     * @return a buttom object with above characteristics.
     */
    protected ToggleButton createCustomButton(String text, Drawable background) {
        ToggleButton myButton = new ToggleButton(this);
        myButton.setTextColor(Color.parseColor("#1980FC"));
        myButton.setBackground(background);
        Typeface face = Typeface.create("font/poppins_semibold", Typeface.BOLD);
        myButton.setTypeface(face);

        myButton.setText(text);
        myButton.setTextOff(text);
        myButton.setTextOn(text);

        return myButton;
    }

    /**
     * Helper method to turn off all toggleButtons in a toggleButton array
     *
     * @param myButtons
     */
    protected void toggleOffButtons(ToggleButton[] myButtons) {
        for (int i = 0; i < myButtons.length; i++)
            myButtons[i].setChecked(false);
    }

    /**
     * Helper method to turn on all toggleButtons in a toggleButton array
     *
     * @param myButtons
     */
    protected void toggleOnButtons(ToggleButton[] myButtons) {
        for (int i = 0; i < myButtons.length; i++)
            myButtons[i].setChecked(true);
    }

    /**
     * Helper method to provide proper toggleButtonImage based on whether the toggle button is checked or not
     *
     * @param buttonView
     */
    protected void toggleButtonImage(CompoundButton buttonView) {
        if (buttonView.isChecked()) {
            buttonView.setTextColor(Color.WHITE);
            Drawable d = getResources().getDrawable(R.drawable.button_border_on);
            buttonView.setBackground(d);

        } else {
            buttonView.setTextColor(Color.parseColor("#1980FC"));
            Drawable d = getResources().getDrawable(R.drawable.button_border_off);
            buttonView.setBackground(d);
        }
    }


    /**
     * This function checks whether the orderButton should be enabled or not
     * The conditions require that at least one airing/time has been selected \
     * and the number of tickets selected is > 0.
     *
     * @return Returns whether orderButton enabled conditions are met
     */
    protected boolean checkOrderButtonConditions() {
        for (int i = 0; i < airingButtons.length; i++) {
            if (airingButtons[i].isChecked()) {
                if (numOfTickets > 0) {
                    orderButton.setEnabled(true);
                    orderButton.setClickable(true);
                    return true;
                }
            }
        }
        orderButton.setEnabled(false);
        orderButton.setClickable(false);
        return false;
    }


    /**
     * The method makes a call to the database to get airings on the selected month and day and
     * sets them into the airings[].
     *
     * @param month
     * @param day
     * @return Because of current implementation of database, only dates are returned, so currently
     * all dates have the same showing times (12:30,2:30,4:30)
     * @throws DatabaseAccessException
     */
    protected String[] getAirings(int month, int day) throws DatabaseAccessException {

        //airings.addAll(dbm.getAirings(selectedMovie));
        stubAirings = new StubAirings(selectedMovie);
        airings.addAll(stubAirings.getAirings());
        return new String[]{"12:30 PM", "2:30 PM", "4:30 PM"};
    }

    /**
     * Helper method to create dialog, when order button is selected.
     */
    protected void openDialog() {
        OrderTicketsDialog orderTicketsDialog = new OrderTicketsDialog(getOrderBundle());
        orderTicketsDialog.show(getSupportFragmentManager(), "orderTicketDialog");
    }

    /**
     * Helper method to bundle selections, non persistent data and pass to next activity.
     *
     * @return bunlde with all selections made in this activity
     */

    protected Bundle getOrderBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("movieTitle", tvMovieTitle.getText().toString());
        bundle.putString("theatre", tvTheatre.getText().toString());
        bundle.putInt("price", price);
        bundle.putInt("ticketsNum", Integer.parseInt(eTNumOfTickets.getText().toString()));

        return bundle;
    }

    /**
     * Getter method that returns movie from movies list (from database manager).
     * Finds specific movie based off of title.
     *
     * @param title
     * @return
     */
    protected Movie getSelectedMovie(String title) {
        for (int i = 0; i < movies.size(); i++) {
            if (movies != null && movies.get(i).getTitle().equals(title)) {
                return movies.get(i);
            }
        }
        return null;// not there is list
    }

}