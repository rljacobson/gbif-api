package org.gbif.api.util;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.collect.Range;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class SearchTypeValidatorDoubleRangeTest {

  private final String arg;
  private final Double start;
  private final Double end;

  public SearchTypeValidatorDoubleRangeTest(String arg, Double start, Double end) {
    this.arg = arg;
    this.start = start;
    this.end = end;
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    Object[][] data = {
      {"10.3", null, null},
      {"10,20", 10d, 20d},
      {"*,20", null, 20d},
      {"10, *", 10d, null},
      {"10.1,20.2", 10.1d, 20.2d},
      {"-1,2.0432", -1d, 2.0432d},
      {"peter", null, null},
    };
    return Arrays.asList(data);
  };


  @Test
  public void testRange() throws Exception {
    try {
      Range<Double> range = SearchTypeValidator.parseDecimalRange(arg);
      if (start == null && end == null) {
        fail(arg + " supposed to be an invalid range");
      } else {
        // test parse result
        if (range.hasLowerBound()) {
          assertEquals(start, range.lowerEndpoint());
        } else {
          assertNull(start);
        }

        if (range.hasUpperBound()) {
          assertEquals(end, range.upperEndpoint());
        } else {
          assertNull(end);
        }

        assertTrue(SearchTypeValidator.isRange(arg));
      }

    } catch (IllegalArgumentException e) {
      // expected?
      if (start != null || end != null) {
        fail(arg + " supposed to be a valid range");
      }
    }
  }

}
