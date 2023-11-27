
import java.util.List;

public class Period {
    private int startHour;
    private int endHour;

    public Period(int start, int end) {
        if (start >= end) {
            throw new IllegalArgumentException("start of period cannot be later or equal to end of period");
        }
        if (start < 0 || start > 24 || end < 0 || end > 24) {
            throw new IllegalArgumentException("start of period and end of period must be between 0 and 24");
        }
        this.startHour = start;
        this.endHour = end;
    }
    public Boolean isIn(int hour) {
        return hour >= this.startHour && hour < this.endHour;
    }
    public static Boolean isIn(int hour, List<Period> list) {
        Boolean isIn = false;
        int i = 0;
        while (i < list.size() && !isIn) {
            isIn = list.get(i).isIn(hour);
            i++;
        }
        return isIn;
    }
    public int duration() {
        return this.endHour - this.startHour;
    }
    public int occurences(List<Period> list) {
        int occurences = 0;
        for (int hour = this.startHour; hour < this.endHour; hour++) {
            if (isIn(hour, list)) {
                occurences++;
            }
        }
        return occurences;
    }
    public boolean overlaps(Period period) {
        return this.endHour>period.startHour && this.startHour<period.endHour;
    }
}