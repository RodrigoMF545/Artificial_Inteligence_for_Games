# Artificial Intelligence for Games

Coursework repository exploring classic game-AI techniques in Java, split into three self-contained projects: **steering behaviors**, **pathfinding with A\*** in a simplified RTS, and a **behavior tree** agent for the Mario AI Benchmark.

Each project pairs a small/medium Java codebase with a written report (`.pdf`/`.docx`) describing the design and results.

## Repository Structure

```
.
├── Projeto_1 (SteeringBehaviores)/     # Steering behaviors — car simulation
│   ├── Steering/
│   │   ├── graphics/                   # Car sprites
│   │   └── src/
│   │       ├── engine/                 # Game loop, vectors, game objects
│   │       ├── controllers/            # Seek, Arrive, Wall Avoidance, Keyboard
│   │       └── test/                   # Runnable demo scenarios
│   ├── Steering.rar                    # Archived copy of the project
│   └── P1-Steering Behaviors*.{docx,pdf}   # Written report
│
├── Projeto_2/                          # Pathfinding — A* search
│   ├── S3-CS387/                       # S3 RTS engine (base framework)
│   │   ├── src/s3/ai/                  # AStar.java + built-in rush AIs
│   │   ├── src/s3/base/                # Main entry point
│   │   ├── src/s3/experimenter/        # Batch experiment runner
│   │   ├── maps/                       # XML map files
│   │   └── libs/                       # Third-party jars (jargs, jdom, xerces...)
│   └── Pathfinding in S3.pdf           # Written report
│
└── Projeto_3 (MarioAi)/                # Behavior tree — Mario AI Benchmark
    ├── MarioAI/
    │   ├── src/ch/idsia/agents/controllers/
    │   │   ├── BehaviorAgent.java          # Custom BT-driven agent
    │   │   └── BT_MarioImplementacion/     # Tree, Sequence, condition/action nodes
    │   ├── src/ch/idsia/scenarios/Main.java  # Entry point (wired to BehaviorAgent)
    │   └── lib/                            # Third-party jars
    ├── arvore de comportamento.drawio       # Behavior tree diagram (draw.io)
    └── Scripting in Super Mario.pdf         # Written report
```

## Projects

### 1. Steering Behaviors

A 2D top-down car simulation built on a small custom Java/Swing engine, used to demonstrate classic autonomous-movement algorithms:

- **Seek** (`SeekController`) — steer directly toward a target.
- **Arrive** (`ArriveController`) — seek with deceleration inside a slowing radius so the car comes to rest at the target instead of overshooting.
- **Wall Avoidance** (`WallAvoidanceController`, `WallAvoidanceController_2`) — raycasting against obstacles to steer away from walls.
- **Keyboard** (`KeyboardController`) — manual control, used as a baseline/comparison.

The engine (`engine/`) provides the game loop, rendering window, 2D vector math, cars, obstacles, and markers.

**Run it:** each scenario in `src/test/` (`SeekScenario`, `ArriveScenario`, `WallAvoidanceSeekScenario`, `KeyboardExample`) has its own `main` method — run any of them directly from your IDE to open the simulation window.

### 2. Pathfinding — A\* in S3

Built on top of **S3**, a simplified RTS simulator (the `S3-CS387` framework, originally developed for a Georgia Tech AI-for-games course). This project adds:

- **`s3/ai/AStar.java`** — A\* pathfinding used by units to navigate the map.
- Several **built-in scripted opponents** under `s3/ai/builtin/` (`RushAI`, `FootmenRush`, `ArchersRush`, `KnightsRush`, `DefensiveKnightsRush`, `CatapultRush`) to test the pathfinding against.
- A set of **XML maps** (`maps/`) of varying sizes for testing.
- An **`Experimenter`** class for running batches of matches automatically.

**Run it:** launch `s3.base.Main`, which accepts command-line flags for the map and player types, e.g.:

```
Usage: S3 -m map -i interval [-t method] [-u user] [-p playerType|idname|AIType|ME]...
```

Make sure the jars in `S3-CS387/libs/` (`jargs`, `java-cup-11a-runtime`, `jdom`, `xercesImpl`) are on the classpath.

### 3. Behavior Tree — Mario AI

Built on top of the **Mario AI Benchmark** (the IDSIA/Karakovskiy–Togelius framework for Infinite Mario Bros.). The custom addition is a simple **behavior tree** agent:

- **`BehaviorAgent.java`** — extends the framework's `BasicMarioAIAgent`; on every tick it resets its action set and runs the tree.
- **`BT_MarioImplementacion/`** — the tree/sequence implementation and its leaf conditions and actions.
- The tree is a priority list of `Sequence`s, evaluated top to bottom, e.g. *"is a coin near? → collect it"*, *"is an enemy near? → shoot"*, *"can I go forward? → move forward"*, with fallbacks for jumping and backing away from danger.
- **`arvore de comportamento.drawio`** contains the visual diagram of the tree (open with [draw.io](https://app.diagrams.net/)).

**Run it:** `ch.idsia.scenarios.Main` is already configured to instantiate `BehaviorAgent` and play a single episode — just run its `main` method. The jars in `MarioAI/lib/` (`asm-all-3.3`, `jdom`, `junit-4.8.2`) must be on the classpath.

## Requirements

- **JDK 8** is recommended — both third-party frameworks (S3 and the Mario AI Benchmark) predate modern Java and use Swing/AWT graphics.
- **IntelliJ IDEA** — every project already ships with its own `.iml` module file, so the simplest path is to open the relevant `Projeto_X` folder as a project/module and run a scenario's `main` method directly.
- No Maven/Gradle build is included; all third-party dependencies are pre-bundled as `.jar` files under each project's `libs/`/`lib/` folder.

## Reports

Each project includes a written report with design decisions, implementation details, and results:

- `Projeto_1 (SteeringBehaviores)/P1-Steering Behaviors.pdf`
- `Projeto_2/Pathfinding in S3.pdf`
- `Projeto_3 (MarioAi)/Scripting in Super Mario.pdf`

## Notes

- `Projeto_2` and `Projeto_3` build on external academic frameworks (S3 and the Mario AI Benchmark, respectively); the coursework contributions are the AI logic layered on top (A\* search and the behavior tree agent), not the underlying engines.
- Compiled output (`out/`) and IDE metadata (`.idea/`, `*.iml`) are currently tracked in the repo; consider adding a `.gitignore` for these if you continue developing the projects.

## Author

Rodrigo M. F. — coursework for an Artificial Intelligence for Games class.
