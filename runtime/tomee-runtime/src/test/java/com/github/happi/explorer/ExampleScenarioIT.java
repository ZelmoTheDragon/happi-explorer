package com.github.happi.explorer;


import java.util.stream.Stream;

import com.github.happi.explorer.junit.scenario.Scenarios;
import com.github.happi.explorer.junit.scenario.model.Scenario;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ExampleScenarioIT {

    ExampleScenarioIT() {
    }

    @MethodSource("createArguments")
    @ParameterizedTest
    public void processScenario(final Scenario scenario, final TestReporter reporter) {
        Scenarios.callEndpoint(scenario, reporter);
    }

    private static Stream<Arguments> createArguments() {
        return Scenarios.createArguments();
    }
}
