package com.shopmart.pops.manager.data;

import com.shopmart.pops.POPS;
import com.shopmart.pops.manager.data.abstracts.AbstractManager;
import com.shopmart.pops.manager.data.abstracts.TimeStatusManager;
import com.shopmart.pops.manager.data.enums.OrderStatus;
import com.shopmart.pops.manager.data.entries.Order;
import com.shopmart.pops.manager.data.entries.Request;
import com.shopmart.pops.manager.data.entries.Supplier;
import com.shopmart.pops.manager.data.enums.RequestStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 0nepeop1e on 4/26/16.
 */
public class OrderManager extends AbstractManager<Order> implements TimeStatusManager<Order, OrderStatus> {

    public void addFromRequest(Request request){
        Map<Supplier, List<Map.Entry<Integer, Integer>>> grouped =
                request.getItemsAmount().entrySet().stream().collect(
                Collectors.groupingBy(e->POPS.getDataManager().getItemManager()
                .getById(e.getKey()).getSupplier()));
        request.setStatus(RequestStatus.Approved);
        request.setModifier(POPS.getDataManager().getUserManager().getCurrentUser().getId());
        for(Map.Entry<Supplier, List<Map.Entry<Integer, Integer>>> en : grouped.entrySet()){
            Order order = new Order();
            for(Map.Entry<Integer, Integer> sen : en.getValue())
                order.getItemsAmount().put(sen.getKey(), sen.getValue());
            order.setSupplier(en.getKey().getId());
            order.setCreator(request.getModifier().getId());
            order.setStatus(OrderStatus.Processing);
            this.add(order);
        }
    }

    @Override
    public Collection<Order> getAllAfter(LocalDateTime time){
        return this.data.stream().filter(r->
                r.getTime().compareTo(time) >= 0)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Order> getAllBefore(LocalDateTime time){
        return this.data.stream().filter(r->
                r.getTime().compareTo(time) < 0)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Order> getAllBetween(LocalDateTime lower, LocalDateTime upper){
        return this.data.stream().filter(r->
                r.getTime().compareTo(lower) >= 0 &&
                        r.getTime().compareTo(upper) <= 0)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Order> getAllByStatus(OrderStatus status){
        return this.data.stream().filter(r->
                r.getStatus() == status)
                .collect(Collectors.toList());
    }

    @Override
    public Class<Order> getManageType() {
        return Order.class;
    }
}
