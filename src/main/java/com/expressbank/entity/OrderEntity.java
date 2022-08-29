package com.expressbank.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ToString
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "order", schema = "expressbank")
@NoArgsConstructor
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private StockEntity stockEntity;

    private Double quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
