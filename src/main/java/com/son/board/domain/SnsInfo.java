package com.son.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SnsInfo {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "sns_id", nullable = false, unique = true)
    private String snsId;

    @Column(name = "sns_type", nullable = false, length = 10)
    private String snsType;

    @Column(name = "sns_name")
    private String snsName;
}
