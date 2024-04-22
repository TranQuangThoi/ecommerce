package com.techmarket.api.controller;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.techmarket.api.Transaction.PaymentService;
import com.techmarket.api.dto.ApiMessageDto;
import com.techmarket.api.dto.ErrorCode;
import com.techmarket.api.form.transaction.CreatePaymentForm;
import com.techmarket.api.model.Order;
import com.techmarket.api.model.User;
import com.techmarket.api.repository.OrderRepository;
import com.techmarket.api.repository.UserRepository;
import com.techmarket.api.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/transaction")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TransactionController extends ABasicController{
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private PaymentService paymentService;
  @Autowired
  private EmailService emailService;


  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiMessageDto<String> create(@Valid @RequestBody CreatePaymentForm createPaymentForm, BindingResult bindingResult){

    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

    Order order = orderRepository.findById(createPaymentForm.getOrderId()).orElse(null);
    if (order==null)
    {
      apiMessageDto.setMessage("Not found order");
      apiMessageDto.setCode(ErrorCode.ORDER_ERROR_NOT_FOUND);
      return apiMessageDto;
    }

    try {
      Payment payment = paymentService.createPayment(createPaymentForm,order);
      for (Links link : payment.getLinks()) {
        if (link.getRel().equals("approval_url")) {
          apiMessageDto.setData(link.getHref());
          return apiMessageDto;
        }
      }
    } catch (PayPalRESTException e) {
      throw new RuntimeException(e);
    }

    apiMessageDto.setResult(false);
    apiMessageDto.setMessage("Paypal is not available now, please contact to our customer service");
    return apiMessageDto;
  }
  @GetMapping("/deposit/cancel")
  public ApiMessageDto<String> cancelPay(@RequestParam Long orderId) throws MessagingException {

    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    Order order = orderRepository.findById(orderId).orElse(null);
    if (order==null)
    {
      apiMessageDto.setMessage("order not found");
      return apiMessageDto;
    }
    if (order.getEmail()!=null)
    {
      emailService.sendOrderPaidToEmail(order,order.getEmail());
    }
    apiMessageDto.setMessage("cancel");
    return apiMessageDto;
  }

  @GetMapping("/deposit/success")
  public ApiMessageDto<String> successPay(@RequestParam("paymentId") String paymentId,
                                          @RequestParam("PayerID") String payerId ,@RequestParam Long orderId) {

    ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
    try {
      Payment payment = paymentService.executePayment(paymentId, payerId);
      if (payment.getState().equals("approved")) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order==null)
        {
          apiMessageDto.setMessage("order not found");
          return apiMessageDto;
        }
        order.setIsPaid(true);
        orderRepository.save(order);
        if (order.getEmail()!=null)
        {
          emailService.sendOrderPaidToEmail(order,order.getEmail());
        }
      }
    } catch (PayPalRESTException | MessagingException e) {
      System.out.println("lỗi");
    }

    apiMessageDto.setMessage("payment success");
    return apiMessageDto;
  }
}