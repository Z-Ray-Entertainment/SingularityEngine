# How to contribute #

In order to contribute you have to read, follow and apply to the following term:

## The Branches ##
### Master ###
This branch is always the lates stable release (in case of the current progress 
of develompent alpha release).
This branch is always functional and tested.
Only administrators are allowed do merge other branches into the master.
The master branch is onyl updated if a milestone was successfully compleated and
testet in the other branches.

### Stage ###
The stage is a pre release branch whitch intends to be the final testing branch
only if the stage branch is functional and well tested it is merged into the master.
It is also meant to be an official experimental brachn for external develper so 
they can use the latest feauters but may have to expect some issues or even crashes.
If this branch is found to be stable it can be merged into the master branch.

### Test ###
The test branch is the first branch in which all development branches are merged 
to and the first tier where all different features are brought together and 
testet with each other.
Only if this branch is stable it is merged into the next branch (stage).

### Developent branches ###
It is allowed to have as many dev brnaches as possible even for each individual 
developer for any purpose he/she/it wants.
But your only allowed to issue a merge request for your branch in to the test 
branch if youre dev branch is stable and barely tested.
Every developer is allowed to merge any other branch into his/her/it dev branch 
without restrictions.

## Merging ##
Anybody is allowed to request a merge but not to finly merge two different branches.
Every request is revisited by and administrator.
Only for his very own dev branch, if one exists, every developer is allowed to
merge any other branch into his dev branch.
Merges which are into the Test, Stage or Master branch are only allowed by
project administrators (VOrtex Acherontic and Kentravyon).

# Issues #

## Submitting issues ##
You are always allowed to submit an issue.

If you create a new issue please follow these steps:

  * Make your issue "Confidential"
  * Lable your issue as "TBD"
  * Give a short but coherent title
  * A brief descritption of what your issue is about.
  * Add addinional Labels such as "New Feature", "Bug", "Task" or "Optimization"

if any of those belong to your issue.

Afterwards a member of the head dev team will revisit your issue may ask further
questions and approve or refuse the issue.
If an issue is approved it is in the queue for further adminsitration such as
Assigning the issue, make it unconfidential or added some lables.

## Worikng on issues ##
You are only allowed to work on an issue which is assigned to you and labled as 
"Approved".
You're not allowed to assign an issue to you or anyone else except if you are a 
meber of the head dev team (HDT).
After you finished an issue successfully you can request an merge into the test 
branch.

# Milestones #
Milestones are ment to be a collection of issues which belonging to one final
targed.
For example: Milestone "Implementation of Awesome Render API"
And an issues which belonging to this API such as rendering lights, textures,
geometry eg.
You can find an example in the "Legacy GL Renderbackend" milestone.

Every time a milestone is compleded and testet in the other branches the master
branch is updated.
The pre release is the stage which is updated if the test branch is working and
testet. The stage is updated even if a milestone is not completed.