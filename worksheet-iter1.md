# Worksheet Iteration 1

* [Added Features](./addedFeatures.md)
* [Found Solid Violation](https://discordapp.com/channels/801159051894325278/801159051894325281/814986164939128833)
 
## Branching

Provide a link to where you describe your branching strategy. https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/network/master
Image: https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/network/master

## Exceptional Code

https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/blob/master/app/src/main/java/comp3350/sceneit/logic/CreditManager.java

CreditManager.java in the isNumeric() function handles an exception. This method was needed to make sure when users input a string that it doesnt include any letters. One of the easiest ways of handling it without using complex regex strings is to rely on the fact Long.parseLong("string") would throw an error if the string contains anything that isnt a number.

## Agile Planning

We had to move the search function for users to iteration 2. The Database Manager currently requires manually allowing network activity in the main thread so pushed it to iteration 2 [here](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/issues/40).
