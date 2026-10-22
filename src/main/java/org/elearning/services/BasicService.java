package org.elearning.services;

import java.util.List;

public interface BasicService <I,O>{

    void create(I request);
    void update(final String id, final I request);
    O findById(final String id);
    void deleteById(final String id);
    List<O> findAll();
}
