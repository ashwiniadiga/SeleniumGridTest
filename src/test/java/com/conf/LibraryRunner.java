package com.conf;


import com.report.CustomRunner;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import cucumber.api.testng.AbstractTestNGCucumberTests;

//@RunWith(Cucumber.class)
@CucumberOptions(features ="test-classes/com/resources/cucumber/Library.feature",
        glue ="com/test",
        tags = "@FIRSTTEST",
        plugin ={"pretty"})
public class LibraryRunner extends CustomRunner{

}
