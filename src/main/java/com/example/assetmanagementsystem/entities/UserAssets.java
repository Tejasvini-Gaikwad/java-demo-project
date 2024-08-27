package com.example.assetmanagementsystem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name="user_assets")
@Getter
@Setter
public class UserAssets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Users user;
    @ManyToOne
    @JoinColumn(name = "asset_id")

    private Assets asset;
    private Integer created_by;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date created_at;
    private Integer updated_by;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;
    public UserAssets() {}
    public UserAssets(Users user, Assets asset, String status) {
        this.user = user;
        this.asset = asset;
        this.status = "ACCEPTED".equalsIgnoreCase(status) ? RequestStatus.ACCEPTED.name() : RequestStatus.PENDING.name();
        this.created_at = new Date();
        this.updated_at = new Date();
        this.created_by=1;
        this.updated_by=1;
    }

    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
