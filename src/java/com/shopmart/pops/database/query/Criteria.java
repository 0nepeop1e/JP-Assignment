package com.shopmart.pops.database.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class which represent criteria
 */
public class Criteria extends Query{
    public static final int AND = 0;
    public static final int OR = 1;
    public static final int XOR = 2;
    public static final int NAND = 3;
    public static final int NOR = 4;
    public static final int XNOR = 5;

    public Criteria(String sql, Query query){
        super(String.format(sql, query.sql));
        this.params = query.params;
    }

    public Criteria(String sql, Object[] ...params) {
        super(sql, params);
    }

    public Criteria not(){
        return new Criteria(
                String.format("NOT (%s)", this.sql),
                this.params);
    }

    public Criteria and(Criteria ...criterias){
        List<Criteria> ps = Arrays.asList(criterias);
        ps.add(0, this);
        return Criteria.join(AND, (Criteria[]) ps.toArray());
    }

    public Criteria or(Criteria ...criterias){
        List<Criteria> ps = Arrays.asList(criterias);
        ps.add(0, this);
        return Criteria.join(OR, (Criteria[]) ps.toArray());
    }

    public Criteria xor(Criteria ...criterias){
        List<Criteria> ps = Arrays.asList(criterias);
        ps.add(0, this);
        return Criteria.join(XOR, (Criteria[]) ps.toArray());
    }

    public Criteria nand(Criteria ...criterias){
        List<Criteria> ps = Arrays.asList(criterias);
        ps.add(0, this);
        return Criteria.join(NAND, (Criteria[]) ps.toArray());
    }

    public Criteria nor(Criteria ...criterias){
        List<Criteria> ps = Arrays.asList(criterias);
        ps.add(0, this);
        return Criteria.join(NOR, (Criteria[]) ps.toArray());
    }

    public Criteria xnor(Criteria ...criterias){
        List<Criteria> ps = Arrays.asList(criterias);
        ps.add(0, this);
        return Criteria.join(XNOR, (Criteria[]) ps.toArray());
    }

    public static Criteria join(int logic, Criteria ...criterias){
        Criteria result = new Criteria("");
        List<Object> params =  new ArrayList<>();
        switch(logic){
            case AND:case OR:
                for(int i = 0; i < criterias.length; i++){
                    result.sql += String.format("(%s)", criterias[0].sql);
                    params.addAll(Arrays.asList(criterias[0].params));
                    if(i < criterias.length - 1)
                        result.sql += logic == AND ? " AND " : " OR ";
                }
                break;
            case NAND:case NOR:
                return Criteria.join(logic == NAND ? AND : OR, criterias).not();
            case XOR:case XNOR:
                if(criterias.length != 2) return null;
                Criteria c1 = criterias[0], c2 = criterias[1];
                result.sql = String.format("(%s) %s (%s)",
                        c1.sql, logic == XOR ? "<>": "=", c2.sql);
                params.addAll(Arrays.asList(c1.params));
                params.addAll(Arrays.asList(c2.params));
                break;
            default: return null;
        }
        result.params = params.toArray();
        return result;
    }
}
