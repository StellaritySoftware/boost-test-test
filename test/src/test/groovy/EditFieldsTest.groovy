import commonpages.LoginPage
import configuration.CommonConfig
import geb.spock.GebReportingSpec
import pages.BoostTestTaskConfigurationPage
import pages.TaskTypesPage

class EditFieldsTest extends GebReportingSpec
{

    def run()
    {
        when:

        def loginPage = browser.to LoginPage

        def dashboardPage = loginPage.login(CommonConfig.user, CommonConfig.password)

        def createNewPlanConfigurePlanPage = dashboardPage.createNewPlan()
        createNewPlanConfigurePlanPage.setRandomProjectPlanNames()
        createNewPlanConfigurePlanPage.setNoneRepository()

        def configureTasksPage = createNewPlanConfigurePlanPage.clickConfigurePlanButton()

        def tasks = configureTasksPage.addTask(TaskTypesPage)

        def boostTestTaskConfiguration = tasks.selectBoostTesttask()
        boostTestTaskConfiguration.taskDescription << "my_task"
        boostTestTaskConfiguration.disabletaskCheckbox = true
        boostTestTaskConfiguration.testExecutables << "my_test,testFile_Test"
        boostTestTaskConfiguration.subdirectory << "subDir"
        boostTestTaskConfiguration.uncollapseAdvancedOptions()
        boostTestTaskConfiguration.setEnvironmenVariables "JAVA_OPTS=-Xmx256m -Xms128m"
        boostTestTaskConfiguration.taskNameCollisions = true
        boostTestTaskConfiguration.fileNameCollisions = true
        boostTestTaskConfiguration.timeout = "5"

        boostTestTaskConfiguration.clickSave()

        configureTasksPage.editTask(BoostTestTaskConfigurationPage)
// FIRST CHECK
        then:
        boostTestTaskConfiguration.taskDescriptionUpdate.value() == "my_task"
        boostTestTaskConfiguration.disabletaskCheckboxUpdate == "true"
        boostTestTaskConfiguration.testExecutables.value() == "my_test,testFile_Test"
        boostTestTaskConfiguration.subdirectory.value() == "subDir"

        when:
        boostTestTaskConfiguration.uncollapseAdvancedOptions()

        then:
        boostTestTaskConfiguration.environmentVariable.value() == "JAVA_OPTS=-Xmx256m -Xms128m"
        boostTestTaskConfiguration.taskNameCollisions.value() == "true"
        boostTestTaskConfiguration.fileNameCollisions.value() == "true"
        boostTestTaskConfiguration.timeout.value() == "5"
// END FIRST CHECK

// SECOND CHECK

        when:
        configureTasksPage.editTask(BoostTestTaskConfigurationPage)

        boostTestTaskConfiguration.taskDescriptionUpdate = "second_task"
        boostTestTaskConfiguration.disabletaskCheckboxUpdate = false
        boostTestTaskConfiguration.parseOnlyModeCheckbox = true
        boostTestTaskConfiguration.enterOutputFilesName("testXml.xml")
        boostTestTaskConfiguration.subdirectory = ""
        boostTestTaskConfiguration.taskNameCollisions = false
        boostTestTaskConfiguration.fileNameCollisions = false
        boostTestTaskConfiguration.checkPickOutdatedFiles()

        boostTestTaskConfiguration.clickSave()

        configureTasksPage.editTask(BoostTestTaskConfigurationPage)

        then:
        boostTestTaskConfiguration.taskDescriptionUpdate.value() == "second_task"
        boostTestTaskConfiguration.disabletaskCheckboxUpdate.value() == null
        boostTestTaskConfiguration.parseOnlyModeCheckbox.value() == "true"
        boostTestTaskConfiguration.subdirectory.value() == ""

        when:
        boostTestTaskConfiguration.uncollapseAdvancedOptions()

        then:
        boostTestTaskConfiguration.outpuFiles.value() == "testXml.xml"
        boostTestTaskConfiguration.taskNameCollisions.value() == null
        boostTestTaskConfiguration.fileNameCollisions.value() == null
        boostTestTaskConfiguration.pickOutdatedFiles.value() == "true"

    }
}