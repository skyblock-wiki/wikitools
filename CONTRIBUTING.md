# For Contributors

## Current Practices

- Use coding best practices.
- Write good tests, automated or manual.

## Project Structure

- This is a [multi-project build](https://docs.gradle.org/current/userguide/multi_project_builds.html). There are two projects: `core` and `fabric`. `fabric` depends on `core`.
  - `core`: Domain and application services of the project. This part is not tied to Minecraft/Fabric or HTTP and can be independently tested.
  - `fabric`: The fabric mod and infrastructure code. This part that can interact with Minecraft and the Internet. Event handling and information retrieval are implemented here.
- For each project,
  - In `src/main/...`, there are two possible subdirectories: `feature` and `common`.
  - Each subdirectory of `feature` consists of a feature of the mod.
  - Normally, feature code should remain isolated to that feature until it becomes beneficial to share a piece of code.
  - The structure of the feature is up to the developer's decision. Smaller features may not even place code in `core`.
  - The `common` directory consists of code that are sparingly shared between features.
  - In the fabric project, there is another subdirectory `mixin` for mixins used by fabric. The name of a subdirectory of `mixin` should either be `common` or match the name of a feature. Code isolation for features and sparse sharing of code in `common` apply here.
  - Tests are located in `src/test/...`.
  - Test libraries currently in use: JUnit5, MockServer (for mocking external API)
- Manual tests are located in the `manual-tests` directory. To ensure correctness, it is recommended to write manual tests to test code that is not covered in automated tests.

## Important Files

In the fabric project, there are several important files:

- `build.gradle` is the Gradle build script
- `gradle.properties` contains project properties
- `resources/fabric.mod.json` contains mod information
- `resources/wikitools.mixins.json` registers mixins used by the mod (e.g. `common.HandledScreenAccessor`)
- `resources/config.properties` contains properties loaded into `WikiToolsProperties`

We set up `build.gradle` in a way that properties defined in `gradle.properties` can be used in `fabric.mod.json` and `config.properties` using the `${...}` format.

To bump mod version, update `mod_version` in `gradle.properties`. Refresh Gradle.

To update Minecraft version, update the fabric properties and dependency versions in `gradle.properties`, and also the `depends` object in `resources/fabric.mod.json`. There may be more things that needs updating - see [fabric porting guide](https://docs.fabricmc.net/develop/porting/) for your Minecraft version. Refresh Gradle.

## Development Environment

[DevAuth](https://github.com/DJtheRedstoner/DevAuth) can be optionally enabled to authenticate Minecraft accounts in development environments.
