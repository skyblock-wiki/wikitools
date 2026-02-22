# For Contributors

## Current Practices

- Use coding best practices.
- Write good tests, automated or manual. Manual tests are located in the `manual-tests` directory.

## Project Structure
- This is a [multi-project build](https://docs.gradle.org/current/userguide/multi_project_builds.html). There are two projects: `core` and `fabric`. `fabric` depends on `core`.
  - `core`: The part not tied to Minecraft/Fabric or HTTP. This part can contain domain and application services, which can be independently tested.
  - `fabric`: The part that can interact with Minecraft and the Internet. All event listening and information retrieval is handled here.
- For each project, there are two possible subdirectories: `feature` and `common`.
    - Each subdirectory of `feature` consists of a feature of the mod.
    - Normally, feature code should remain isolated to that feature until it becomes beneficial to share a piece of code.
    - The `common` directory consists of code that are sparingly shared between features.
