package com.crediline.utils;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by dimer on 1/21/14.
 */
@Component("utils")
public class CommonUtils implements Serializable {

    private static final long serialVersionUID = 5562766037108564445L;

    public static String getRandomImageName() {
        int i = (int) (Math.random() * 10000000);

        return String.valueOf(i);
    }

}
