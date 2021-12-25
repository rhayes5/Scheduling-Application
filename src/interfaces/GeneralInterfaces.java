package interfaces;

import java.time.ZoneId;
import java.util.Locale;

public interface GeneralInterfaces {
    interface Country {
        String getCountryString(Locale l);
    }

    interface UserTimeZone {
        String userTime(Locale l);
    }

    interface convertTime {
        ZoneId convertTime(ZoneId z);
    }
}
