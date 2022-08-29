package com.expressbank.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "stock", schema = "expressbank")
@NoArgsConstructor
public class StockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String company;
    @Lob
    @Column(length = 65555)
    private String description;
    private Double lastPrice;
    private String symbol;
}
