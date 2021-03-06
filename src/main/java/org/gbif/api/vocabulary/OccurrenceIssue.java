package org.gbif.api.vocabulary;

import org.gbif.dwc.terms.DcTerm;
import org.gbif.dwc.terms.DwcTerm;
import org.gbif.dwc.terms.GbifTerm;
import org.gbif.dwc.terms.Term;
import org.gbif.utils.AnnotationUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import static org.gbif.api.vocabulary.InterpretationRemarkSeverity.ERROR;
import static org.gbif.api.vocabulary.InterpretationRemarkSeverity.INFO;
import static org.gbif.api.vocabulary.InterpretationRemarkSeverity.WARNING;

/**
 * An enumeration of validation rules for single occurrence records.
 */
public enum OccurrenceIssue implements InterpretationRemark {

  /**
   * Coordinate is the exact 0/0 coordinate, often indicating a bad null coordinate.
   */
  ZERO_COORDINATE(WARNING, TermsGroup.COORDINATES_TERMS_NO_DATUM),

  /**
   * Coordinate has invalid lat/lon values out of their decimal max range.
   */
  COORDINATE_OUT_OF_RANGE(WARNING, TermsGroup.COORDINATES_TERMS_NO_DATUM),

  /**
   * Coordinate value given in some form but GBIF is unable to interpret it.
   */
  COORDINATE_INVALID(WARNING, TermsGroup.COORDINATES_TERMS_NO_DATUM),

  /**
   * Original coordinate modified by rounding to 5 decimals.
   */
  COORDINATE_ROUNDED(INFO, TermsGroup.COORDINATES_TERMS_NO_DATUM),

  /**
   * The geodetic datum given could not be interpreted.
   */
  GEODETIC_DATUM_INVALID(WARNING, DwcTerm.geodeticDatum),

  /**
   * Indicating that the interpreted coordinates assume they are based on WGS84 datum as the datum was either not
   * indicated or interpretable. See GEODETIC_DATUM_INVALID.
   */
  GEODETIC_DATUM_ASSUMED_WGS84(INFO, DwcTerm.geodeticDatum),

  /**
   * The original coordinate was successfully reprojected from a different geodetic datum to WGS84.
   */
  COORDINATE_REPROJECTED(INFO, TermsGroup.COORDINATES_TERMS),

  /**
   * The given decimal latitude and longitude could not be reprojected to WGS84 based on the provided datum.
   */
  COORDINATE_REPROJECTION_FAILED(WARNING, TermsGroup.COORDINATES_TERMS),

  /**
   * Indicates successful coordinate reprojection according to provided datum, but which results in a datum shift
   * larger than 0.1 decimal degrees.
   */
  COORDINATE_REPROJECTION_SUSPICIOUS(WARNING, TermsGroup.COORDINATES_TERMS),

  /**
   * Indicates an invalid or very unlikely coordinate accuracy derived from precision or uncertainty in meters.
   */
  @Deprecated //see POR-3061
  COORDINATE_ACCURACY_INVALID(WARNING),

  /**
   * Indicates an invalid or very unlikely coordinatePrecision
   */
  COORDINATE_PRECISION_INVALID(WARNING, DwcTerm.coordinatePrecision),

  /**
   * Indicates an invalid or very unlikely dwc:uncertaintyInMeters.
   */
  COORDINATE_UNCERTAINTY_METERS_INVALID(WARNING, DwcTerm.coordinateUncertaintyInMeters),

  /**
   * There is a mismatch between coordinate uncertainty in meters and coordinate precision.
   */
  @Deprecated //see POR-1804
  COORDINATE_PRECISION_UNCERTAINTY_MISMATCH(WARNING),

  /**
   * The interpreted occurrence coordinates fall outside of the indicated country.
   */
  COUNTRY_COORDINATE_MISMATCH(WARNING, TermsGroup.COORDINATES_COUNTRY_TERMS),

  /**
   * Interpreted country for dwc:country and dwc:countryCode contradict each other.
   */
  COUNTRY_MISMATCH(WARNING, TermsGroup.COUNTRY_TERMS),

  /**
   * Uninterpretable country values found.
   */
  COUNTRY_INVALID(WARNING, TermsGroup.COUNTRY_TERMS),

  /**
   * The interpreted country is based on the coordinates, not the verbatim string information.
   */
  COUNTRY_DERIVED_FROM_COORDINATES(WARNING, TermsGroup.COORDINATES_COUNTRY_TERMS),

  /**
   * The interpreted continent and country do not match up.
   */
  CONTINENT_COUNTRY_MISMATCH(WARNING),

  /**
   * Uninterpretable continent values found.
   */
  CONTINENT_INVALID(WARNING),

  /**
   * The interpreted continent is based on the coordinates, not the verbatim string information.
   */
  CONTINENT_DERIVED_FROM_COORDINATES(WARNING),

