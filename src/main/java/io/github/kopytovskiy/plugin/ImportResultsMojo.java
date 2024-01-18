package io.github.kopytovskiy.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import org.json.JSONObject;
import io.github.kopytovskiy.helpers.AuthorizationHelper;
import io.github.kopytovskiy.helpers.FileHelper;
import io.github.kopytovskiy.helpers.XmlHelper;
import io.github.kopytovskiy.helpers.XrayImportHelper;

import java.io.File;
import java.util.Map;

@Slf4j
@Mojo(name = "import-results", defaultPhase = LifecyclePhase.INITIALIZE)
public class ImportResultsMojo extends AbstractMojo {

    @Parameter(property = "projectKey", required = true)
    private String projectKey;

    @Parameter(property = "issueType", required = true)
    private String issueType;

    @Parameter(property = "testPlanKey")
    private String testPlanKey;

    @Parameter(property = "pathToJUnitReport", defaultValue = "target/TEST-junit-jupiter.xml")
    private String pathToJUnitReport;

    @Parameter(property = "xrayClientId", required = true)
    private String xrayClientId;

    @Parameter(property = "xrayClientSecret", required = true)
    private String xrayClientSecret;

    @Parameter(property = "customJiraFields")
    private Map<String, String> customJiraFields;

    @Parameter(property = "summary", defaultValue = "Test Execution - Automation Tests Result (${session.request.startTime})")
    private String summary;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        FileHelper.writeToFile(FileHelper.getTestsResultsFileFilepath(pathToJUnitReport),
                new JSONObject()
                        .put("tests", XmlHelper.convertXmlToJson(pathToJUnitReport)));

        JSONObject resultsJiraFieldsData = new JSONObject();
        resultsJiraFieldsData.put("project", new JSONObject().put("key", projectKey));
        resultsJiraFieldsData.put("issuetype", new JSONObject().put("id", issueType));

        if (!customJiraFields.isEmpty()) {
            for (Map.Entry<String, String> customField : customJiraFields.entrySet()) {
                String key = customField.getKey();
                String value = customField.getValue();
                resultsJiraFieldsData.put(key, new JSONObject().put("value", value));
            }
        }
        resultsJiraFieldsData.put("summary", summary);

        JSONObject resultsXrayFieldsData = new JSONObject();
        resultsXrayFieldsData.put("testPlanKey", testPlanKey);

        FileHelper.writeToFile(FileHelper.getInfoFileFilepath(pathToJUnitReport),
                new JSONObject()
                        .put("fields", resultsJiraFieldsData)
                        .put("xrayFields", resultsXrayFieldsData));

        String authToken = AuthorizationHelper.userAuthorization(xrayClientId, xrayClientSecret);

        XrayImportHelper.importResults(authToken,
                new File(FileHelper.getTestsResultsFileFilepath(pathToJUnitReport)),
                new File(FileHelper.getInfoFileFilepath(pathToJUnitReport)));

    }
}
