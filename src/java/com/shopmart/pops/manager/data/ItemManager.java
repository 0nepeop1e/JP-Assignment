package com.shopmart.pops.manager.data;

import com.shopmart.pops.POPSUtils;
import com.shopmart.pops.manager.data.abstracts.AbstractManager;
import com.shopmart.pops.manager.data.objects.Item;

import java.util.Collection;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by 0nepeop1e on 4/25/16.
 */
public class ItemManager extends AbstractManager<Item> {

    public Collection<Item> findByName(String glob){
        return data.stream().filter(i->Pattern.matches(
                POPSUtils.globToRegEx(glob),i.getName()))
                .collect(Collectors.toList());
    }

    public Collection<Item> findByBarcode(String glob){
        return data.stream().filter(i->Pattern.matches(
                POPSUtils.globToRegEx(glob),i.getBarcode()))
                .collect(Collectors.toList());
    }

    public Collection<Item> findBySupplierId(int supplierId){
        return data.stream().filter(i->true).collect(Collectors.toList());
    }

    @Override
    public Class<Item> getManageType() {
        return Item.class;
    }
}
