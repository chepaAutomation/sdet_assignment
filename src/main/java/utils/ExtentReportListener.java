package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import java.util.Set;

public class ExtentReportListener
        implements ITestListener, ISuiteListener, IInvokedMethodListener, IExecutionListener, IAnnotationTransformer {

    public ExtentReports extentReports = ExtentManager.getReporter("./reports/" + File.separator + "PetStoreAutomationResults.html");
    public ExtentTest extentTest;

    // This belongs to ISuiteListener and will execute before the Suite start
    @Override
    public void onStart(ISuite arg0) {
        LogUtil.getLogger().info("About to begin executing Suite " + arg0.getName());
    }

    // This belongs to ISuiteListener and will execute, once the Suite is
    // finished
    @Override
    public void onFinish(ISuite arg0) {
        LogUtil.getLogger().info("About to end executing Suite " + arg0.getName());
        for (String s : Reporter.getOutput()) {
            extentReports.setTestRunnerOutput(s);
        }
        extentReports.flush();
        extentReports.close();

    }

    @Override
    public void onStart(ITestContext arg0) {
        LogUtil.getLogger().info("About to begin executing Test " + arg0.getName());

    }

    @Override
    public void onFinish(ITestContext arg0) {
        Set<ITestResult> failedTests = arg0.getFailedTests().getAllResults();
        for (ITestResult temp : failedTests) {
            ITestNGMethod method = temp.getMethod();
            if (arg0.getFailedTests().getResults(method).size() > 1) {
                failedTests.remove(temp);
            } else {
                if (arg0.getPassedTests().getResults(method).size() > 0) {
                    failedTests.remove(temp);
                }
            }
        }
        LogUtil.getLogger().info("About to end executing Test " + arg0.getName());

    }

    // This belongs to ITestListener and will execute only when the test is pass
    @Override
    public void onTestSuccess(ITestResult arg0) {
        // This is calling the printTestResults method
        String params = "";
        String[] groups = getGroupsName(arg0);
        String[] author = {"test user"};
        if (arg0.getParameters() != null && arg0.getParameters().length > 0) {
            for (Object parameter : arg0.getParameters()) {
                params += parameter.toString() + ",";
            }
            // params are also added to test case name
            extentTest = extentReports.startTest(arg0.getName() + " " + params);
            extentTest.setStartedTime(getTime(arg0.getStartMillis()));
            if (groups.length > 0) {
                extentTest.assignCategory(groups);
            }
            if (author != null && author.length > 0) {
                extentTest.assignAuthor(author);
            }
        } else {
            extentTest = extentReports.startTest(arg0.getName());
            extentTest.setStartedTime(getTime(arg0.getStartMillis()));
            if (groups.length > 0) {
                extentTest.assignCategory(groups);
            }
            if (author != null && author.length > 0) {
                extentTest.assignAuthor(author);
            }
        }
        // taking all steps
        try {
            for (int i = 0; i < UtilClass.utilList.size(); i++) {
                extentTest.log(LogStatus.INFO, arg0.getName(), UtilClass.utilList.get(i));
            }

        } catch (Throwable e) {
            e.printStackTrace();

        }

        printTestResults(arg0);
        logTestResults(arg0);
        LogUtil.getLogger().info("Test case :" + arg0.getName() + " got passed");
        // clearing list
        UtilClass.utilList.clear();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // This is calling the printTestResults method
        String params = "";
        String[] groups = getGroupsName(result);
        String[] author = {"test user"};
        if (result.getParameters() != null && result.getParameters().length > 0) {
            for (Object parameter : result.getParameters()) {
                params += parameter.toString() + ",";
            }
            // params are also added to test case name
            extentTest = extentReports.startTest(result.getName() + " " + params);
            extentTest.setStartedTime(getTime(result.getStartMillis()));
            if (groups.length > 0) {
                extentTest.assignCategory(groups);
            }
            if (author != null && author.length > 0) {
                extentTest.assignAuthor(author);
            }
        } else {
            extentTest = extentReports.startTest(result.getName());
            extentTest.setStartedTime(getTime(result.getStartMillis()));
            if (groups.length > 0) {
                extentTest.assignCategory(groups);
            }
            if (author != null && author.length > 0) {
                extentTest.assignAuthor(author);
            }
        }
        // taking all steps

        try {

            for (int i = 0; i < UtilClass.utilList.size(); i++) {

                extentTest.log(LogStatus.INFO, result.getName(), UtilClass.utilList.get(i));

            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        printTestResults(result);
        logTestResults(result);

        // clearing list
        UtilClass.utilList.clear();

    }

    // This belongs to ITestListener and will execute before the main test start
    // (@Test)
    @Override
    public void onTestStart(ITestResult arg0) {
        LogUtil.getLogger().info("Test case started for test name:" + arg0.getName());

    }

    // This belongs to ITestListener and will execute only if any of the main
    // test(@Test) get skipped
    @Override
    public void onTestSkipped(ITestResult arg0) {
        LogUtil.getLogger().info("Test case :" + arg0.getName() + " got skipped");
        String params = "";
        String[] groups = getGroupsName(arg0);
        String[] author = {"test user"};
        if (arg0.getParameters() != null && arg0.getParameters().length > 0) {
            for (Object parameter : arg0.getParameters()) {
                params += parameter.toString() + ",";
            }
            // params are also added to test case name
            extentTest = extentReports.startTest(arg0.getName() + " " + params);
            extentTest.setStartedTime(getTime(arg0.getStartMillis()));
            if (groups.length > 0) {
                extentTest.assignCategory(groups);
            }
            if (author != null && author.length > 0) {
                extentTest.assignAuthor(author);
            }
        } else {
            extentTest = extentReports.startTest(arg0.getName());
            extentTest.setStartedTime(getTime(arg0.getStartMillis()));
            if (groups.length > 0) {
                extentTest.assignCategory(groups);
            }
            if (author != null && author.length > 0) {
                extentTest.assignAuthor(author);
            }
        }
        // taking all steps

        printTestResults(arg0);
        logTestResults(arg0);

        // clearing list
        UtilClass.utilList.clear();

    }

    // This is just a piece of shit, ignore this
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {

    }

    // This will provide the information on the test

    private void printTestResults(ITestResult result) {
        LogUtil.getLogger().info("Test Method resides in " + result.getTestClass().getName());
        if (result.getParameters().length != 0) {
            String params = "";
            for (Object parameter : result.getParameters()) {
                params += parameter.toString() + ",";
            }
            LogUtil.getLogger().info("Test Method had the following parameters : " + params);

        }

        String status = null;
        switch (result.getStatus()) {
            case ITestResult.SUCCESS:
                status = "Pass";
                break;
            case ITestResult.FAILURE:
                status = "Failed";
                break;
            case ITestResult.SKIP:
                status = "Skipped";

        }
        LogUtil.getLogger().info("Test Status: " + status);

    }

    @Override
    public void beforeInvocation(IInvokedMethod arg0, ITestResult arg1) {
        String textMsg = "About to begin executing following method : " + returnMethodName(arg0.getTestMethod());
        LogUtil.getLogger().info(textMsg);

    }

    @Override
    public void afterInvocation(IInvokedMethod arg0, ITestResult arg1) {
        String textMsg = "Completed executing following method : " + returnMethodName(arg0.getTestMethod());
        LogUtil.getLogger().info(textMsg);

    }

    // This will return method names to the calling function
    private String returnMethodName(ITestNGMethod method) {
        return method.getRealClass().getSimpleName() + "." + method.getMethodName();

    }

    @Override
    public void onExecutionStart() {
        LogUtil.getLogger().info("<<<<<<========onExecutionStart==========>>>>>>>");

    }

    @Override
    public void onExecutionFinish() {
        LogUtil.getLogger().info("<<<<<<========onExecutionFinish==========>>>>>>>");

    }


    public String getCurrentTimeInstance() {
        Calendar calendar = Calendar.getInstance();
        String currentTimeInstance = "-" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-"
                + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.HOUR_OF_DAY) + "-"
                + calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND) + "-"
                + calendar.get(Calendar.MILLISECOND);
        return currentTimeInstance;

    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    public void logTestResults(ITestResult testResult) {
        switch (testResult.getStatus()) {
            case ITestResult.SUCCESS:
                logTestResultsForPassed(testResult);
                break;
            case ITestResult.FAILURE:
                logTestResultsForFail(testResult);
                break;
            case ITestResult.SKIP:
                LogUtil.getLogger().info("Skipped called in logTestResults");
                logTestResultsForSkip(testResult);
                break;
        }
    }

    public void logTestResultsForPassed(ITestResult testResult) {
        String params = "";
        if (testResult.getParameters() != null && testResult.getParameters().length > 0) {
            for (Object parameter : testResult.getParameters()) {
                params += parameter.toString() + ",";
            }
            extentTest.setEndedTime(getTime(testResult.getEndMillis()));
            extentTest.log(LogStatus.PASS, testResult.getName() + " " + params, params);
        } else {
            extentTest.setEndedTime(getTime(testResult.getEndMillis()));
            extentTest.log(LogStatus.PASS, testResult.getName(), params);
        }
        extentReports.endTest(extentTest);
    }

    public void logTestResultsForFail(ITestResult testResult) {
        Object currentClass = testResult.getInstance();
        String img = null;
        try {
            String params = "";
            if (testResult.getParameters() != null && testResult.getParameters().length > 0) {
                for (Object parameter : testResult.getParameters()) {
                    params += parameter.toString() + ",";
                }
                extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                extentTest.log(LogStatus.FAIL, testResult.getName() + " with params [ " + params + " ]",
                        testResult.getThrowable());
                extentTest.log(LogStatus.FAIL, testResult.getName(), img);
            } else {
                extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                extentTest.log(LogStatus.FAIL, testResult.getName(), testResult.getThrowable());
                extentTest.log(LogStatus.FAIL, testResult.getName(), img);
            }

            LogUtil.getLogger().error("Test case :" + testResult.getName() + " got failed... Failure Reason:"
                    + testResult.getThrowable());
            extentReports.endTest(extentTest);
        } catch (Exception e) {
            String params = "";
            if (testResult.getParameters() != null && testResult.getParameters().length > 0) {
                for (Object parameter : testResult.getParameters()) {
                    params += parameter.toString() + ",";
                }
                extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                extentTest.log(LogStatus.FAIL, testResult.getName() + " with params [ " + params + " ]",
                        testResult.getThrowable());
            } else {
                extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                extentTest.log(LogStatus.FAIL, testResult.getName(), testResult.getThrowable());
            }

            LogUtil.getLogger().error("Test case :" + testResult.getName() + " got failed... Failure Reason:"
                    + testResult.getThrowable());
            extentReports.endTest(extentTest);
        }

    }

    public void logTestResultsForSkip(ITestResult testResult) {
        Object currentClass = testResult.getInstance();
        String img = null;
        try {

            String params = "";
            if (testResult.getParameters() != null && testResult.getParameters().length > 0) {
                for (Object parameter : testResult.getParameters()) {
                    params += parameter.toString() + ",";
                }
                try {
                    extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                    extentTest.log(LogStatus.SKIP, testResult.getName() + " with params [ " + params + " ]",
                            testResult.getThrowable());
                } catch (NullPointerException e) {
                    extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                    extentTest.log(LogStatus.SKIP, testResult.getName() + " with params [ " + params + " ]",
                            "Test case skipped");
                }
                extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                extentTest.log(LogStatus.SKIP, testResult.getName(), img);
            } else {
                try {
                    extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                    extentTest.log(LogStatus.SKIP, testResult.getName(), "Dependent Testcases Skipped --->" + testResult.getThrowable().getMessage());
                } catch (NullPointerException e) {
                    extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                    extentTest.log(LogStatus.SKIP, testResult.getName(), "Test Case skipped");
                }
                extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                extentTest.log(LogStatus.SKIP, testResult.getName(), img);
            }

            LogUtil.getLogger().error("Test case :" + testResult.getName() + " got skipped... skipped Reason:"
                    + testResult.getThrowable());
            extentReports.endTest(extentTest);

        } catch (Exception e) {
            String params = "";
            if (testResult.getParameters() != null && testResult.getParameters().length > 0) {
                for (Object parameter : testResult.getParameters()) {
                    params += parameter.toString() + ",";
                }
                extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                extentTest.log(LogStatus.SKIP, testResult.getName() + " with params [ " + params + " ]",
                        testResult.getThrowable());
            } else {
                try {
                    extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                    extentTest.log(LogStatus.SKIP, testResult.getName(), "Dependent Testcases Skipped --->" + testResult.getThrowable().getMessage());
                } catch (NullPointerException t) {
                    extentTest.setEndedTime(getTime(testResult.getEndMillis()));
                    extentTest.log(LogStatus.SKIP, testResult.getName(), "Test Case skipped");
                }
            }
            try {
                LogUtil.getLogger().error("Test case :" + testResult.getName() + " got skipped... skipped Reason:"
                        + testResult.getThrowable());
                extentReports.endTest(extentTest);
            } catch (NullPointerException t) {
                LogUtil.getLogger().error("Test case :" + testResult.getName() + " got skipped... ");
                extentReports.endTest(extentTest);
            }

        }

    }

    public String[] getGroupsName(ITestResult testResult) {
        return testResult.getMethod().getGroups();
    }


}
