package com.shopmart.pops.manager.data;

import com.shopmart.pops.manager.data.abstracts.AbstractManager;
import com.shopmart.pops.manager.data.enums.RequestStatus;
import com.shopmart.pops.manager.data.objects.Request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by 0nepeop1e on 4/26/16.
 */
public class RequestManager extends AbstractManager<Request> {

    public Collection<Request> getAllAfter(LocalDateTime time){
        return this.data.stream().filter(r->
                r.getTime().compareTo(time) >= 0)
                .collect(Collectors.toList());
    }

    public Collection<Request> getAllBefore(LocalDateTime time){
        return this.data.stream().filter(r->
                r.getTime().compareTo(time) < 0)
                .collect(Collectors.toList());
    }

    public Collection<Request> getAllBetween(LocalDateTime lower, LocalDateTime upper){
        return this.data.stream().filter(r->
                r.getTime().compareTo(lower) >= 0 &&
                r.getTime().compareTo(upper) <= 0)
                .collect(Collectors.toList());
    }

    public Collection<Request> getAllByStatus(RequestStatus status){
        return this.data.stream().filter(r->
                r.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public Class<Request> getManageType() {
        return Request.class;
    }
}
