package org.mk.helpers;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

@Slf4j
public class FileHelper {

    /**
     * Method for creating JSON file.
     *
     * @param filepath Path to file where it should be stored;
     * @param jsonObject <b>JSONObject</b> that should be written in file;
     */
    public static void writeToFile(String filepath, JSONObject jsonObject) {
        try {
            FileWriter file = new FileWriter(filepath);
            file.write(jsonObject.toString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for getting path to JSON file with Jira fields.
     *
     * @param junitXmlFilepath Path to JUnit5 report (e.g. "src/test/resources/TEST-junit-jupiter.xml");
     *
     * @return <b>String</b> with filepath;
     */
    public static String getInfoFileFilepath(String junitXmlFilepath) {
        return Paths.get(junitXmlFilepath).getParent().toString() + "/info-report.json";
    }

    /**
     * Method for getting path to JSON file with test results.
     *
     * @param junitXmlFilepath Path to JUnit5 report (e.g. "src/test/resources/TEST-junit-jupiter.xml");
     *
     * @return <b>String</b> with filepath;
     */
    public static String getTestsResultsFileFilepath(String junitXmlFilepath) {
        return Paths.get(junitXmlFilepath).getParent().toString() + "/results-report.json";
    }
}