  /**
   * Latitude and longitude appear to be swapped.
   */
  PRESUMED_SWAPPED_COORDINATE(WARNING, TermsGroup.COORDINATES_TERMS_NO_DATUM),

  /**
   * Longitude appears to be negated, e.g. 32.3 instead of -32.3
   */
  PRESUMED_NEGATED_LONGITUDE(WARNING, TermsGroup.COORDINATES_TERMS_NO_DATUM),

  /**
   * Latitude appears to be negated, e.g. 32.3 instead of -32.3
   */
  PRESUMED_NEGATED_LATITUDE(WARNING, TermsGroup.COORDINATES_TERMS_NO_DATUM),

  /**
   * The recording date specified as the eventDate string and the individual year, month, day are contradicting.
   */
  RECORDED_DATE_MISMATCH(WARNING, TermsGroup.RECORDED_DATE_TERMS),

  /**
   * A (partial) invalid date is given, such as a non existing date, invalid zero month, etc.
   */
  RECORDED_DATE_INVALID(WARNING, TermsGroup.RECORDED_DATE_TERMS),

  /**
   * The recording date is highly unlikely, falling either into the future
   * or represents a very old date before 1600 that predates modern taxonomy.
   */
  RECORDED_DATE_UNLIKELY(WARNING, TermsGroup.RECORDED_DATE_TERMS),

  /**
   * Matching to the taxonomic backbone can only be done using a fuzzy, non exact match.
   */
  TAXON_MATCH_FUZZY(WARNING, TermsGroup.TAXONOMY_TERMS),

  /**
   * Matching to the taxonomic backbone can only be done on a higher rank and not the scientific name.
   */
  TAXON_MATCH_HIGHERRANK(WARNING, TermsGroup.TAXONOMY_TERMS),

  /**
   * Matching to the taxonomic backbone cannot be done cause there was no match at all
   * or several matches with too little information to keep them apart (homonyms).
   */
  TAXON_MATCH_NONE(WARNING, TermsGroup.TAXONOMY_TERMS),

  /**
   * Set if supplied depth is not given in the metric system, for example using feet instead of meters
   */
  DEPTH_NOT_METRIC(WARNING, DwcTerm.minimumDepthInMeters, DwcTerm.maximumDepthInMeters),

  /**
   * Set if depth is larger than 11.000m or negative.
   */
  DEPTH_UNLIKELY(WARNING, DwcTerm.minimumDepthInMeters, DwcTerm.maximumDepthInMeters),

  /**
   * Set if supplied min>max
   */
  DEPTH_MIN_MAX_SWAPPED(WARNING, DwcTerm.minimumDepthInMeters, DwcTerm.maximumDepthInMeters),

  /**
   * Set if depth is a non numeric value
   */
  DEPTH_NON_NUMERIC(WARNING, DwcTerm.minimumDepthInMeters, DwcTerm.maximumDepthInMeters),

  /**
   * Set if elevation is above the troposphere (17km) or below 11km (Mariana Trench).
   */
  ELEVATION_UNLIKELY(WARNING, DwcTerm.minimumElevationInMeters, DwcTerm.maximumElevationInMeters),

  /**
   * Set if supplied min > max elevation
   */
  ELEVATION_MIN_MAX_SWAPPED(WARNING, DwcTerm.minimumElevationInMeters, DwcTerm.maximumElevationInMeters),

  /**
   * Set if supplied elevation is not given in the metric system, for example using feet instead of meters
   */
  ELEVATION_NOT_METRIC(WARNING, DwcTerm.minimumElevationInMeters, DwcTerm.maximumElevationInMeters),

  /**
   * Set if elevation is a non numeric value
   */
  ELEVATION_NON_NUMERIC(WARNING, DwcTerm.minimumElevationInMeters, DwcTerm.maximumElevationInMeters),

  /**
   * A (partial) invalid date is given for dc:modified, such as a non existing date, invalid zero month, etc.
   */
  MODIFIED_DATE_INVALID(WARNING, DcTerm.modified),

  /**
   * The date given for dc:modified is in the future or predates unix time (1970).
   */
  MODIFIED_DATE_UNLIKELY(WARNING, DcTerm.modified),

  /**
   * The date given for dwc:dateIdentified is in the future or before Linnean times (1700).
   */
  IDENTIFIED_DATE_UNLIKELY(WARNING, DwcTerm.dateIdentified),

  /**
   * The date given for dwc:dateIdentified is invalid and cant be interpreted at all.
   */
  IDENTIFIED_DATE_INVALID(WARNING, DwcTerm.dateIdentified),

  /**
   * The given basis of record is impossible to interpret or seriously different from the recommended vocabulary.
   */
  BASIS_OF_RECORD_INVALID(WARNING, DwcTerm.basisOfRecord),

