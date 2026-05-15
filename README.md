# YourProj

SE 350 Project

• Checklist:

– Are you in a Group?

No, I am not in a group.

– If so, who else is in your group?

There is no one in my group

– What programming language are you selecting?

I will be using java.

– Do you have your GitHub account set up?

Yes.

– Do you have a public repository for your Project?

Yes.

– What is the link to your GitHub repository?

https://github.com/angelaj18/YourProj.git

– If you are in a group, does everyone have access to the github repo?

I don't have anyone else in my group, but I do have the link :)

– Do you have a “Hello World” program that compiles and runs?
Yes, it compiles and runs.

-Where is the entry point to your project?(src/main/Main.java for example)
YourProj/src/main/Main.java

## Sprint 2 Plan and Progress

### Project Idea
Alegna Dispach is a superhero dispatch simulation where the player acts as a command dispatcher instead of controlling heroes directly. The game focuses on balancing city safety, public trust, and hero stress.

### Sprint 2 Scope
- Build a runnable Java prototype using CLI output.
- Simulate incidents across multiple cities.
- Dispatch available heroes to incidents.
- Resolve outcomes and print a turn report with city and hero metrics.

### Current Sprint 2 Prototype
- Two cities with independent safety and trust scores.
- Hero roster with power and stress levels.
- Random incident generation per city.
- Dispatch and resolution flow with success/failure consequences.

### Entry Point
`src/main/Main.java`

## Sprint 3

### Intended final submission
By the end of the course I want Alegna Dispach to be a text-based dispatcher sim you can demonstrate end-to-end: compile and run from `Main`, manually assign heroes to incidents across at least one full turn, see outcomes and updated city and hero stats, and point to six GoF-style custom patterns in my own code (Strategy and Factory are in place; more will be added before the final). I also plan to ship UML covering each pattern, a short demo video that walks the repo, and a README that lists any gaps and bugs.

### Sprint 3 — pattern and implementation notes
Refactoring to Strategy and Factory meant touching `DispatchCenter` and `Main` more than once; the trickiest part was keeping resolution logic in strategy classes while still printing villain-only flavor from the dispatcher without blurring responsibilities. Manual dispatch required a clear contract (`List<Incident>` in difficulty order plus parallel `List<Hero>` assignments) so the simulation stays deterministic. I am temporarily deferring the full UML pass until the class graph for the two patterns is stable so the diagram stays accurate. If time runs tight before the final, the highest-risk gap would be finishing the remaining four patterns and multi-turn play; I will call those out in the README as needed.
