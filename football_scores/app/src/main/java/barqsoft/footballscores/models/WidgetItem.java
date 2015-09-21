package barqsoft.footballscores.models;

/**
 * Created by clerks on 9/20/15.
 */
public class WidgetItem {
    private final String home;
    private final String away;
    private final int homeGoals;
    private final int awayGoals;

    public WidgetItem(String home, String away, int homeGoals, int awayGoals) {
        this.home = home;
        this.away = away;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public String getHome() {
        return home;
    }

    public String getAway() {
        return away;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }
}
