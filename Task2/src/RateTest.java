import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RateTest {

   // @Test
    //void calculate() {
    //}

    @Test
    void invalidRateConstruction_NullPeriods() {
        assertThrows(IllegalArgumentException.class, () ->
                new Rate(CarParkKind.STAFF, BigDecimal.TEN, BigDecimal.valueOf(7), null, null));
    }

    @Test
    void invalidRateConstruction_NullRates() {
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () ->
                new Rate(CarParkKind.STAFF, null, null, normalPeriods, reducedPeriods));
    }

    @Test
    void invalidRateConstruction_NegativeRates() {
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () ->
                new Rate(CarParkKind.STAFF, BigDecimal.valueOf(-5), BigDecimal.ZERO, normalPeriods, reducedPeriods));
    }

    @Test
    void invalidRateConstruction_NormalRateLessOrEqualReducedRate() {
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();

        assertThrows(IllegalArgumentException.class, () ->
                new Rate(CarParkKind.STAFF, BigDecimal.TEN, BigDecimal.TEN, normalPeriods, reducedPeriods));
    }

    @Test
    void invalidRateConstruction_OverlappingPeriods() {
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();

        normalPeriods.add(new Period(8, 12));
        reducedPeriods.add(new Period(10, 15));

        assertThrows(IllegalArgumentException.class, () ->
                new Rate(CarParkKind.STUDENT, BigDecimal.TEN, BigDecimal.valueOf(7), normalPeriods, reducedPeriods));
    }

    @Test
    void calculateVisitorRate() {
        Rate rate = new Rate(CarParkKind.VISITOR, BigDecimal.TEN, BigDecimal.valueOf(5), new ArrayList<>(), new ArrayList<>());
        Period period = new Period(10, 15);

        assertEquals(BigDecimal.ZERO, rate.calculate(period));
    }

    @Test
    void calculateNormalRate() {
        ArrayList<Period> normalPeriods = new ArrayList<>();
        normalPeriods.add(new Period(8, 12));
        Rate rate = new Rate(CarParkKind.STUDENT, BigDecimal.TEN, BigDecimal.valueOf(5), normalPeriods, new ArrayList<>());

        Period period = new Period(9, 11);

        assertEquals(BigDecimal.valueOf(20), rate.calculate(period));
    }

    @Test
    void calculateReducedRate() {
        ArrayList<Period> reducedPeriods = new ArrayList<>();
        reducedPeriods.add(new Period(2, 5));
        Rate rate = new Rate(CarParkKind.STUDENT, BigDecimal.TEN, BigDecimal.valueOf(7), new ArrayList<>(), reducedPeriods);

        Period period = new Period(3, 4);

        assertEquals(BigDecimal.valueOf(14), rate.calculate(period));
    }
    /////////////////NEW TEST CASES - BRANCH COVERAGE
    /////////////////////
    ///////////////////
    /////////////////
    @Test
    void validRateConstruction() {
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();

        normalPeriods.add(new Period(8, 12));
        reducedPeriods.add(new Period(14, 18));

        assertDoesNotThrow(() ->
                new Rate(CarParkKind.STUDENT, BigDecimal.valueOf(10), BigDecimal.valueOf(5), normalPeriods, reducedPeriods));
    }

    @Test
    void validRateConstruction_EmptyPeriods() {
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();

        assertDoesNotThrow(() ->
                new Rate(CarParkKind.STUDENT, BigDecimal.valueOf(10), BigDecimal.valueOf(5), normalPeriods, reducedPeriods));
    }

    @Test
    void validRateConstruction_NullKind() {
        ArrayList<Period> normalPeriods = new ArrayList<>();
        ArrayList<Period> reducedPeriods = new ArrayList<>();

        normalPeriods.add(new Period(8, 12));
        reducedPeriods.add(new Period(14, 18));

        assertDoesNotThrow(() ->
                new Rate(null, BigDecimal.valueOf(10), BigDecimal.valueOf(5), normalPeriods, reducedPeriods));
    }

    @Test
    void isValidPeriods_ValidNonOverlappingPeriods() {
        ArrayList<Period> periods1 = new ArrayList<>();
        ArrayList<Period> periods2 = new ArrayList<>();

        periods1.add(new Period(8, 12));
        periods2.add(new Period(14, 18));

        Rate rate = new Rate(CarParkKind.STUDENT, BigDecimal.valueOf(10), BigDecimal.valueOf(5), periods1, periods2);

        assertTrue(rate.isValidPeriods(periods1, periods2));
    }

    @Test
    void isValidPeriods_ValidOverlappingPeriods() {
        ArrayList<Period> periods1 = new ArrayList<>();
        ArrayList<Period> periods2 = new ArrayList<>();

        periods1.add(new Period(8, 12));
        periods2.add(new Period(11, 15));

        Rate rate = new Rate(CarParkKind.STUDENT, BigDecimal.valueOf(10), BigDecimal.valueOf(5), periods1, periods2);

        assertFalse(rate.isValidPeriods(periods1, periods2));
    }
}