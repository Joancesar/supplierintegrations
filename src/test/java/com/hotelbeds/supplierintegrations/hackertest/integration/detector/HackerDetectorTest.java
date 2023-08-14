package com.hotelbeds.supplierintegrations.hackertest.integration.detector;

import com.hotelbeds.supplierintegrations.hackertest.detector.HackerDetector;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class HackerDetectorTest {

    @Autowired
    HackerDetector hackerDetector;

    @Test
    void parseLine_withConsecutiveEpochSeconds_thenParseCorrectly() throws IOException {
        List<String> inputLines = readLinesFromResource("fixtures/consecutive_input.csv");
        String expectedTextOutput = String.join("\n", readLinesFromResource("fixtures/consecutive_output.txt"));

        long startEpoch = Instant.now().getEpochSecond() - 200;

        List<String> processedLines = new ArrayList<>();
        for (int i = 0; i < inputLines.size(); i++) {
            String processedLine = inputLines.get(i).replace("{{consecutiveEpoch}}", String.valueOf(startEpoch + i));
            String result = hackerDetector.parseLine(processedLine);
            processedLines.add(result);
        }

        String actualTextOutput = String.join("\n", processedLines);
        assertEquals(expectedTextOutput, actualTextOutput);
    }

    @Test
    void parseLine_withSameEpochSeconds_thenParseCorrectly() throws IOException {
        List<String> inputLines = readLinesFromResource("fixtures/sameEpoch_input.csv");
        String expectedTextOutput
                = String.join("\n", readLinesFromResource("fixtures/sameEpoch_output.txt"));

        long sameEpoch = Instant.now().getEpochSecond() - 200;

        List<String> processedLines = new ArrayList<>();
        for (String inputLine : inputLines) {
            String processedLine = inputLine.replace("{{sameEpoch}}", String.valueOf(sameEpoch));
            String result = hackerDetector.parseLine(processedLine);
            processedLines.add(result);
        }

        String actualTextOutput = String.join("\n", processedLines);
        assertEquals(expectedTextOutput, actualTextOutput);
    }

    @Test
    void parseLine_withUnorderedEpoch_thenParseCorrectly() throws IOException {
        List<String> inputLines = readLinesFromResource("fixtures/unordered_input.csv");
        String expectedTextOutput
                = String.join("\n", readLinesFromResource("fixtures/unordered_output.txt"));

        long startEpoch = Instant.now().getEpochSecond() - 200;
        String epochString = String.valueOf(startEpoch);
        epochString = epochString.substring(0, epochString.length() - 2);

        List<String> processedLines = new ArrayList<>();
        for (String inputLine : inputLines) {
            String processedLine = inputLine.replace("{{unorderedEpoch}}", epochString);
            String result = hackerDetector.parseLine(processedLine);
            processedLines.add(result);
        }

        String actualTextOutput = String.join("\n", processedLines);
        assertEquals(expectedTextOutput, actualTextOutput);
    }

    @Test
    void parseLine_withCombiningCases_thenParseCorrectly() throws IOException {
        List<String> inputLines = readLinesFromResource("fixtures/combination_input.csv");
        String expectedTextOutput
                = String.join("\n", readLinesFromResource("fixtures/combination_output.txt"));

        long startEpoch = Instant.now().getEpochSecond() - 100;
        String epochString = String.valueOf(startEpoch);
        epochString = epochString.substring(0, epochString.length() - 2);

        List<String> processedLines = new ArrayList<>();
        for (String inputLine : inputLines) {
            String processedLine = inputLine.replace("{{epoch}}", epochString);
            String result = hackerDetector.parseLine(processedLine);
            processedLines.add(result);
        }

        String actualTextOutput = String.join("\n", processedLines);
        assertEquals(expectedTextOutput, actualTextOutput);
    }

    private List<String> readLinesFromResource(String resourcePath) throws IOException {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }
            return new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.toList());
        }
    }
}
