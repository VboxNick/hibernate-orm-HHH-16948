package org.hibernate.bugs;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;
import java.util.UUID;

@Entity
public class Company {

    @Id
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date establishedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getEstablishedAt() {
        return establishedAt;
    }

    public void setEstablishedAt(Date establishedAt) {
        this.establishedAt = establishedAt;
    }
}