package com.hoa.shopbanhang.application.constants;

public class DevMessageConstant {

  private DevMessageConstant() {
  }

  public static final class Common {
    public static final String NOT_FOUND_OBJECT_BY_ID = "Can not find %s by id = %s";
    public static final String DATA_WAS_DELETE = "This object id = %s was delete";
    public static final String DATA_WAS_DISABLE = "This object id = %s was disable";
    public static final String NO_DATA_SELECTED = "No data select result";
    public static final String DUPLICATE_NAME = "Duplicate name = %s";
    public static final String FILE_IS_EMPTY = "FileWeb is empty";
    private Common() {
    }
  }

  public static final class Token {
    public static final String TOKEN_IS_EXPIRED = "Token is expired time";
    public static final String TOKEN_IS_INVALID = "Token is invalid";
    public static final String CAN_NOT_GENERATE_TOKEN = "Can not generate token by token, try it with refresh token";
  }

  public static final class Cart {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found cart by id";
    public static final String NAME_IS_EXIST = "Category with name '%s' is exist";
    public static final String DUPLICATE_NAME = "Duplicate category name '%s'";
  }

  public static final class Category {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found category by id = %s";
    public static final String NAME_IS_EXIST = "Category with name '%s' is exist";
    public static final String DUPLICATE_NAME = "Duplicate category name '%s'";
  }

  public static final class Media {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found media by id = %s";
    public static final String NAME_IS_EXIST = "Category with name '%s' is exist";
    public static final String DUPLICATE_NAME = "Duplicate category name '%s'";
  }

  public static final class Product {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found product by id = %s";
    public static final String NAME_IS_EXIST = "Category with name '%s' is exist";
    public static final String DUPLICATE_NAME = "Duplicate category name '%s'";
  }

  public static final class Order {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found order by id = %s";
    public static final String NAME_IS_EXIST = "Category with name '%s' is exist";
    public static final String DUPLICATE_NAME = "Duplicate category name '%s'";
  }

  public static final class Subject {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found subject by id = %s";
    public static final String DUPLICATE_NAME = "Duplicate subject name '%s'";
  }

  public static final class Lesson {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found lesson by id = %s";
    public static final String ERR_NOT_FOUND_LESSON_BY_SUBJECT_ID = "Not found lesson by subject id = %s";
  }

  public static final class LessonStudent {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found lesson student by id = %s";
  }

  public static final class Comment {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found comment by id = %s";
    public static final String ERR_NOT_YOURS = "Comment id = '%s' is not yours.";
  }

  public static final class Notification {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found notification by id = %s";
  }

  public static final class Schedule {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found schedule by id = %s";
  }

  public static final class Role {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found role by id = %s";
    public static final String NAME_IS_EXIST = "Role with name '%s' is exist";
    public static final String DUPLICATE_NAME = "Duplicate role name '%s'";
  }

  public static final class User {
    public static final String ERR_NOT_FOUND_BY_ID = "Not found user student by id = %s";
    public static final String NOT_FOUND_USER_BY_USERNAME = "Not found user student by id = %s";
    public static final String ERR_NOT_FOUND_BY_EMAIL = "Not found user by email = %s";
    public static final String ERR_NOT_FOUND_BY_TOKEN = "Not found token = %s";
    public static final String ERR_EXPIRED_BY_TOKEN = "Token = %s has expired";
    public static final String INVALID_STUDENT_CODE = "Student code is invalid";
    public static final String INVALID_PASSWORD = "Password is invalid";
  }

  public static final class UserSubjectRelation {
    public static final String CAN_NOT_REMOVE_OBJECT = "Can not remove object with userId = %s and subjectId = %s";
  }

  public static final class SettingByKey {
    public static final String ERR_NOT_FOUND_BY_KEY = "Not found setting by key = %s";
  }
}
