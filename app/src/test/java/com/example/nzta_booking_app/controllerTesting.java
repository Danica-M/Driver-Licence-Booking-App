package com.example.nzta_booking_app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.nzta_booking_app.models.Controller;

import org.junit.Test;

public class controllerTesting {


    @Test
    public void licenceValidation_positiveTest(){
        assertTrue(Controller.validateLicence("AB456983"));
    }

    @Test
    public void licenceValidation_negativeTest(){
        assertFalse(Controller.validateLicence("ABss5d6983"));
    }

    @Test
    public void numberOfSlotsGenerated(){
        assertEquals(16, Controller.getBookingSlots().size());
    }

    @Test
    public void stringValidation_positiveTest(){
        assertTrue(Controller.validateString("Adam"));
    }

    @Test
    public void stringValidation_negativeTest(){
        assertFalse(Controller.validateString("Ad86am"));
    }

}
