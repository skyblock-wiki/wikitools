# Manual Tests (Fabric)

## Environment

- Minecraft 1.21.5
- Fabric Loader 0.16.14
- Mods: Fabric API 0.127.1+1.21.5, WikiTools 2.7.0+1.21.5

## Tests

Tester should set the following fields before or after performing tests. Tester should time their test session, which should include the time to launch Minecraft with the test environment. If there is any ambiguity in the test document or suggestions for new tests, please write down in the `Comment/Suggestion` field.

- Tester: Not set
- Test date: Not set
- Time taken: Not set
- Comment/Suggestion (optional): Not set

Tester should perform each test and write the test result in the `Result` field. The test result should be "OK" if the `Action` is accurate and the output exactly matches `Expected Output`. Otherwise, write down the problem.

#### Copy tooltip of hovered item as template call by pressing X
- Action: Go to a Hypixel lobby and open the inventory. At the bottom-left, hover over the compass for game menu. Press X.
- Expected Output:
  - Game message: Copied tooltip (template formatting)
  - Clipboard content: {{Slot|Game Menu (Right Click)|title=&aGame Menu &7(Right Click)|text=&5&7Right Click to bring up the Game Menu!}}
- Result: Not set

#### Copy tooltip of hovered item as template call by pressing Shift-X
- Action: Go to a Hypixel lobby and open the inventory. At the bottom-left, hover over the compass for game menu. Press Shift-X.
- Expected Output:
  - Game message: Copied tooltip (module formatting)
  - Clipboard content: ['Game Menu (Right Click)'] = { name = 'Game Menu (Right Click)', title = '&aGame Menu &7(Right Click)', text = '&5&7Right Click to bring up the Game Menu!', },
- Result: Not set

#### Copy texture ID of hovered player head item by pressing Z
- Action: Go to a Hypixel lobby and open the Game Menu. Hover over the SkyBlock icon. Press Z.
- Expected Output:
  - Game message: Copied skull ID
  - Clipboard content should start with: d7cc66
  - Clipboard content should end with: 4e7bf8

#### Copy texture ID of the head equipment slot of an entity by pressing Z
- Action: Go to the SkyBlock hub and head to -15 69 -109. Slowly sink into the village well at the next water block while facing East. A fairy soul can be seen at -15 65 -109. Point the cursor at it and press Z.
- Expected Output:
  - Game message: Copied skull ID
  - Clipboard content should start with: 299ea1
  - Clipboard content should end with: e8a793

#### Copy texture ID of a placed player head by pressing Z
- Action: Go to the SkyBlock hub and head to -15 68 -108. Point cursor to the placed player head of a cauldron of water. Press Z.
- Expected Output:
  - Game message: Copied skull ID
  - Clipboard content should start with: 848a19
  - Clipboard content should end with: c77ba6
