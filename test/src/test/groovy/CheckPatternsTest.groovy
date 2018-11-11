import commonpages.LoginPage
import configuration.CommonConfig
import geb.spock.GebReportingSpec
import helpers.DirectoryCreator
import pages.TaskTypesPage

class CheckPatternsTest extends GebReportingSpec
{

    def run()
    {
        when:

        def loginPage = browser.to LoginPage

        def dashboardPage = loginPage.login(CommonConfig.user, CommonConfig.password)

        def createNewPlanConfigurePlanPage = dashboardPage.createNewPlan()
        createNewPlanConfigurePlanPage.setRandomProjectPlanNames()
        DirectoryCreator.createPlanDirectory()
        DirectoryCreator.copyFile("my_test")
        DirectoryCreator.copyFile("libboost_unit_test_framework.so.1.58.0")
        DirectoryCreator.copyFile("testFile_Test")
        DirectoryCreator.copyFile("t_st")
        createNewPlanConfigurePlanPage.setNoneRepository()

        def configureTasksPage = createNewPlanConfigurePlanPage.clickConfigurePlanButton()

        def tasks = configureTasksPage.addTask(TaskTypesPage)

        def boostTestTaskConfiguration = tasks.selectBoostTesttask()
        boostTestTaskConfiguration.testExecutables << "*test*"
        boostTestTaskConfiguration.checkFileNameCollision()
        boostTestTaskConfiguration.clickSave()

        configureTasksPage.markEnablePlanCheckbox()

        def createdPlan = configureTasksPage.clickCreateButton()

        def planBuild = createdPlan.runManualBuild()

        then:
        planBuild.waitForFailedHeader()

        when:
        planBuild.testsTabLink.click()

        then:
        planBuild.checkTextAddedToTests('my_test', 5)
        planBuild.checkTextAddedToTests('testFile_Test', 5)
        planBuild.checkNoTestsWithTexts ('t_st', 0)

    }
}