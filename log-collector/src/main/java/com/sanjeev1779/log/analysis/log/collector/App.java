package com.sanjeev1779.log.analysis.log.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.error("hiiiii {}", "name");
    }
}
