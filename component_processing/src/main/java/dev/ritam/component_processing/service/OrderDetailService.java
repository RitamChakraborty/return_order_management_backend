package dev.ritam.component_processing.service;

import dev.ritam.component_processing.entity.OrderDetail;
import dev.ritam.component_processing.model.ProcessRequest;
import dev.ritam.component_processing.model.ProcessResponse;
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

    public void addOrderDetail(
            String customerEmail,
            ProcessRequest processRequest,
            ProcessResponse processResponse
    ) {
        OrderDetail orderDetail = OrderDetail.builder()
                .customerEmail(customerEmail)
                .processRequest(processRequest)
                .processResponse(processResponse)
                .build();
        orderDetailRepository.saveAndFlush(orderDetail);
        log.info(String.format("Order Detail added : %s", orderDetail));
    }

    public List<OrderDetail> getOrderDetailsByCustomerEmail(String customerEmail) {
        return orderDetailRepository.findOrderDetailByCustomerEmail(customerEmail);
    }
}
