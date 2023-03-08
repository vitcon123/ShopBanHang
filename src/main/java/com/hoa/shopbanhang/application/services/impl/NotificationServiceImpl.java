package com.hoa.shopbanhang.application.services.impl;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.CommonConstant;
import com.hoa.shopbanhang.application.constants.MessageConstant;
import com.hoa.shopbanhang.application.inputs.notification.CreateNotificationInput;
import com.hoa.shopbanhang.application.inputs.notification.SendNotificationInput;
import com.hoa.shopbanhang.application.inputs.notification.UpdateNotificationInput;
import com.hoa.shopbanhang.application.repositories.INotificationRepository;
import com.hoa.shopbanhang.application.repositories.IUserRepository;
import com.hoa.shopbanhang.application.services.INotificationService;
import com.hoa.shopbanhang.configs.exceptions.VsException;
import com.hoa.shopbanhang.domain.entities.Notification;
import com.hoa.shopbanhang.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements INotificationService {

  private final INotificationRepository notificationRepository;
  private final IUserRepository userRepository;
  private final ModelMapper modelMapper;

  public NotificationServiceImpl(INotificationRepository notificationRepository,
                                 IUserRepository userRepository, ModelMapper modelMapper) {
    this.notificationRepository = notificationRepository;
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public Notification findNotificationById(Long id) {
    Optional<Notification> notification = notificationRepository.findById(id);
    checkNotificationExists(notification);
    return notification.get();
  }

  @Override
  public List<Notification> findNotificationByUser(Long id) {
    Optional<User> user = userRepository.findById(id);
    UserServiceImpl.checkUserExists(user, id);
    List<Notification> notifications = user.get().getNotifications();

    return notifications;
  }

  @Override
  public RequestResponse createNotification(CreateNotificationInput input) {
    Notification notification = modelMapper.map(input, Notification.class);
    if (input.getUserId() != null) {
      Optional<User> user = userRepository.findById(input.getUserId());
      UserServiceImpl.checkUserExists(user, input.getUserId());
      notification.setUsers(List.of(user.get()));
    }
    notificationRepository.save(notification);
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public RequestResponse sendNotification(SendNotificationInput input) {
    Optional<Notification> notification = notificationRepository.findById(input.getNotificationId());
    checkNotificationExists(notification);
    List<User> users = notification.get().getUsers();
    Optional<User> user;
    for (Long i : input.getAccountId()) {
      user = userRepository.findById(i);
      if (!user.isEmpty()) {
        users.add(user.get());
      }
    }
    notification.get().setUsers(users);
    notificationRepository.save(notification.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public RequestResponse updateNotification(UpdateNotificationInput input) {
    Optional<Notification> notification = notificationRepository.findById(input.getId());
    checkNotificationExists(notification);
    modelMapper.map(input, notification.get());
    notificationRepository.save(notification.get());

    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public RequestResponse readNotification(Long id) {
    notificationRepository.readNotification(id);
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public RequestResponse readAllNotification(Long id) {
    Optional<User> user = userRepository.findById(id);
    UserServiceImpl.checkUserExists(user, id);
    List<Notification> notifications = user.get().getNotifications();
    for (Notification notification : notifications) {
      if (notification.getIsRead() == Boolean.FALSE) {
        notification.setIsRead(Boolean.TRUE);
        notificationRepository.save(notification);
      }
    }
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  @Override
  public RequestResponse deleteNotification(Long id) {
    notificationRepository.deleteById(id);
    return new RequestResponse(CommonConstant.TRUE, CommonConstant.EMPTY_STRING);
  }

  private void checkNotificationExists(Optional<Notification> notification) {
    if (notification.isEmpty()) {
      throw new VsException(MessageConstant.NOTIFICATION_NOT_EXISTS);
    }
  }

}
