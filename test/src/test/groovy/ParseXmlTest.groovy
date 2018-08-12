import geb.spock.GebReportingSpec
import helpers.DirectoryCreator
import pages.Config
import pages.LoginPage

class ParseXmlTest extends GebReportingSpec
{

    def run()
    {
        when:

        def loginPage = browser.to LoginPage

        def dashboardPage = loginPage.login(Config.user, Config.password)

        def createNewPlanConfigurePlanPage = dashboardPage.createNewPlan()
        createNewPlanConfigurePlanPage.setRandomProjectPlanNames()

        DirectoryCreator.createPlanDirectory()
        DirectoryCreator.copyFile("testXml.xml")


        createNewPlanConfigurePlanPage.setNoneRepository()

        def configureTasksPage = createNewPlanConfigurePlanPage.clickConfigurePlanButton()

        def tasks = configureTasksPage.addTask()

        def boostTestTaskConfiguration = tasks.selectBoostTesttask()
        boostTestTaskConfiguration.parseOnlyModeCheckbox = true
        boostTestTaskConfiguration.enterOutputFilesName("testXml.xml")
        boostTestTaskConfiguration.uncollapseAdvancedOptions()
        boostTestTaskConfiguration.pickOutdatedFiles = true

        boostTestTaskConfiguration.clickSave()

        def createdPlan = configureTasksPage.clickCreateButton()

        def planBuild = createdPlan.runManualBuild()

        then:

        planBuild.waitForFailedHeader()
        planBuild.checkNumberOfFailedTests('5')

    }
}