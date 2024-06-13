package com.mfc.infra.output.port;

import java.util.List;


public interface RelationalServicePort<T, ID> {

    T crear(T entity);

    T actualizar(T entity);

    void borrar(T entity);

    void borrar(List<T> entities);
    void borrar();

    T buscarPorId(ID id);

    List<T> buscar();

    List<T> buscarPorCampoValor(String fieldName, Object fieldValue);

    String getDocumentEntityClassname();

}
