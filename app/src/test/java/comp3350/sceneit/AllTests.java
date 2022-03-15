package comp3350.sceneit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.sceneit.data.HTTPUserManagerTest;
import comp3350.sceneit.data.PostgresDatabaseTest;
import comp3350.sceneit.logic.CreditManagerTest;
import comp3350.sceneit.logic.TicketLogicTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({TicketLogicTest.class, CreditManagerTest.class, PostgresDatabaseTest.class, HTTPUserManagerTest.class})
public class AllTests {
}
