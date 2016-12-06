package com.report;

import com.base.WebDriverBase;
import cucumber.api.testng.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by ashwad01 on 11/28/2016.
 */
// public abstract class CustomRunner  {
//    private static final Logger LOG = LoggerFactory.getLogger(CustomRunner.class);
//
//    @AfterClass(alwaysRun = true)
//    public void tearDownClass() throws Exception {
//        LOG.info("At teardown");
//    }
//}
    public abstract class CustomRunner implements IHookable {
    private static final Logger LOG = LoggerFactory.getLogger(CustomRunner.class);
        public CustomRunner() {
           LOG.info("++++++++++CustomRunner+++++++++");
        }


        @Test(
                groups = {"cucumber"},
                description = "Runs Cucumber Features"
        )
        public void run_cukes() throws IOException {
            LOG.info("++++++++++run_cukes+++++++++");
            LOG.info(getClass().getName());
            (new TestNGCucumberRunner(getClass())).runCukes();
        }

        public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
            LOG.info("++++++++++run_cukes+++++++++");
            iHookCallBack.runTestMethod(iTestResult);
            LOG.info("++++++++++++++++"+iTestResult.getMethod().getMethodName());
        }

    @AfterClass
    public static void tearDown(){
        LOG.info("++++++++++tearDown+++++++++");
        WebDriverBase.teardown();
    }

}
