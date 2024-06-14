package com.mfc.infra.output.port;

import java.util.List;


public interface RelationalServicePort<T, D, ID> {

    D crear(D entity);

    D actualizar(D entity);

    void borrar(D entity);

    void borrar(List<D> entities);
    void borrar();

    D buscarPorId(ID id);

    List<D> buscarTodos();

    List<D> buscarPorCampoValor(String fieldName, Object fieldValue);

    String getDocumentEntityClassname();

}
