package com.lboric.soccerdnd;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class SpringSoccerDndApplicationTests {

    @Test
    @DisplayName("GIVEN application sucessufly runs, WHEN main method is called, THEN context should load")
    void contextLoads() {
    }

    @Test
    @DisplayName("GIVEN application attempts to run, WHEN main method is called, THEN application should start")
    void mainTest() {
        try (final MockedStatic<SpringApplication> mockedMainClass = mockStatic(SpringApplication.class)) {
            mockedMainClass.when(() -> SpringApplication.run(SpringSoccerDndApplication.class, "foo", "bar"))
                .thenReturn(Mockito.mock(ConfigurableApplicationContext.class));

            SpringSoccerDndApplication.main(new String[] { "foo", "bar" });

            mockedMainClass.verify(() -> SpringApplication.run(SpringSoccerDndApplication.class, "foo", "bar"));
        }
    }

}
