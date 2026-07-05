package Guru99.common.listener;

import org.testng.ITestListener;
import org.testng.ITestResult;
import Guru99.tests.BaseTest;
import Guru99.common.keywords.WebUI;

public class TestListener implements ITestListener {

  @Override
  public void onTestStart(ITestResult result) {
    System.out.println("Starting test case: " + result.getName());
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    System.out.println("Test case PASSED: " + result.getName());
  }

  @Override
  public void onTestFailure(ITestResult result) {
    System.out.println("Test case FAILED: " + result.getName());
    Object testClass = result.getInstance();
    if (testClass instanceof BaseTest) {
      WebUI webUI = ((BaseTest) testClass).getWebUI();
      if (webUI != null) {
        webUI.attachmentScreenshot();
      }
    }
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    System.out.println("Test case SKIPPED: " + result.getName());
  }
}