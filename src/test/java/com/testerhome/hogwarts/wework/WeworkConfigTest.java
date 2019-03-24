package com.testerhome.hogwarts.wework;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeworkConfigTest {

    @Test
    void load() {
        WeworkConfig.load("");
    }

    @Test
    void getInstance(){
        WeworkConfig.getInstance();
    }
}