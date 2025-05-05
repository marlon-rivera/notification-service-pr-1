package com.pragma.notification_service.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notification_code")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotificationCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idOrder;
    private String phoneNumber;
    private String confirmationCode;

}
