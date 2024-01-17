package org.mk.helpers;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

public class XmlHelper {

    /**
     * Method for parsing JUnit5 XML and convert it to JSON file.
     *
     * @param filepath Path to JUnit5 report (e.g. "src/test/resources/TEST-junit-jupiter.xml");
     *
     * @return <b>JSONArray</b> object
     */
    public static JSONArray convertXmlToJson(String filepath) {

        JSONArray testArray = new JSONArray();

        try {
            File inputFile = new File(filepath);
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputFile);

            List<Node> nodes = document.selectNodes("//testsuite//testcase");
            
            for (Node node : nodes) {
                JSONObject test = new JSONObject();
                test.put("testKey", node.selectSingleNode("properties/property[@name='testrun_customfields']/item[@name='testKey']").getText());
                if (node.selectSingleNode("failure") == null) {
                    test.put("status", "PASSED");
                } else {
                    test.put("status", "FAILED");
                }
                testArray.put(test);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return testArray;
    }
}
