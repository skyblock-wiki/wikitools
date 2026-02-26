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
  - Tests are located in `src/test/...`.
  - Test libraries currently in use: JUnit5, MockServer (for mocking external API)
- Manual tests are located in the `manual-tests` directory. To ensure correctness, it is recommended to write manual tests to test code that is not covered in automated tests.
- Diagrams, documentations, and ADRs are located in the `structurizr` directory. It is recommended to keep these up to date.
  - The web interface can be opened by running the Structurizr local docker image (see [Quickstart](https://docs.structurizr.com/local/quickstart)).
  - Example command to run in the project folder: `docker run -it --rm -p 8080:8080 -v ./structurizr:/usr/local/structurizr structurizr/lite`
