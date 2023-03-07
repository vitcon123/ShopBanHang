package com.hoa.shopbanhang.adapter.web.v1.controller;

import com.hoa.shopbanhang.adapter.web.base.RestApiV1;
import com.hoa.shopbanhang.adapter.web.base.VsResponseUtil;
import com.hoa.shopbanhang.adapter.web.v1.transfer.response.RequestResponse;
import com.hoa.shopbanhang.application.constants.UrlConstant;
import com.hoa.shopbanhang.application.inputs.notification.CreateNotificationInput;
import com.hoa.shopbanhang.application.inputs.notification.SendNotificationInput;
import com.hoa.shopbanhang.application.inputs.notification.UpdateNotificationInput;
import com.hoa.shopbanhang.application.services.INotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestApiV1
public class NotificationController {

  private final INotificationService notificationService;

  public NotificationController(INotificationService notificationService) {
    this.notificationService = notificationService;
  }


  @GetMapping(UrlConstant.Notification.GET)
  public ResponseEntity<?> getNotificationById(@PathVariable("id") Long idNotification) {
    return VsResponseUtil.ok(notificationService.findNotificationById(idNotification));
  }

  @GetMapping(UrlConstant.Notification.GET_BY_ACCOUNT)
  public ResponseEntity<?> getNotificationByAccount(@PathVariable("idAccount") Long idAccount) {
    return VsResponseUtil.ok(notificationService.findNotificationByUser(idAccount));
  }

  @PostMapping(UrlConstant.Notification.CREATE)
  public ResponseEntity<?> createNotification(@Valid @RequestBody CreateNotificationInput input) {
    // Get output
    RequestResponse output = notificationService.createNotification(input);
    // Return output
    return VsResponseUtil.ok(output);
  }

  @PatchMapping(UrlConstant.Notification.SEND)
  public ResponseEntity<?> sendNotification(@Valid @RequestBody SendNotificationInput input) {
    // Get output
    RequestResponse output = notificationService.sendNotification(input);
    // Return output
    return VsResponseUtil.ok(output);
  }

  @PatchMapping(UrlConstant.Notification.UPDATE)
  public ResponseEntity<?> updateNotification(@Valid @RequestBody UpdateNotificationInput input) {
    // Get output
    RequestResponse output = notificationService.updateNotification(input);
    // Return output
    return VsResponseUtil.ok(output);
  }

  @PatchMapping(UrlConstant.Notification.READ)
  public ResponseEntity<?> readNotification(@PathVariable("id") Long id) {
    // Get output
    RequestResponse output = notificationService.readNotification(id);
    // Return output
    return VsResponseUtil.ok(output);
  }

  @PatchMapping(UrlConstant.Notification.READ_ALL)
  public ResponseEntity<?> readAllNotification(@PathVariable("idAccount") Long idAccount) {
    // Get output
    RequestResponse output = notificationService.readAllNotification(idAccount);
    // Return output
    return VsResponseUtil.ok(output);
  }

  @DeleteMapping(UrlConstant.Notification.DELETE)
  public ResponseEntity<?> deleteNotification(@PathVariable("id") Long id) {
    // Get output
    RequestResponse output = notificationService.deleteNotification(id);
    // Return output
    return VsResponseUtil.ok(output);
  }

}
