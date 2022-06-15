# Reminder application

## Introduction

Implement an Android reminder application that can check-off reminders as they are completed. User notifications should be posted automatically at the appropriate time. Reminder details may be opened either by clicking on a notification or directly via notification actions. The app is nearly ready for deployment, except for the problems you’ll have to address.

## Problem Statement
The key requirements are to schedule an action to be performed and to implement a feature that schedules actions that display the proper notification about a reminder. Please have in mind the following:
1. `Reminder` model class is already implemented.
2. You need to implement the `scheduleReminder(Reminder)` method in `ReminderService` to schedule `ReminderNotificationService` launch. Be aware that the Service needs to know which reminder it will process.
3. Implement notification display in `ReminderNotificationService.onHandleIntent` method. Make sure to read info about the reminder which should be displayed.
4. The notification should include a reminder title clearly displayed.
5. The notification should be displayed as a **heads-up** notification - so please make sure that all requirements from Android docs regarding displaying heads-up notifications are met - https://developer.android.com/guide/topics/ui/notifiers/notifications#Heads-up
6. The notification should be clickable and transfer the user to the `ReminderDetailsActivity`.
7. `ReminderDetailsActivity` should display reminder title, date/time (using default system format) and check/uncheck `cb_done` checkbox according to the reminder data. All of these *must be* read-only.
8. If reminder is not found then `ReminderDetailsActivity` should show Toast with `R.string.reminder_not_found_error` text and conclude.
9. Notifications should have an action named 'Done'. The action should mark the proper reminder as done using `ReminderService.markDone` method.

Finally, the application should be able to display notifications for scheduled reminders and those notification should be useful from the user perspective (go to details, mark as done).

It is not enough to just fill missing implementation to pass the exam. The project evaluation contains several additional tests not visible to you.

Mind your code style, you might get some extra points for keeping the files neat and tidy. 

## Note

Please be careful when editing `build.gradle` in your project. This task as it is doesn’t require any changes to it. It is generally ok to add new dependencies but changing or removing existing dependencies or configuration can cause the project and verification tests to not function in the expected way and give a unreliable score.
