# Squid Tests

## Introduction
This project contains some Appium tests for executing against the Squid Android app.

The tests can be run against a local emulated device, but are configured to execute against a real device in BroswerStack (https://www.browserstack.com/).

## Pre-requisites
1. You should have Maven installed
2. To run the tests in BrowserStack, you will need to setup an account. There is a free trial which I used.
3. You will also need to have a pre-created account in the Squid app.

## Running the tests against BrowserStack
1. Once you have a BrowserStack account, you can populate your username and accessKey in the browserstack.yml file. 
2. The email and password for your Squid account needs to be provided to the tests. Currently, they need to be hardcoded in the TestDiscoveryFeature file. 
3. To run the tests, you will need to add the path to the browserstack sdk jar file to your VM options: 
(-javaagent:</full/.m2-repo/path/to/browserstack-java-sdk.jar>). Details can be found here: https://www.browserstack.com/docs/app-automate/appium/getting-started/java/integrate-your-tests
4. BrowserStack provides a dashboard report of the test execution
