package interfaces;

import java.util.Locale;

public interface GeneralInterfaces {

    /**
     * Interface for 2 lambda expressions.
     * The first lambda expression uses the interface to get and display the user's country as a string on the login page using the user's default locale.
     * The second lambda expression uses the interface to get and display the user's time zone on the login page using the user's default locale.
     */
    interface StringFromLocale {
        String stringFromLocale(Locale l);
    }
}
