package com.invadermonky.omniwand.util;

import com.invadermonky.omniwand.Omniwand;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {
    private static final Logger LOG = Logger.getLogger(Omniwand.MOD_ID);

    public static void debug(String obj) {
        LOG.log(Level.INFO, obj);
    }

    public static void error(String obj) {
        LOG.log(Level.WARNING, obj);
    }

    public static void info(String obj) {
        LOG.info(obj);
    }

}
