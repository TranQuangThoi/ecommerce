package com.techmarket.api.model.criteria;

import com.techmarket.api.model.Order;
import com.techmarket.api.model.Review;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class OrderCriteria {
  private Long id;
  private Long userId;
  private Integer paymentMethod;
  private String address;
  private String province;
  private String ward;
  private String district;
  private String receiver;
  private String phone;
  private Integer state;
  private String orderCode;
  private Date createDate;

  public Specification<Order> getCriteria() {
    return new Specification<Order>() {
      private static final long serialVersionUID = 1L;
      @Override
      public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();
        if(getId() != null){
          predicates.add(cb.equal(root.get("id"), getId()));
        }

        if(getUserId() != null){
          predicates.add(cb.equal(root.get("user").get("id"), getUserId()));
        }
        if(getState() != null){
          predicates.add(cb.equal(root.get("state"), getState()));
        }
        if(getPaymentMethod() != null){
          predicates.add(cb.equal(root.get("paymentMethod"), getPaymentMethod()));
        }
        if(!StringUtils.isEmpty(getProvince())){
          predicates.add(cb.like(cb.lower(root.get("province")), "%" + getProvince().toLowerCase() + "%"));
        }
        if(!StringUtils.isEmpty(getWard())){
          predicates.add(cb.like(cb.lower(root.get("ward")), "%" + getWard().toLowerCase() + "%"));
        }
        if(!StringUtils.isEmpty(getDistrict())){
          predicates.add(cb.like(cb.lower(root.get("district")), "%" + getDistrict().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(getReceiver())) {
          predicates.add(cb.like(cb.lower(root.get("receiver")), "%" + getReceiver().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(getPhone())) {
          predicates.add(cb.like(cb.lower(root.get("phone")), "%" + getPhone().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(getAddress())) {
          predicates.add(cb.like(cb.lower(root.get("address")), "%" + getAddress().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(getOrderCode())) {
          predicates.add(cb.equal(
                  cb.function("binary", String.class, root.get("orderCode")),
                  cb.literal(getOrderCode())
          ));
        }
        if (getCreateDate() != null) {
          Expression<Date> createDateExpression = root.get("createdDate");
          Expression<Date> createDateToCompare = cb.literal(getCreateDate());

          // Lấy chỉ ngày, tháng và năm từ createDateExpression và createDateToCompare
          Expression<Date> createDateExpressionDateOnly = cb.function(
                  "DATE",
                  Date.class,
                  createDateExpression
          );
          Expression<Date> createDateToCompareDateOnly = cb.function(
                  "DATE",
                  Date.class,
                  createDateToCompare
          );

          predicates.add(cb.equal(createDateExpressionDateOnly, createDateToCompareDateOnly));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }
    };
  }
}
