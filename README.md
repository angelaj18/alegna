# alegna

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

## Sprint 4

### Intended final submission
For the final project I want to demonstrate Alegna Dispach as a runnable CLI dispatcher sim: compile and run from `Main`, manually assign heroes to incidents for one full turn, see HR-style dispatch logs and a turn report with updated city and hero stats, and walk through four GoF-style custom patterns already in my code (Strategy, Factory, Observer, Command). Before the final I still need two more patterns (six total for solo), optional multi-turn shifts, a demo video that points to pattern-related files, this README with incomplete features and bugs listed, and a zipped source submission on D2L with the GitHub link in the comment box.

### Sprint 4 — pattern and implementation notes
Observer was introduced with `GameEventListener` and `HrDispatchFeed`, then refactored to `DispatchNotifier` so multiple listeners (including `AuditTrailListener`) can subscribe without hard-coding one feed inside `DispatchCenter`. Command replaced raw hero assignment lists with `DispatchCommand` implementations (`AssignHeroCommand`, `SkipIncidentCommand`) executed through `DispatchCenter.resolveTurnFromCommands`. The main difficulty was keeping Strategy and Observer behavior intact while changing how dispatch input is represented; villain flavor text and HR log lines overlap slightly in tone but serve different layers (report vs live feed). UML for all four patterns is in `design-patterns-uml.drawio` at the repo root (open in draw.io / diagrams.net). Remaining risk before final: adding two more patterns without over-refactoring, and expanding gameplay beyond a single turn if time allows.

## Sprint 5

### Intended final submission
Alegna Dispach is a CLI superhero dispatch sim: run `Main`, play through three shifts, manually assign heroes to incidents per shift, and watch HR dispatch logs, audit lines, and per-shift reports update city safety, public trust, and hero stress/status. The project implements six custom GoF-style patterns in my own Java code: Strategy, Factory, Observer, Command, State, and Template Method. 

### Sprint 5 — pattern and implementation notes
State replaced a simple availability flag with `HeroState` implementations (`ReadyState`, `OnMissionState`, `RecoveringState`) so overstressed heroes enter an HR hold and cannot be redeployed until they recover across later shifts. Template Method moved the per-shift workflow into `ShiftTemplate` with `CliDispatchShift` supplying CLI-specific hooks, and `Main` now loops three shifts instead of owning all turn logic inline. The hardest part was refactoring without breaking Command, Observer, and Strategy behavior that already depended on `DispatchCenter`. Stress thresholds are tuned so recovery is visible within a multi-shift session.

### Final submission — known gaps and bugs
- No graphical UI; CLI only (Scanner input).
- No save/load between sessions; all state is in-memory for one run.
- Win/lose conditions are not fully implemented; the sim ends after three shifts with a sign-off message.
- Random incidents can occasionally produce duplicate incident types across cities in the same shift.
- Villain codenames are random and not persisted across shifts for the same threat.
- If invalid non-numeric input is entered at prompts, the program re-prompts (no crash), but there is no quit command mid-shift.
