package org.elearning.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

import  static  jakarta.persistence.GenerationType.UUID;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@FilterDef(
        name = "tenantFilter",
        parameters =  @ParamDef(name= "tenantId" , type= String.class),
        defaultCondition = "tenant_id= :tenantId"
)
@Filter(name ="tenantFilter")
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    @Column(name = "id" , nullable = false, updatable = false)
    private String id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @CreatedDate
    @Column(name = "created_at" , nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at"  , updatable = false)
    private LocalDate updatedAt;

    @Column(name="deleted" , nullable = false)
    private Boolean deleted;

    @PrePersist
    protected  void onCreate(){
        if(this.deleted ==null){
            this.deleted=false;
        }

        if(tenantId==null){
            this.tenantId ="default";
        }
    }
}
