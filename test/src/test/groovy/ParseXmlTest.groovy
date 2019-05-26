import commonpages.LoginPage
import configuration.CommonConfig
import geb.spock.GebReportingSpec
import helpers.DirectoryCreator
import pages.TaskTypesPage

class ParseXmlTest extends GebReportingSpec
{

    def run()
    {
        when:

        def loginPage = browser.to LoginPage

        def dashboardPage = loginPage.login(CommonConfig.user, CommonConfig.password)

        def createNewPlanConfigurePlanPage = dashboardPage.createNewPlan()
        createNewPlanConfigurePlanPage.setRandomProjectPlanNames()

        DirectoryCreator.createPlanDirectory()
        DirectoryCreator.copyFile("testXml.xml")
        DirectoryCreator.copyFile("libboost_unit_test_framework.so.1.58.0")


        createNewPlanConfigurePlanPage.setNoneRepository()

        def configureTasksPage = createNewPlanConfigurePlanPage.clickConfigurePlanButton()

        def tasks = configureTasksPage.addTask(TaskTypesPage)

        def boostTestTaskConfiguration = tasks.selectBoostTesttask()
        boostTestTaskConfiguration.parseOnlyModeCheckbox = true
        boostTestTaskConfiguration.enterOutputFilesName("testXml.xml")
        boostTestTaskConfiguration.uncollapseAdvancedOptions()
        boostTestTaskConfiguration.pickOutdatedFiles()

        boostTestTaskConfiguration.clickSave()

        configureTasksPage.markEnablePlanCheckbox()

        def createdPlan = configureTasksPage.clickCreateButton()

        def planBuild = createdPlan.runManualBuild()
        
        then:
        planBuild.waitForFailedHeader()
        
        when:
        planBuild.testsTabLink.click()
        
        then:
        planBuild.checkNumberOfFailedTests('5')

    }
}