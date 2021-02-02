package com.bookshop.mybookshop.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity(name = "user_contacts")
@Data
@ToString(exclude = "id")
public class UserContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactType type;

    @Column(nullable = false)
    private boolean approved;

    private String code;

    @Column(name = "code_trials")
    private Integer codeTrials;

    @Column(name = "code_time", nullable = false)
    private LocalDateTime codTime;

    @Column(nullable = false)
    private String contact;
}
