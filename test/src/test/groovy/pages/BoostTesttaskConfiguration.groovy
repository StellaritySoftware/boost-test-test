package pages

import geb.Page

/**
 * Created by Kateryna on 05.12.2017.
 */
class BoostTesttaskConfiguration extends Page{

    static url = {Config.context + "/build/admin/create/createPlanTasks.action"}
    static at = { ($("#createTask h2").text() == "Boost::Test Task configuration" ||
                $("#updateTask h2").text() == "Boost::Test Task configuration")
    }

    static content = {
        taskDescreption {$("#createTask_userDescription")}
        disabletaskCheckbox {$("#label_createTask_taskDisabled")}
        parseOnlyModeCheckbox{$("#boosttestParseOnly")}
        testExecutables {$("#boosttestExecutables")}
        subdirectory {$("#boosttestSubdirectory")}
        taskNameCollisions {$("#boosttestUseTaskName")}
        fileNameCollisions {$("#boosttestUseFileName")}
        environmentVariable {$("#boosttestEnvironment")}
        timeout{$("#boosttestTimeout")}
    }

    def clickSave(){
        js.exec(
            "var createSave = document.getElementById('createTask_save');" +
            "var updateSave = document.getElementById('updateTask_save');" +
            "createSave ? createSave.click() : updateSave.click();"
        )
        browser.waitFor{successfulTaskCreationText || successfulTaskUpdatedText}
        browser.at CreateNewPlanConfigureTasksPage
    }

    def uncollapseAdvancedOptions(){
        js."document.querySelector('form#updateTask div span.icon.icon-expand').click()"
        waitFor{advancedOptionsRetryCount.isDisplayed()}
    }

    def changePassword(){
        js.exec("scroll(0, 250)")
        changePassword.click()
        waitFor {passwordFtp.isDisplayed()}
    }

    def chooseUseSharedCredentials(){
        js.exec("scroll(0, 250)")
        useSharedCredentials = true
        waitFor {dropDownCredentials.isDisplayed()}
    }
}
