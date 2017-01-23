package org.gbif.api.vocabulary;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.reflect.ClassPath;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Checks InterpretationRemark implementation under "org.gbif.api.vocabulary".
 */
public class InterpretationRemarkTest {

  private static final String ROOT_PACKAGE = "org.gbif.api.vocabulary";

  private static final List<Class<?>> IMPLEMENTING_CLASSES = loadInterpretationRemarkImplementations();

  /**
   * Get all {@link Class} that implement the {@link InterpretationRemark} interface from ROOT_PACKAGE.
   *
   * @return
   */
  private static List<Class<?>> loadInterpretationRemarkImplementations() {
    try {
      Set<ClassPath.ClassInfo> classes =
              ClassPath.from(InterpretationRemarkTest.class.getClassLoader()).getTopLevelClasses(ROOT_PACKAGE);

      return classes.stream()
              .map(ClassPath.ClassInfo::load)
              .filter(c -> Arrays.asList(c.getInterfaces()).contains(InterpretationRemark.class))
              .collect(Collectors.toList());
    } catch (IOException e) {
      fail(e.getMessage());
    }
    return Collections.emptyList();
  }

  /**
   * Checks all classes (enumerations) that implement {@link InterpretationRemark} to ensure they have unique entry
   * names.
   * {@link InterpretationRemark} implementations can be used as keys and can also be serialized as String. For this
   * reason we want to ensure we have unique entries among implementations. This is mainly to avoid confusion when we
   * do aggregation.
   */
  @Test
  public void testInterpretationRemarkImplementations() {
    Set<String> interpretationRemarks = new HashSet<>();
    IMPLEMENTING_CLASSES.forEach(cl -> Arrays.asList((InterpretationRemark[]) cl.getEnumConstants()).forEach(
            ir -> assertTrue("Enumeration value " + ir + " is unique within all InterpretationRemark implementations.",
                    interpretationRemarks.add(ir.toString()))
    )
    );
  }

  @Test
  public void testInterpretationRemarkNotNulls() {
    IMPLEMENTING_CLASSES.forEach(cl -> Arrays.asList((InterpretationRemark[]) cl.getEnumConstants()).forEach(
            ir -> assertTrue("InterpretationRemark implementations return a not null value for getSeverity()",
                    ir.getSeverity() != null)
            )
    );
  }


}
