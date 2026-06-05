Feature: Login Page Modules

Scenario: User need to check background color for Forget Password Page,Username,Password,Rememberme,Sign in your account.

Given User enter the login page
When Grab the Css values for all Text.
Then User need to check the background color match or not with the expected background color.


Scenario: User click the Support and Helpdesk button check popup is displayed or not.
Given User enter the login page
When User click the Support and Helpdesk button
Then User need to check the popup is displayed or not.

Scenario: User click the Features button check popup is displayed or not.
Given User enter the login page
When User click the Features button
Then User need to check the features popup is displayed or not.

Scenario: User click the Services button check popup is displayed or not.
Given User enter the login page
When User click the Services button
Then User need to check the searvices popup is displayed or not.

Scenario: User click the Training button check popup is displayed or not.
Given User enter the login page
When User click the Training button
Then User need to check the training popup is displayed or not.

Scenario: User without enter anything in username and password and check the valid error message.
Given User enter the login page
When Without enter anything in username and password and click the sign in button
Then User need to check the valid error message is displayed or not.

Scenario: User enter valid username and without enter password and check the valid error message.
Given User enter the login page
When User enter valid username and without enter password and click the sign in button
Then User need to check the valid error message is displayed or not for valid username and without enter password.

Scenario: User without enter username and enter valid password and check the valid error message.
Given User enter the login page
When User without enter username and enter valid password and click the sign in button
Then User need to check the valid error message is displayed or not for without enter username and enter valid password.

Scenario: User enter invalid username and invalid password and check the valid error message.
Given User enter the login page
When User enter the invalid username and invalid password and click the sign in button
Then User need to check the valid error message is displayed or not for invalid username and invalid password.


Scenario: User can able to enable the Remember me option.
Given User enter the login page
When User click the Remember me checkbox
Then User need to check the Remember me checkbox is enabled or not.

