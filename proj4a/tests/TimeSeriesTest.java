import main.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testYears() {
        TimeSeries ts = new TimeSeries();
        assertThat(ts.years()).isEmpty();

        ts.put(1401, 2.5);
        ts.put(1690, 5.5);
        ts.put(1960, 3.25);
        ts.put(2099, 12.33);

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1401);
        expectedYears.add(1690);
        expectedYears.add(1960);
        expectedYears.add(2099);

        assertThat(ts.years()).containsExactlyElementsIn(expectedYears).inOrder();
    }

    @Test
    public void testData() {
        TimeSeries ts = new TimeSeries();
        assertThat(ts.data()).isEmpty();

        ts.put(1401, 2.5);
        ts.put(1690, 12.33);
        ts.put(1960, 3.25);
        ts.put(2099, 5.5);

        List<Double> expectedData = new ArrayList<>();
        expectedData.add(2.5);
        expectedData.add(12.33);
        expectedData.add(3.25);
        expectedData.add(5.5);

        assertThat(ts.data()).containsExactlyElementsIn(expectedData).inOrder();
    }

    @Test
    public void testTimeSeries() {
        TimeSeries ts0 = new TimeSeries();
        assertThat(new TimeSeries(ts0, 1600, 2000)).isEmpty();

        ts0.put(1401, 2.5);
        ts0.put(1690, 12.33);
        ts0.put(1960, 3.25);
        ts0.put(2099, 5.5);

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1690);
        expectedYears.add(1960);
        List<Double> expectedData = new ArrayList<>();
        expectedData.add(12.33);
        expectedData.add(3.25);

        TimeSeries ts1 = new TimeSeries(ts0, 1600, 2000);
        assertThat(ts1.years()).containsExactlyElementsIn(expectedYears).inOrder();
        assertThat(ts1.data()).containsExactlyElementsIn(expectedData).inOrder();
    }

    @Test
    public void testPlus() {
        TimeSeries TVCount = new TimeSeries();
        TimeSeries computerCount = new TimeSeries();
        assertThat(TVCount.plus(computerCount).years()).isEmpty();
        assertThat(TVCount.plus(computerCount).data()).isEmpty();

        TVCount.put(1960, 200.0);
        TVCount.put(1990, 1000.0);
        TVCount.put(2020, 5000.0);
        computerCount.put(1990, 100.0);
        computerCount.put(2020, 8000.0);
        computerCount.put(2050, 15000.0);

        TimeSeries TVCopy = new TimeSeries(TVCount, 1400, 2100);
        TimeSeries computerCopy = new TimeSeries(computerCount, 1400, 2100);
        TimeSeries totalCount = TVCount.plus(computerCount);

        List<Integer> expectedYears = new ArrayList<>();
        List<Double> expectedData = new ArrayList<>();
        expectedYears.add(1960);
        expectedYears.add(1990);
        expectedYears.add(2020);
        expectedYears.add(2050);
        expectedData.add(200.0);
        expectedData.add(1100.0);
        expectedData.add(13000.0);
        expectedData.add(15000.0);

        assertThat(totalCount.years().size()).isEqualTo(4);
        assertThat(totalCount.years()).containsExactlyElementsIn(expectedYears).inOrder();
        for (int i = 0; i < 4; i++) {
            assertThat(totalCount.data().get(i)).isWithin(1E-10).of(expectedData.get(i));
        }
        assertThat(TVCount.years()).containsExactlyElementsIn(TVCopy.years()).inOrder();
        for (int i = 0; i < 3; i++) {
            assertThat(TVCount.data().get(i)).isWithin(1E-10).of(TVCopy.data().get(i));
        }
        assertThat(computerCount.years()).containsExactlyElementsIn(computerCopy.years()).inOrder();
        for (int i = 0; i < 3; i++) {
            assertThat(computerCount.data().get(i)).isWithin(1E-10).of(computerCopy.data().get(i));
        }
    }


    @Test
    public void testDividedBy() {
        TimeSeries TVCount = new TimeSeries();
        TimeSeries computerCount = new TimeSeries();
        assertThat(TVCount.dividedBy(computerCount).years()).isEmpty();
        assertThat(TVCount.dividedBy(computerCount).data()).isEmpty();

        TVCount.put(1990, 1000.0);
        TVCount.put(2020, 5000.0);
        computerCount.put(1990, 100.0);
        computerCount.put(2020, 10000.0);
        computerCount.put(2050, 15000.0);

        TimeSeries TVCopy = new TimeSeries(TVCount, 1400, 2100);
        TimeSeries computerCopy = new TimeSeries(computerCount, 1400, 2100);
        TimeSeries TVTimesOverComputer = TVCount.dividedBy(computerCount);

        List<Integer> expectedYears = new ArrayList<>();
        List<Double> expectedData = new ArrayList<>();
        expectedYears.add(1990);
        expectedYears.add(2020);
        expectedData.add(10.0);
        expectedData.add(0.5);

        assertThat(TVTimesOverComputer.years().size()).isEqualTo(2);
        assertThat(TVTimesOverComputer.years()).containsExactlyElementsIn(expectedYears).inOrder();
        for (int i = 0; i < 2; i++) {
            assertThat(TVTimesOverComputer.data().get(i)).isWithin(1E-10).of(expectedData.get(i));
        }
        assertThat(TVCount.years()).containsExactlyElementsIn(TVCopy.years()).inOrder();
        for (int i = 0; i < 2; i++) {
            assertThat(TVCount.data().get(i)).isWithin(1E-10).of(TVCopy.data().get(i));
        }
        assertThat(computerCount.years()).containsExactlyElementsIn(computerCopy.years()).inOrder();
        for (int i = 0; i < 3; i++) {
            assertThat(computerCount.data().get(i)).isWithin(1E-10).of(computerCopy.data().get(i));
        }
    }
} 