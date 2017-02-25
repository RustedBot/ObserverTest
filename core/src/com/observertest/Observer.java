package com.observertest;

import com.observertest.enums.Event;
import com.observertest.gameobjects.GameObject;

/**
 * Created by Carl on 16/02/2017.
 */
public interface Observer
{
    boolean respond(GameObject gameObject, Event event);
}
