package com.expressbank.service.impl;

import com.expressbank.dto.CommonResponse;
import com.expressbank.dto.deposit.DepositRequest;
import com.expressbank.dto.order.OrderDTO;
import com.expressbank.dto.order.OrderItemDTO;
import com.expressbank.dto.order.request.BuyOrderRequest;
import com.expressbank.dto.order.request.SellOrderRequest;
import com.expressbank.email.EmailSender;
import com.expressbank.entity.DepositEntity;
import com.expressbank.entity.OrderEntity;
import com.expressbank.entity.StockEntity;
import com.expressbank.entity.UserEntity;
import com.expressbank.enums.ResponseEnum;
import com.expressbank.exception.CommonException;
import com.expressbank.repository.DepositRespository;
import com.expressbank.repository.OrderRespository;
import com.expressbank.service.DepositService;
import com.expressbank.service.OrderService;
import com.expressbank.service.StockService;
import com.expressbank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    @Autowired
    private OrderRespository orderRespository;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private DepositService depositService;

    @Autowired
    private DepositRespository depositRespository;


    @Override
    public ResponseEntity<CommonResponse> buyOrder(BuyOrderRequest request) {
        UserEntity user = userService.findUserById(request.getUserId());
        StockEntity stockEntity = stockService.findStockById(request.getStockId());

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStockEntity(stockEntity);
        orderEntity.setCreatedDate(new Date());
        orderEntity.setQuantity(request.getQuantity());
        orderEntity.setUserEntity(user);
        orderRespository.save(orderEntity);

        OrderDTO orderDTO = listOrderItems(user);

        List<Object> objectList = new ArrayList<>();
        objectList.add(user);
        objectList.add(request);
        objectList.add(orderDTO);

        return new ResponseEntity<>(CommonResponse.success(objectList), HttpStatus.OK);
    }

    public OrderDTO listOrderItems(UserEntity user) {
        List<OrderEntity> orderList = orderRespository.findAllByUserEntityOrderByCreatedDateDesc(user);
        DepositEntity depositEntity = depositRespository.findDepositEntityByUserEntity(user).orElseThrow(
                () -> new CommonException(ResponseEnum.DEPOSIT_NOT_FOUND));

        double totalCost = 0;
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for (OrderEntity orderEntity : orderList) {

            if (orderEntity.getQuantity() < orderEntity.getStockEntity().getLastPrice()
                    && orderEntity.getQuantity() < depositEntity.getBalance()) {
                OrderItemDTO orderItemDTO = new OrderItemDTO(orderEntity);

                totalCost += orderEntity.getQuantity();
                orderItemDTOS.add(orderItemDTO);
                
                DepositRequest depositRequest = DepositRequest.builder()
                        .amount(orderEntity.getQuantity())
                        .iban(depositEntity.getIban())
                        .build();
                depositService.reduceBalance(depositRequest);
            }
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTotalCost(totalCost);

        orderDTO.setOrderItemDTOList(orderItemDTOS);

        // emailSender.sendNotifications(user.getEmail(), buildEmail(user.getName(), totalCost));

        return orderDTO;
    }

    @Override
    public ResponseEntity<CommonResponse> sellOrder(SellOrderRequest request) {
        return null;
    }


    private String buildEmail(String name, double totalCost) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your place order</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for choosing us. The amount of your total order: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + totalCost + "$" + "</p></blockquote>\n <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
