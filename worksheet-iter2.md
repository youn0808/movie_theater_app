
# Iteration 2 Worksheet
<br> </br>
## Paying off technical debt

#### Deliberate Prudent Technical debt

We ran into Deliberate and Prudent technical debt with our database managers implementation. Initially our PostgresDatabaseManager had only been tested in tests, not on android. When we merged in the database manager, and added code that used it on android we ran into an issue where the code would have errors as android does not like having network operations (like database/http requests) on the main thread. We deliberately decided that we didn’t have time to investigate how to properly fix this for the iteration 1 release. Thus we enabled a hack of disabling the safeguard that prevents networking on the main thread, until Iteration 2, where we would have a proper fix. This hack was very prudent, and is why I’m classifying this as prudent. This was eventually implemented using background threads.

[Before:](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/commit/de0f05ad13f94f70180eb15bdf1909c6020eaa85)

[Fix:](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/commit/8341784e430694c9dc0939fbe54005ba007dc242)


#### Inadvertent Prudent Technical Debt ####

In Credit Activity had a big if else statement checking if each field entered by the user contained valid data, it was not a clean way to do it and left logic in the presentation layer. Instead created a CreditCard class that stores all the info and created a method in CreditManager that checks if the data help in the credit card is correct, which moved almost all the logic outside our Presentation layer. This was Inadvertent Prudent debt evidence to the comment made in the code when the debt was still there saying that I didn't know a better way
to code that section. Thanks to Jon for giving the info needed to rewrite and fix the debt.

Credit Activity logic in presentation layer: 
[Before](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/commit/588cc2f6c6afae04855286ac58af71177051228d#177c562cb50a92998e19758c279d72173ff4a66a7985)

Credit Manager new method to validate credit cards: 
[Fix pt.1](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/commit/588cc2f6c6afae04855286ac58af71177051228d#f95958f1c46e637a5c7597033868857835e21aa0_100_15)

Credit Card brand new class: [Fix pt.2](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/commit/588cc2f6c6afae04855286ac58af71177051228d#342e9a38f964201a638923212510f0dc603ef09f_0_1)

## SOLID
[Link to opened issue for A03 group 8](https://code.cs.umanitoba.ca/3350-winter-2021-a03/group8/-/issues/49)

## Retrospective
The retrospective assisted us in managing our time properly. In iteration 1, we had to merge in our implementation just before the deadline. However with iteration 2, we aimed to complete our tasks a day before the deadline. Doing this gave us time to thoroughly go through our implementation and merge safely. There may not be direct proof, but the group felt that in this iteration we only had to do minor fixups in the last day (adding worksheet, architecture,small adjustments), and did not have to do any major merging of features. This is in comparison to last iteration, where we had to merge major features/improvements on the day off the iteration, along with all the aforementioned small adjustments. 

* [Iteration 1 merge requests](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/merge_requests?scope=all&utf8=%E2%9C%93&state=merged&milestone_title=Iteration%201)
* [Iteration 2 merge requests](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/merge_requests?scope=all&utf8=%E2%9C%93&state=merged&milestone_title=Iteration%202)
* [Iteration 1 submission date](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/tags/Iteration-1) 
* [Iteration 2 submission date](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/tags/Iteration-2)

## Design patterns

#### Adapter (used recyclerView adapters) 


* [Example Adapter pattern pt. 1](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/blob/master/app/src/main/java/comp3350/sceneit/presentation/RecyclerViewAdapter.java)
* [Example Adapter pattern pt. 1](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/blob/master/app/src/main/java/comp3350/sceneit/presentation/SearchAdapter.java)
* [Adapter Pattern description](https://en.wikipedia.org/wiki/Adapter_pattern#:~:text=In%20software%20engineering%2C%20the%20adapter,be%20used%20as%20another%20interface)

Used RecyclerView adapter to bing and display the data 


#### Null Object Pattern
* [Example Null Object pattern pt. 1](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/blob/master/app/src/main/java/comp3350/sceneit/presentation/CreditActivity.java)
* [Example Null Object pattern pt. 2](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/blob/master/app/src/main/java/comp3350/sceneit/logic/CreditManager.java)
* [Null object pattern description](https://en.wikipedia.org/wiki/Null_object_pattern)

In CreditActivity a call is made to the CreditManager validateInput() method. This is to check all the info the user entered is correct.

If a piece of info is wrong then validateInput() returns a string with an error message for the CreditActivity to bring up an alert to the user.

If no info is wrong then validateInput() returns null which CreditActivity reads and knows not to display an error and bring the user back to the home page.

## Iteration 1 Feedback Fixes
* [Issue Opened](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/issues/47)
* [Commit link for fix](https://code.cs.umanitoba.ca/3350-winter-2021-a02/group-8/app/-/merge_requests/25/diffs?commit_id=a6754b0affdea16079758742d8435476f1cd3c21)

When choosing a theater location from MainActivity and going to OrderActivity, the location does not update on OrderActivity. So to fix this, we made a listener (IMovieClickListener) that listens to movie clicks in MainActivity and passes data (theater location, movie title, movie rating and movie poster) to OrderActivity.  










