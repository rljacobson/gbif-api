package org.gbif.api.vocabulary;

import java.util.List;

import com.google.common.collect.ImmutableList;

/**
 * An enumeration of validation rules for single occurrence records.
 */
public enum OccurrenceValidationRule {

  /**
   * Coordinate is the exact 0/0 coordinate, often indicating a bad null coordinate.
   */
  ZERO_COORDINATE,

  /**
   * Coordinate has invalid lat/lon values out of their decimal max range.
   */
  COORDINATES_OUT_OF_RANGE,

  /**
   * The interpreted occurrence coordinates fall outside of the indicated country.
   */
  COUNTRY_COORDINATE_MISMATCH,

  /**
   * Latitude and longitude appear to be swapped.
   */
  PRESUMED_SWAPPED_COORDINATE,

  /**
   * Longitude appears to be negated, e.g. 32.3 instead of -32.3
   */
  PRESUMED_NEGATED_LONGITUDE,

  /**
   * Latitude appears to be negated, e.g. 32.3 instead of -32.3
   */
  PRESUMED_NEGATED_LATITUDE,

  /**
   * The recording date specified as the eventDate string and the individual year, month, day are contradicting.
   */
  RECORDED_DATE_MISMATCH,

  /**
   * A (partial) invalid date is given, such as a non existing date, invalid zero month, etc.
   */
  RECORDED_DATE_INVALID,

  /**
   * The recording dates year is highly unlikely, falling either into the future
   * or represents a very old date before 1700 that predates modern taxonomy.
   */
  RECORDED_YEAR_UNLIKELY,

  /**
   * Matching to the taxonomic backbone can only be done using a fuzzy, non exact match.
   */
  TAXON_MATCH_FUZZY,

  /**
   * Matching to the taxonomic backbone can only be done on a higher rank and not the scientific name.
   */
  TAXON_MATCH_HIGHERRANK,

  /**
   * Matching to the taxonomic backbone cannot be done cause there was no match at all
   * or several matches with too little information to keep them apart (homonyms).
   */
  TAXON_MATCH_NONE;

  public static final List<OccurrenceValidationRule> GEOSPATIAL_RULES = ImmutableList.of(ZERO_COORDINATE,
                                                                                   COORDINATES_OUT_OF_RANGE,
                                                                                   COUNTRY_COORDINATE_MISMATCH,
                                                                                   PRESUMED_SWAPPED_COORDINATE,
                                                                                   PRESUMED_NEGATED_LATITUDE,
                                                                                   PRESUMED_NEGATED_LONGITUDE);
  public static final List<OccurrenceValidationRule> TAXONOMIC_RULES = ImmutableList.of();

}
