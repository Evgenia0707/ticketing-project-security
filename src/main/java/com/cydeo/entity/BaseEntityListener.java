package com.cydeo.entity;

import com.cydeo.entity.common.UserPrincipal;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component      //dont have separate- BUT lits like business logic // Bean for injection
public class BaseEntityListener extends AuditingEntityListener {//when any changes in appl - listen base entity//new class - need extend
//in DB when create/update info (who, time) used BaseEntity - assign this info (security do it)

//entity listener assign//  listener working

    @PrePersist//checking events - before happen - linked
    private void onPrePersist(BaseEntity baseEntity) {//

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.setInsertDateTime(LocalDateTime.now());//
        baseEntity.setLastUpdateDateTime(LocalDateTime.now());

        if (authentication != null && !authentication.getName().equals("anonymousUser")) {//validation
            Object principal = authentication.getPrincipal();
            baseEntity.setInsertUserId(((UserPrincipal) principal).getId());// userPrincipal need id(give me userPrincipal id)
            baseEntity.setLastUpdateUserId(((UserPrincipal) principal).getId());
        }
    }

    @PreUpdate
    private void onPreUpdate(BaseEntity baseEntity) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.setLastUpdateDateTime(LocalDateTime.now());

        if (authentication != null && !authentication.getName().equals("anonymousUser")) {//validation
            Object principal = authentication.getPrincipal();
            baseEntity.setLastUpdateUserId(((UserPrincipal) principal).getId());
        }
    }
}