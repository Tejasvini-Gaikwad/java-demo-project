package com.example.assetmanagementsystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="assets")
@Getter
@Setter
public class Assets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be empty")
    private String name;
    @NotBlank(message = "Description must not be empty")
    private String description;
    private Long created_by;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, nullable = false)
    private Date created_at;
    private Long updated_by;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;
    @PrePersist
    protected void onCreate() {
        created_at = new Date();
        updated_at = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated_at = new Date();
    }
    @OneToMany(mappedBy = "asset")
    private Set<UserAssets> userAssets;
    public Assets() {}

    public Assets(String name, String description, Integer created_by, Integer updated_by) {
        this.name = name;
        this.description=description;
        this.created_by = Long.valueOf(created_by);
        this.updated_by = Long.valueOf(updated_by);
    }
}
