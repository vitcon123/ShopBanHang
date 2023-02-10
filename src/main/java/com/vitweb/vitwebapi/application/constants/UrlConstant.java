package com.vitweb.vitwebapi.application.constants;

public class UrlConstant {

  public static final class Banner {
    public static final String PREFIX = "/banners";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

    public Banner() {

    }
  }

  public static final class Bill {
    public static final String PREFIX = "/bills";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

    public Bill() {

    }
  }

  public static final class Blog {
    public static final String PREFIX = "/blogs";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String FIND_BY_NAME = PREFIX + "/name/{name}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX;
    public static final String DELETE = PREFIX + "/{id}";

    public Blog() {

    }
  }

  public static final class Book {
    public static final String PREFIX = "/books";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

    public Book() {

    }
  }

  public static final class Comment {
    public static final String PREFIX = "/comments";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String CREATE_COMMENT_BLOG = PREFIX + "/comment-blog";
    public static final String CREATE_COMMENT_POST = PREFIX + "/comment-post";
    public static final String CREATE_COMMENT_LESSON = PREFIX + "/comment-lesson";
    public static final String UPDATE = PREFIX;
    public static final String DELETE = PREFIX + "/{id}";

    public Comment() {

    }
  }

  public static final class Course {
    public static final String PREFIX = "/courses";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX;
    public static final String DELETE = PREFIX + "/{id}";

    public Course() {

    }
  }

  public static final class Event {
    public static final String PREFIX = "/events";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

    public Event() {

    }
  }

  public static final class FavoriteItem {
    public static final String PREFIX = "/favorite-items";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

    public FavoriteItem() {

    }
  }

  public static final class Image {
    public static final String PREFIX = "/images";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

    public Image() {

    }
  }

  public static final class Lesson {
    public static final String PREFIX = "/lessons";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX;
    public static final String DELETE = PREFIX + "/{id}";

    public Lesson() {

    }
  }

  public static final class Podcast {
    public static final String PREFIX = "/podcasts";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

    public Podcast() {

    }
  }

  public static final class Post {
    public static final String PREFIX = "/posts";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX;
    public static final String DELETE = PREFIX + "/{id}";

    public Post() {

    }
  }

  public static final class Rate {
    public static final String PREFIX = "/rates";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

    public Rate() {

    }
  }

  public static final class Token {
    public static final String PREFIX = "/tokens";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{token}";
    public static final String VERIFY = PREFIX + "/verify/{token}";
    public static final String VERIFY_FORGOT_PASSWORD = PREFIX + "/verify-forgot-password/{token}";
    public static final String RESEND = PREFIX + "/resend/{token}";
    public static final String FORGOT_PASSWORD = PREFIX + "/forgot-password/{token}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

    public Token() {

    }
  }

  public static final class UserLesson {
    public static final String PREFIX = "/user-lessons";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

    public UserLesson() {

    }
  }

  public static final class Voucher {
    public static final String PREFIX = "/vouchers";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String CREATE = PREFIX;
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

    public Voucher() {

    }
  }

  public static final class User {
    public static final String PREFIX = "/users";
    public static final String LIST = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String GET_FOLLOWING = PREFIX + "/{id}/following";
    public static final String GET_FOLLOWERS = PREFIX + "/{id}/followers";
    public static final String GET_ID_NAME = PREFIX + "/{idName}";
    public static final String CHANGE_AVATAR = PREFIX + "/change-avatar/{id}";
    public static final String FOLLOW = PREFIX + "/follow/{idFollow}";
    public static final String UNFOLLOW = PREFIX + "/unfollow/{idFollow}";
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

    public User() {

    }
  }

  public static class Role {
    private Role() {
    }

    private static final String PRE_FIX = "/roles";
    public static final String DATA_ROLE = PRE_FIX;
    public static final String DATA_ROLE_ID = PRE_FIX + "/{id}";
  }

  public static class Category {
    private Category() {
    }

    private static final String PREFIX = "/categories";
    public static final String LIST = PREFIX;
    public static final String CREATE = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String UPDATE = PREFIX;
    public static final String DELETE = PREFIX + "/{id}";

  }

  public static class Help {
    private Help() {
    }

    private static final String PREFIX = "/helps";
    public static final String LIST = PREFIX;
    public static final String CREATE = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String UPDATE = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";

  }

  public static class Notification {
    private Notification() {
    }

    private static final String PREFIX = "/notifications";
    public static final String LIST = PREFIX;
    public static final String CREATE = PREFIX;
    public static final String GET = PREFIX + "/{id}";
    public static final String UPDATE = PREFIX;
    public static final String READ = PREFIX + "/{id}";
    public static final String DELETE = PREFIX + "/{id}";
  }

  public static class UserCourse {
    private UserCourse() {
    }

    private static final String PREFIX = "/user-course";
    public static final String DATA_USER_COURSE = PREFIX;
    public static final String DATA_USER_COURSE_REGIS = PREFIX + "/register/{userId}/{courseId}";
    public static final String DATA_USER_COURSE_UN_REGIS = PREFIX + "/unregister/{userId}/{courseId}";
    public static final String DATA_USER_COURSE_FIND = PREFIX + "/{userId}";
  }

  public static class Timer {
    private Timer() {
    }

    private static final String PRE_FIX = "/timer";
    public static final String DATA_TIMER = PRE_FIX;
    public static final String DATA_TIMER_NOW = PRE_FIX + "/{id}/now";
    public static final String DATA_TIMER_MONTH = PRE_FIX + "/{id}/{month}";
  }

  public static class Auth {
    private Auth() {
    }

    private static final String PRE_FIX = "/auth";
    public static final String LOGIN = PRE_FIX + "/login";
    public static final String SIGNUP = PRE_FIX + "/signup";
    public static final String REFRESH_TOKEN = PRE_FIX + "/refresh-token";
    public static final String REFRESH_PASSWORD = PRE_FIX + "/refresh-password/{email}";
    public static final String FORGOT_PASSWORD = PRE_FIX + "/forgot-password/{email}";
    public static final String CHANGE_PASSWORD = PRE_FIX + "/change-password/{email}";
    public static final String RESET_PASSWORD = PRE_FIX + "/reset-password/{email}";
    public static final String VALIDATE = PRE_FIX + "/validate";
    public static final String LOGOUT = PRE_FIX + "/logout/{id}";
  }

  public static class ChatRoom {
    private static final String PRE_FIX = "/rooms";
    public static final String LIST = PRE_FIX;
    public static final String GET = PRE_FIX + "/{id}";
    public static final String SEARCH = PRE_FIX + "/search";
    public static final String CREATE = PRE_FIX;
    public static final String DELETE = PRE_FIX + "/{id}";

    public ChatRoom() {
    }

  }
}