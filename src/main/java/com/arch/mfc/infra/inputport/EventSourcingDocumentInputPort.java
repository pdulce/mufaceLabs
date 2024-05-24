package com.arch.mfc.infra.inputport;

import java.util.List;
import java.util.Map;

public interface EventSourcingDocumentInputPort<T> {

    void insertEvent(T object);

    List<T> getAll();

}
