import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PeriodTest {


    @Test
    void invalidPeriodConstruction_EndBeforeStart() {
        assertThrows(IllegalArgumentException.class, () ->
                new Period(12, 8));
    }

    @Test
    void invalidPeriodConstruction_StartHourOutOfRange() {
        assertThrows(IllegalArgumentException.class, () ->
                new Period(-1, 12));
    }

    @Test
    void invalidPeriodConstruction_EndHourOutOfRange() {
        assertThrows(IllegalArgumentException.class, () ->
                new Period(8, 25));
    }

    @Test
    void invalidPeriodConstruction_DurationZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new Period(8, 8));
    }

    @Test
    void duration() {
        Period period = new Period(8, 12);
        assertEquals(4, period.duration());
    }

    @Test
    void overlaps() {
        Period period1 = new Period(8, 12);
        Period period2 = new Period(10, 14);
        Period period3 = new Period(14, 18);

        assertTrue(period1.overlaps(period2));
        assertFalse(period1.overlaps(period3));
    }

    @Test
    void occurences() {
        List<Period> periodList = new ArrayList<>();
        periodList.add(new Period(8, 12));
        periodList.add(new Period(14, 18));

        Period period = new Period(10, 16);
        assertEquals(6, period.occurences(periodList));
    }



}