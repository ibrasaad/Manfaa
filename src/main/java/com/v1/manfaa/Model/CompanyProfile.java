package com.v1.manfaa.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Entity
@Setter
@Getter
@NoArgsConstructor
public class CompanyProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @Column(columnDefinition = "varchar(20) not null")
    private String industry;

    @Column(columnDefinition = "int not null")
    private Integer team_size;

    @Column(columnDefinition = "TEXT not null")
    private String description;

    @Column(columnDefinition = "timestamp not null")
    private LocalDate created_at;

    @Column(columnDefinition = "boolean not null")
    private boolean is_subscriber;

//    @OneToOne
//    @MapsId
//    private User user;

}
