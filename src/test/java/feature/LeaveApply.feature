Feature: Leave Apply Modules

Background:
Given User enters valid Authentication username and Authentication password
When User click the login button
Then Check user navigate to the Home page

Scenario: User apply Public Leave based on open leave setting
Given Hit the getData url and check the open leave setting.
And User click the Leave Module
And User need to check the leave module page is open or not.
And Based on open leave setting, user try to apply public holiday leave
When User click the Apply Leave Button
Then User need to check valid error message for successful leave application.
And User need to check the applied public/rest/off day leave is display in the leave list or not and click the delete button for applied leave.
Then User need to check valid error message for public/rest/off day successful deletion.

Scenario: User apply Rest Day based on open leave setting
Given Hit the getData url and check the open leave setting.
And User click the Leave Module
And User need to check the leave module page is open or not.
And Based on open leave setting, user try to apply rest day leave
Then User click the Apply Leave Button
Then User need to check valid error message for successful leave application.
And User need to check the applied public/rest/off day leave is display in the leave list or not and click the delete button for applied leave.
Then User need to check valid error message for public/rest/off day successful deletion.

Scenario: User apply Off Day based on open leave setting
Given Hit the getData url and check the open leave setting.
And User click the Leave Module
And User need to check the leave module page is open or not.
And Based on open leave setting, user try to apply off day leave
When User click the Apply Leave Button
Then User need to check valid error message for successful leave application.
And User need to check the applied public/rest/off day leave is display in the leave list or not and click the delete button for applied leave.
Then User need to check valid error message for public/rest/off day successful deletion.

Scenario: User apply normal leave and check leave applied or not, if applied delete the applied leave
Given Hit the getData url and check the open leave setting.
And User click the Leave Module
And User need to check the leave module page is open or not.
And Based on open leave setting, user try to apply normal day leave
When User clicked the Apply Leave Button
Then User need to check valid error message for successful leave application.
And User need to check the applied leave is display in the leave list or not and click the delete button for applied leave.
Then User need to check valid error message for successful leave deletion.


Scenario: User apply normal leave and check leave applied or not and then apply same date leave and check wthether it will allow or not then delete the applied leave
Given Hit the getData url and check the open leave setting.
And User click the Leave Module
And User need to check the leave module page is open or not.
And Based on open leave setting, user try to apply normal day leave
When User clicked the Apply Leave Button 
Then User need to check valid error message for successful leave application.
And User apply the same date leave
And User clicked the Apply Leave Button
And User need to check valid error message for unsuccessful leave application.
And User need to check the applied leave is display in the leave list or not and click the delete button for applied leave.
Then User need to check valid error message for successful leave deletion.


Scenario: User try to apply more leave based on earn leave count
Given Hit the getData url and check the open leave setting.
And User click the Leave Module
And User need to check the leave module page is open or not. 
And User need to check earn leave count before applying leave.
And Based on earn leave count, user try to apply normal day leave more than earn leave count
When User clicked the Apply Leave Button 
Then User need to check valid error message for unsuccessful leave balance message.

Scenario: User try to apply half day leave and check the half day leave applied or not, if applied delete the applied half day leave
Given Hit the getData url and check the open leave setting.
And User click the Leave Module
And User need to check the leave module page is open or not. 
And User try to apply hald day leave for Morning Session.
When User clicked the Apply Leave Button
Then User need to check valid error message for successful leave application.
And User need to check the applied leave is display in the leave list or not and click the delete button for applied leave.
Then User need to check valid error message for successful leave deletion.

Scenario: User try to apply half day leave and check the half day leave applied or not, then try to applied same date half day leave and then finally delete the leave
Given Hit the getData url and check the open leave setting.
And User click the Leave Module
And User need to check the leave module page is open or not. 
And User try to apply hald day leave for Morning Session.
When User clicked the Apply Leave Button
Then User need to check valid error message for successful leave application.
And User apply the same date half day leave
And User clicked the Apply Leave Button
And User need to check valid error message for unsuccessful leave application.
And User need to check the applied leave is display in the leave list or not and click the delete button for applied leave.
Then User need to check valid error message for successful leave deletion.


Scenario: User try to apply half day leave evening session and check the half day leave applied or not, if applied delete the applied half day leave
Given Hit the getData url and check the open leave setting.
And User click the Leave Module
And User need to check the leave module page is open or not. 
And User try to apply hald day leave for Evening Session.
When User clicked the Apply Leave Button
Then User need to check valid error message for successful leave application.
And User need to check the applied leave is display in the leave list or not and click the delete button for applied leave.
Then User need to check valid error message for successful leave deletion.

Scenario: User try to apply half day leave Evening Session and check the half day leave applied or not, then try to applied same date half day leave and then finally delete the leave
Given Hit the getData url and check the open leave setting.
And User click the Leave Module
And User need to check the leave module page is open or not. 
And User try to apply hald day leave for Evening Session.
When User clicked the Apply Leave Button
Then User need to check valid error message for successful leave application.
And User apply the same date half day leave - Evening Session
And User clicked the Apply Leave Button
And User need to check valid error message for unsuccessful leave application.
And User need to check the applied leave is display in the leave list or not and click the delete button for applied leave.
Then User need to check valid error message for successful leave deletion.

Scenario: User try to apply half day leave both session and then try to apply full day leave and check whether user can able to apply leave or not then finally delete both session leave date
Given Hit the getData url and check the open leave setting.
And User click the Leave Module
And User need to check the leave module page is open or not. 
And User try to apply hald day leave for Morning Session.
When User clicked the Apply Leave Button
Then User need to check valid error message for successful leave application.
And User apply the same date half day leave - Evening Session
When User clicked the Apply Leave Button
Then User need to check valid error message for successful leave application.
And  User apply the same date leave
When User clicked the Apply Leave Button
And User need to check valid error message for unsuccessful leave application.
And User need to check the applied leave is display in the leave list or not and click the delete button for applied leave.
Then User need to check valid error message for successful leave deletion.
And User need to check the applied leave is display in the leave list or not and click the delete button for applied leave.
Then User need to check valid error message for successful leave deletion.