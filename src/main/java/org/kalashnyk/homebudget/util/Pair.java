package org.kalashnyk.homebudget.util;

import lombok.Getter;

/**
 * Created by Sergii on 13.03.2017.
 */
@Getter
public class Pair<K extends Comparable, V extends Comparable> implements Comparable<Pair> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(Pair o) {
        if(getValue().compareTo(o.getValue()) != 0) {
            return o.getValue().compareTo(getValue());
        } else {
            return getKey().compareTo(o.getKey());
        }
    }
}
