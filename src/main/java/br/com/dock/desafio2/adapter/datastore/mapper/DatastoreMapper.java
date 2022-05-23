package br.com.dock.desafio2.adapter.datastore.mapper;

public interface DatastoreMapper<T1, T2> {
    T2 mapToDatabase (T1 data);

    T1 mapFromDatabase(T2 data);
}
