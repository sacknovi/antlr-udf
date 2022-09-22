package edu.yu.pl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserDefinedFieldTest {

    private UDFParseTreeVisitor visitor;

    @BeforeEach
    void setUp() {
        visitor = new UDFParseTreeVisitor();
    }

    @ParameterizedTest
    @ValueSource(strings = {"2 + 4 * 6"})
    void evaluate(String udfText) {
        var udf = new UserDefinedField(udfText);
        Value result = udf.evaluate(visitor);
        assertEquals(26D, result.asDouble());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test_data_double.csv", numLinesToSkip = 2)
    void evaluate_numeric_expressions(String udfText, Double expected) {
        var udf = new UserDefinedField(udfText);
        Value result = udf.evaluate(visitor);
        assertEquals(expected, result.asDouble());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/test_data_string.csv", numLinesToSkip = 2)
    void evaluate_string_expressions(String udfText, String expected) {
        var udf = new UserDefinedField(udfText);
        Value result = udf.evaluate(visitor);
        assertEquals(expected, result.asString());
    }
}