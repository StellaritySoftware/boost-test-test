import geb.spock.GebReportingSpec

import helpers.DirectoryCreator
import pages.Config
import pages.LoginPage

class FtpDownloadIncludeExcludePatternsTest extends GebReportingSpec
{

    def run()
    {
        when:


        def loginPage = browser.to LoginPage

        def dashboardPage = loginPage.login(Config.user, Config.password)

        def createNewPlanConfigurePlanPage = dashboardPage.createNewPlan()
        createNewPlanConfigurePlanPage.setRandomProjectPlanNames()

        DirectoryCreator.createPlanDirectory()
        DirectoryCreator.copyFile("my_test")

        createNewPlanConfigurePlanPage.setNoneRepository()

        def configureTasksPage = createNewPlanConfigurePlanPage.clickConfigurePlanButton()

        def tasks = configureTasksPage.addTask()

        def boostTestTaskConfiguration = tasks.selectBoostTesttask()

//        ftpDownloadConfiguration.ftpServerUrl << Config.ftpUrlDownload
//        ftpDownloadConfiguration.usernameFtp << Config.ftpUser
//        ftpDownloadConfiguration.passwordFtp << Config.ftpPassword
//        ftpDownloadConfiguration.includePatternField << includePattern
//        ftpDownloadConfiguration.excludePatternField << excludePattern
//        ftpDownloadConfiguration.clickSave()
//        configureTasksPage.enablePlanCheckBox = true
//
//        def createdPlan = configureTasksPage.clickCreateButton()
//
//        def planBuild = createdPlan.runManualBuild()
//
//        then:
//
//        planBuild.waitForSuccessfulHeader()
//        DirectoryComparator.verifyDirs(Paths.get(sample), Config.buildDir)
//
//        where:
//        includePattern                | excludePattern       || sample
//        "**/*.xml,**/*.java,**/*.txt" | "**/*.txt"           || Config.ftpSampleIncludeExclude
//        "**/*.xml,**/*.txt"           | ""                   || Config.ftpSampleInclude
//        ""                            | "**/*.java,**/*.txt" || Config.ftpSampleExclude
    }
}