# Branching Strategy

The main branching strategy we're using is https://trunkbaseddevelopment.com/ aka the "Cactus Model".

A video walk through of this workflow is available here: https://drive.google.com/file/d/16qOcEkBrjQvYHRaXTBm9y_JRr9O3U7JY/view?usp=sharing

## Developer Workflow

The developer workflow works as follows,
1. The developer creates a branch for the developer task they're working on. The branch names should be `<user-id>/<issue-number>-<brief-text-description>`. Ex. for the branch implementing this document, the branch is `jacob/38-document-branching-strategy`. 
2. The developer may create a many temporary commits as they wish, but before being merged to master, each logical change needs to be seperated into its own commit. This means that if you implement a UI and business logic for a single developer task, there should be two commits. Ex. 
    - ```
        commit ef99023df7a3c496b8a302013c3b2485f168ffe4
        Author: Naol Chemeda <chemedandd@gmail.com>
        Date:   Thu Feb 11 13:47:54 2021 -0600

            UI: Add movie listing screen
            
        commit jhfdlaksjhflkjashfkjlhadkslfhkjadsfhfs34 
        Author: Naol Chemeda <chemedandd@gmail.com>
        Date:   Thu Feb 11 13:47:54 2021 -0600

            Business: Add movie filtering logic
            
        ```
    - The easiest way to accomplish this is using `git rebase -i master`. This document won't show how that is done, but the information is readily available online, or you can feel free to ask me (@jacob) for help.
3. Once everything has been commit and organised into logical commits, a merge request must be created. (Though this MR can be made earlier in the process if desired)
4. Before merging the requst into master, the following conditions must be met.
    1. The CI tests are passing. These handle automatically running our JUnit tests. This can visually be seen on the merge request screen by having a green checkmark and stating something like `Pipeline #818 passed for 67197fe7 on jacob/ci-test`. Note: These may take 5-10 minutes after your push your code for this to finish running.
    2. Each merge request must have two "approvals" by developers other than the one who did the implementation. Ex. If I Jacob, implemented this document for a ticket, I need two people, ex. Tim and Naol to approve the changes.
        - The benefits of this are twofold, it gives developers other than the implementer a chance to understand and ask questions about code being implemented by others, and it allows more eyes to find bugs before being merged to master.
    3. The branch must be rebased to master. This means that the branch your working on should contain all the commits on master + the commits for your merge. Again, this can be done via `git rebase -i master`. (Ask @jacob for assistance if needed)
5. Once all these conditions are met, you just hit the green merge button on gitlab.

## Versioning

At the end of each iteration a git tag will be created to tag the working version. Ex `git tag release/iteration-1`. These tags should then be documented in the main readme to allow markers/others to quickly find the working version of the code they're looking for.