  /**
   * The given type status is impossible to interpret or seriously different from the recommended vocabulary.
   */
  TYPE_STATUS_INVALID(WARNING, DwcTerm.typeStatus),

  /**
   * An invalid date is given for dc:created of a multimedia object.
   */
  MULTIMEDIA_DATE_INVALID(WARNING) ,

  /**
   * An invalid uri is given for a multimedia object.
   */
  MULTIMEDIA_URI_INVALID(WARNING),

  /**
   * An invalid uri is given for dc:references.
   */
  REFERENCES_URI_INVALID(WARNING, DcTerm.references),

  /**
   * An error occurred during interpretation, leaving the record interpretation incomplete.
   */
  INTERPRETATION_ERROR(ERROR),

  /**
   * Individual count value not parsable into an integer.
   */
  INDIVIDUAL_COUNT_INVALID(WARNING, DwcTerm.individualCount);


  /**
   * Simple helper nested class to allow grouping of Term mostly to increase readability of this class.
   */
  private static class TermsGroup {

    static final Term[] COORDINATES_TERMS_NO_DATUM = {
            DwcTerm.decimalLatitude,
            DwcTerm.decimalLongitude,
            DwcTerm.verbatimLatitude,
            DwcTerm.verbatimLongitude,
            DwcTerm.verbatimCoordinates
    };

    static final Term[] COORDINATES_TERMS = {
            DwcTerm.decimalLatitude,
            DwcTerm.decimalLongitude,
            DwcTerm.verbatimLatitude,
            DwcTerm.verbatimLongitude,
            DwcTerm.verbatimCoordinates,
            DwcTerm.geodeticDatum
    };

    static final  Term[] COUNTRY_TERMS = {
            DwcTerm.country,
            DwcTerm.countryCode
    };

    static final  Term[] COORDINATES_COUNTRY_TERMS = {
            DwcTerm.decimalLatitude,
            DwcTerm.decimalLongitude,
            DwcTerm.verbatimLatitude,
            DwcTerm.verbatimLongitude,
            DwcTerm.verbatimCoordinates,
            DwcTerm.geodeticDatum,
            DwcTerm.country,
            DwcTerm.countryCode
    };

    static final  Term[] RECORDED_DATE_TERMS = {
            DwcTerm.eventDate,
            DwcTerm.year, DwcTerm.month, DwcTerm.day
    };

    static final  Term[] TAXONOMY_TERMS = {
            DwcTerm.kingdom,
            DwcTerm.phylum,
            DwcTerm.class_,
            DwcTerm.order,
            DwcTerm.family,
            DwcTerm.genus,
            DwcTerm.scientificName,
            DwcTerm.scientificNameAuthorship,
            GbifTerm.genericName,
            DwcTerm.specificEpithet,
            DwcTerm.infraspecificEpithet
    };
  }

  private final Set<Term> relatedTerms;
  private final InterpretationRemarkSeverity severity;
  private final boolean isDeprecated;

  /**
   * {@link OccurrenceIssue} not linked to any specific {@link Term}.
   */
  OccurrenceIssue(InterpretationRemarkSeverity severity) {
    this.severity = severity;
    this.relatedTerms = Collections.emptySet();
    this.isDeprecated = AnnotationUtils.isFieldDeprecated(OccurrenceIssue.class, this.name());
  }

  /**
   * {@link OccurrenceIssue} linked to the provided {@link Term}.
   *
   * @param relatedTerms
   */
  OccurrenceIssue(InterpretationRemarkSeverity severity, Term... relatedTerms) {
    this.severity = severity;
    this.relatedTerms = ImmutableSet.<Term>builder().add(relatedTerms).build();
    this.isDeprecated = AnnotationUtils.isFieldDeprecated(OccurrenceIssue.class, this.name());
  }

  @Override
  public String getId() {
    return name();
  }

  @Override
  public Set<Term> getRelatedTerms(){
    return relatedTerms;
  }

  @Override
  public InterpretationRemarkSeverity getSeverity(){
    return severity;
  }

  @Override
  public boolean isDeprecated(){
    return isDeprecated;
  }

  /**
   * All issues that indicate problems with the coordinates and thus should not be shown on maps.
   */
  public static final List<OccurrenceIssue> GEOSPATIAL_RULES = ImmutableList.of(ZERO_COORDINATE,
                                                                                COORDINATE_INVALID,
                                                                                COORDINATE_OUT_OF_RANGE,
                                                                                COUNTRY_COORDINATE_MISMATCH);
  public static final List<OccurrenceIssue> TAXONOMIC_RULES = ImmutableList.of(TAXON_MATCH_FUZZY,
                                                                               TAXON_MATCH_HIGHERRANK,
                                                                               TAXON_MATCH_NONE);

}
