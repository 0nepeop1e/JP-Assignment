package com.shopmart.pops.manager.data;

import com.shopmart.pops.POPSUtils;
import com.shopmart.pops.manager.data.abstracts.AbstractManager;
import com.shopmart.pops.manager.data.entries.Supplier;

import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by 0nepeop1e on 4/25/16.
 */
public class SupplierManager extends AbstractManager<Supplier> {

    public Collection<Supplier> findByName(String glob){
        return this.data.stream().filter(s->Pattern.matches(
                POPSUtils.globToRegEx(glob), s.getName()))
                .collect(Collectors.toList());
    }

    public Collection<Supplier> findByEmail(String glob){
        return this.data.stream().filter(s->Pattern.matches(
                POPSUtils.globToRegEx(glob), s.getEmail()))
                .collect(Collectors.toList());
    }

    public Collection<Supplier> findByPhone(String glob){
        return this.data.stream().filter(s->Pattern.matches(
                POPSUtils.globToRegEx(glob), s.getPhone()))
                .collect(Collectors.toList());
    }

    public Collection<Supplier> findByFax(String glob){
        return this.data.stream().filter(s->Pattern.matches(
                POPSUtils.globToRegEx(glob), s.getFax()))
                .collect(Collectors.toList());
    }
    @Override
    public Class<Supplier> getManageType() {
        return Supplier.class;
    }

}
