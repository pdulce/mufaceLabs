package com.arch.mfc.infra.outputport;

import java.util.List;

public interface CommandRepository {

    public <T> T save( T reg );

    public <T> T getById( Long id, Class<T> clazz );

    public <T> List<T> getAll( Class<T> clazz );
    
}
