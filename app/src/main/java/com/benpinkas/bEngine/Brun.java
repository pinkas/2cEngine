package com.benpinkas.bEngine;

import com.benpinkas.bEngine.object.BglObject;

/**
 * Created by Ben on 2/2/14.
 */
public interface Brun <V>  {

    V exec(BglObject obj) throws java.lang.Exception;
}
