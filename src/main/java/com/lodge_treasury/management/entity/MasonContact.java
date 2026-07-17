package com.lodge_treasury.management.entity;

import com.lodge_treasury.management.enums.ContactPreference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mason_contacts")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class MasonContact extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id", unique = true, nullable = false)
    private Integer contactId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mason_id", nullable = false, unique = true)
    private Mason mason;

    @Column(name = "mobile", length = 20, nullable = false, unique = true)
    private String mobile;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "address", length = 255)
    private String address = null;

    @Column(name = "emergency_contact_name", length = 100, nullable = false)
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone", length = 20, nullable = false)
    private String emergencyContactPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "contact_preference", nullable = false)
    private ContactPreference contactPreference;

    @Column(name = "notes")
    private String notes = null;

}
