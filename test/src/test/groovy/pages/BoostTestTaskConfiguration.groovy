package pages

import geb.Page

/**
 * Created by Kateryna on 05.12.2017.
 */
class BoostTestTaskConfiguration extends Page{

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
        successfulTaskCreationText {$("div.aui-message.aui-message-success").text() == "Task created successfully."}
        successfulTaskUpdatedText {$("div.aui-message.aui-message-success").text() == "Task saved successfully."}
        outpuFiles{$("#boosttestOutputFiles")}
        pickOutdatedFiles{$("#boosttestPickOutdatedFiles")}

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
        js."document.querySelector('fieldset.collapsible-section.collapsed div.summary span.icon.icon-expand').click()"
        waitFor{pickOutdatedFiles.isDisplayed()}
    }

    def enterOutputFilesName(String name) {

        js."document.querySelector('#boosttestOutputFiles').scrollIntoView()"
        outpuFiles << name
    }
}
