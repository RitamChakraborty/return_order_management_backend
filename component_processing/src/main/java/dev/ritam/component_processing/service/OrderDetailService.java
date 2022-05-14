package dev.ritam.component_processing.service;

import dev.ritam.component_processing.entity.OrderDetail;
import dev.ritam.component_processing.exception.OrderDetailNotFoundException;
import dev.ritam.component_processing.model.OrderRequest;
import dev.ritam.component_processing.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetail addOrderDetail(OrderRequest orderRequest, String customerEmail) {
        var orderDetail = OrderDetail.builder()
                .customerEmail(customerEmail)
                .processRequest(orderRequest.getProcessRequest())
                .processResponse(orderRequest.getProcessResponse())
                .build();
        orderDetailRepository.saveAndFlush(orderDetail);
        log.info(String.format("Order Detail added : %s", orderDetail));
        return orderDetail;
    }

    public List<OrderDetail> getOrderDetailsByCustomerEmail(String customerEmail) {
        return orderDetailRepository.findOrderDetailByCustomerEmail(customerEmail);
    }

    public OrderDetail getOrderDetailByCustomerEmailAndOrderId(
            String customerEmail,
            Long orderId
    ) {
        return orderDetailRepository.findOrderDetailByCustomerEmailAndOrderId(
                customerEmail,
                orderId
        ).orElseThrow(() -> new OrderDetailNotFoundException(
                String.format(
                        "Order detail with customer email : %s and order id : %s not found",
                        customerEmail, orderId
                ))
        );
    }
}
