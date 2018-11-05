package com.f1reking.adapter;

import java.util.List;

/**
 * @author F1ReKing
 * @date 2018/10/23 16:27
 * @Description
 */
public interface CRUD<T> {

    void add(T item);

    void add(int position, T item);

    void addAll(List<T> items);

    void addAll(int position, List<T> items);

    void remove(T item);

    void remove(int position);

    void removeAll(List<T> items);

    void retainAll(List<T> items);

    void set(T oldItem, T newItem);

    void set(int position, T item);

    void replaceAll(List<T> items);

    boolean contains(T item);

    void clear();
}
