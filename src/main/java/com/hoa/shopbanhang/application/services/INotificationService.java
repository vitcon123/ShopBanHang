package com.hoa.shopbanhang.application.services;

import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.inputs.cart.CreateCartInput;
import com.hoa.shopbanhang.application.inputs.cart.UpdateCartInput;
import com.hoa.shopbanhang.application.inputs.notification.CreateNotificationInput;
import com.hoa.shopbanhang.application.inputs.notification.SendNotificationInput;
import com.hoa.shopbanhang.application.inputs.notification.UpdateNotificationInput;
import com.hoa.shopbanhang.domain.entities.Cart;
import com.hoa.shopbanhang.domain.entities.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

public interface INotificationService {

  Notification findNotificationById(Long id);

  List<Notification> findNotificationByUser(Long id);

  RequestResponse createNotification(CreateNotificationInput input);

  RequestResponse sendNotification(SendNotificationInput input);

  RequestResponse updateNotification(UpdateNotificationInput input);

  RequestResponse readNotification(Long id);

  RequestResponse readAllNotification(Long id);

  RequestResponse deleteNotification(Long id);

}
