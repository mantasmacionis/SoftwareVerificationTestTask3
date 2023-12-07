

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class MacionisMantasTestTask3 {



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
                periodList.add(new Period(7, 14));


                Period period = new Period(10, 16);
                assertEquals(6, period.occurences(periodList));
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
      void calculateVisitorRate() {
          Rate rate = new Rate(CarParkKind.VISITOR, BigDecimal.TEN, BigDecimal.valueOf(5), new ArrayList<>(), new ArrayList<>());
          Period period = new Period(10, 15);

          assertEquals(BigDecimal.valueOf(12.5), rate.calculate(period));
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

              Period period = new Period(3, 11);

          assertEquals(BigDecimal.valueOf(14), rate.calculate(period));
      }

      @Test
      void isValidPeriods_TwoNonOverlappingPeriods_ShouldReturnTrue() {
          ArrayList<Period> periods = new ArrayList<>();
          periods.add(new Period(8, 12));
          periods.add(new Period(14, 18));

          Rate rate = new Rate(CarParkKind.STUDENT, BigDecimal.TEN, BigDecimal.valueOf(7), periods, new ArrayList<>());

          assertTrue(rate.isValidPeriods(periods));
      }

    @Test
    void isValidPeriods_TwoOverlappingPeriods_ShouldReturnFalse() {
        ArrayList<Period> periods = new ArrayList<>();
        periods.add(new Period(8, 12));
        periods.add(new Period(10, 15));

        Rate rate = new Rate(CarParkKind.STUDENT, BigDecimal.TEN, BigDecimal.valueOf(7), periods, new ArrayList<>());

        assertFalse(rate.isValidPeriods(periods));
    }
}

//RATE CLASS BELOW
///////////////////////////////////////////////////////////////////////////////////


 class Rate {
 private CarParkKind kind;
 private BigDecimal hourlyNormalRate;
 private BigDecimal hourlyReducedRate;
 private ArrayList<Period> reduced = new ArrayList<>();
 private ArrayList<Period> normal = new ArrayList<>();

 public Rate(CarParkKind kind, BigDecimal normalRate, BigDecimal reducedRate, ArrayList<Period> normalPeriods, ArrayList<Period> reducedPeriods) {
 if (reducedPeriods == null || normalPeriods == null) {
 throw new IllegalArgumentException("periods cannot be null");
 }
 if (normalRate == null || reducedRate == null) {
 throw new IllegalArgumentException("The rates cannot be null");
 }
 if (normalRate.compareTo(BigDecimal.ZERO) < 0 || reducedRate.compareTo(BigDecimal.ZERO) < 0) {
 throw new IllegalArgumentException("A rate cannot be negative");
 }
 if (normalRate.compareTo(reducedRate) <= 0) {
 throw new IllegalArgumentException("The normal rate cannot be less or equal to the reduced rate");
 }
 if (!isValidPeriods(reducedPeriods) || !isValidPeriods(normalPeriods)) {
 throw new IllegalArgumentException("The periods are not valid individually");
 }
 if (!isValidPeriods(reducedPeriods, normalPeriods)) {
 throw new IllegalArgumentException("The periods overlaps");
 }

 this.kind = kind;
 this.hourlyNormalRate = normalRate;
 this.hourlyReducedRate = reducedRate;
 this.reduced = reducedPeriods;
 this.normal = normalPeriods;
 }
 public boolean isValidPeriods(ArrayList<Period> periods1, ArrayList<Period> periods2) {
 Boolean isValid = true;
 int i = 0;
 while (i < periods1.size() && isValid) {
 isValid = isValidPeriod(periods1.get(i), periods2);
 i++;
 }
 return isValid;
 }
 public boolean isValidPeriods(ArrayList<Period> list) {
 if (list.size() < 2) {
 return true;
 }

 for (int i = 0; i < list.size() - 1; i++) {
 for (int j = i + 1; j < list.size(); j++) {
 if (list.get(i).overlaps(list.get(j))) {
 return false; // Periods overlap, return false
 }
 }
 }

 return true;
 }
 public Boolean isValidPeriod(Period period, List<Period> list) {
 Boolean isValid = true;
 int i = 0;
 while (i < list.size() && isValid) {
 isValid = !period.overlaps(list.get(i));
 i++;
 }
 return isValid;
 }
 public BigDecimal calculate(Period periodStay) {
 int normalRateHours = periodStay.occurences(normal);
 int reducedRateHours = periodStay.occurences(reduced);
 if (this.kind==CarParkKind.VISITOR) return BigDecimal.valueOf(0);
 return (this.hourlyNormalRate.multiply(BigDecimal.valueOf(normalRateHours))).add(
 this.hourlyReducedRate.multiply(BigDecimal.valueOf(reducedRateHours)));
 }
 }


//PERIOD CLASS BELOW
//////////////////////////////////////////////////////////////////////////////////////////////////////////////


 class Period {
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


//CarParkKind CLASS BELOW
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

enum CarParkKind {
 STAFF, STUDENT, MANAGEMENT, VISITOR
 }



