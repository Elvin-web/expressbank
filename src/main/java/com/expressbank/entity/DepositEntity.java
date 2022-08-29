package com.expressbank.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "deposit", schema = "expressbank")
@NoArgsConstructor
public class DepositEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double balance = 0.0;

    private String iban;

    private boolean accountBlocked = false;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity userEntity;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
