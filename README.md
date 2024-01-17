# Xray XML-To-JSON Maven Plugin

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A Maven Plugin for integrating Java (JUnit5) automation tests results with Xray Test Case Management System manual tests.

## Features

- Import Java (JUnit5) automation results to Xray manual tests.


## Getting Started

1. Add the plugin to your Maven project:

    ```xml
    <build>
        <plugins>
            <plugin>
                <groupId>com.mk</groupId>
                <artifactId>xray-junit5-json-plugin</artifactId>
                <version>0.0.1</version>
            </plugin>
        </plugins>
    </build>
    ```

2. Configure the plugin in your `pom.xml`.

    ```xml
    <configuration>
        <projectKey>${projectKey}</projectKey>
        <issueType>${issueType}</issueType>
        <testPlanKey>${testPlanKey}</testPlanKey>
        <pathToJUnitReport>${pathToJUnitReport}</pathToJUnitReport>
        <xrayClientId>${xrayClientId}</xrayClientId>
        <xrayClientSecret>${xrayClientSecret}</xrayClientSecret>
    </configuration>
    ```

- `projectKey` (required): The key of the Xray project (e.g. "QA").
- `issueType` (required): The number of issue type to be created (e.g., "10007").
- `testPlanKey`: The key of the Xray test plan. If not provided, the Test Execution will be **not** linked to any.
- `pathToJUnitReport` (default: "target/TEST-junit-jupiter.xml"): The path to the JUnit XML report file.
- `xrayClientId` (required): The client ID for authenticating with the Xray API.
- `xrayClientSecret` (required): The client secret for authenticating with the Xray API.


3. Execute the plugin:
    ```bash
    mvn xray-junit5-json-plugin:import-results
    ```

## Contributing

Contributions are welcome!

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE.md](LICENSE.md) file for details.