package com.crediline.service;

import com.crediline.dao.EventDao;
import com.crediline.model.Event;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by dimer on 2/21/14.
 */

/**
 * This class should be changed to use AoP to update the accessible properties of the entities
 */

@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
@Service("eventService")
public class EventService extends AbstractService<Event> {

    @Inject
    private EventDao repository;

    public EventDao getRepository() {
        return repository;
    }

}
