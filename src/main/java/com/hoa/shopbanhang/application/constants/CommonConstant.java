package com.hoa.shopbanhang.application.constants;

import java.util.regex.Pattern;

public class CommonConstant {

  public static final String PRODUCT_ENV = "prod";
  public static final Long ONE_DAY_EPOCH_TIME_SECONDS = 86400L;
  public static final Long ONE_VALUE = 1L;
  public static final Integer ONE_INT_VALUE = 1;
  public static final String EMPTY_STRING = "";
  public static final boolean TRUE = true;
  public static final boolean FALSE = false;


  public static final Long ZERO_VALUE = 0L;
  public static final Integer ZERO_INT_VALUE = 0;

  public static final String BEARER_TOKEN = "Bearer";

  public static final String APPLICATION_JSON_TYPE = "application/json";

  public static final int PAGE_SIZE_DEFAULT = 20;
  public static final String SORT_BY_DEFAULT = "id";
  public static final String SORT_TYPE_ASC = "ASC";
  public static final String SORT_TYPE_DESC = "DESC";

  public static final String MEDIAS = "static/media";
  public static final String URL_MEDIA = "/media/";

  //Pattern
  public static final Pattern PATTERN_IMAGE = Pattern.compile("([^\\s]+(\\.(?i)(jpe?g|gif|png))$)");
  public static final Pattern PATTERN_VIDEO = Pattern.compile("([^\\s]+(\\.(?i)(mp4))$)");

}
