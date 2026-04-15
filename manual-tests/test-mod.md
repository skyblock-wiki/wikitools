# Manual Tests (Mod)

## Environment

- Minecraft 1.21.11
- Fabric Loader 0.18.4
- Mods: Fabric API 0.141.3+1.21.11, WikiTools 3.0.0+1.21.11
- Internet connection

## Instruction

Tester should set the following fields before or after performing tests. Tester should time their test session, which should include the time to prepare the test environment.

- Tester: Not set
- Test date: Not set
- Time taken: Not set

Tester should perform each test and write the test result in the `Result` field. The test result should be "OK" if the `Action` is accurate and the output exactly matches `Expected Output`. Otherwise, put "FAIL:" and write down the problem.

## Test Cases

### Copy Data Tags

#### Copy data tags of hovered item by pressing N
- Action: Join Hypixel SkyBlock and go to the SkyBlock hub, and open the inventory. At the bottom-right, hover over the SkyBlock Menu icon. Press N.
- Expected Output:
  - Game message: `Copied data tags`
  - Clipboard content contains: `minecraft:nether_star`
  - Clipboard content contains: `{id:"SKYBLOCK_MENU"}`
- Result: Not set

#### Copy data tags of facing NPC by pressing N
- Action: Join Hypixel SkyBlock, go to the SkyBlock hub, and head to -5 69 -21. When facing the Hub Selector NPC, press N.
- Expected Output:
  - Game message: `Copied data tags`
  - Clipboard content contains: `Pos:[-5.5d,69.0d,-22.5d]`
  - Clipboard content contains: `__gameProfile:`
  - Clipboard content contains: `value=ewogICJ0aW`
- Result: Not set

#### Copy data tags of armor stands with floating text by pressing N
- Action: Join Hypixel SkyBlock, go to the SkyBlock hub, and head to -5 69 -21. Stand within one block of the Hub Selector NPC and point cursor at the lower half of the "CLICK" text above the NPC. Press N.
- Expected Output:
  - Game message: `Copied data tags`
  - Clipboard content contains: `Hub Selector`
  - Clipboard content contains: `Pos:[-5.5d,69.0d,-22.5d]`
- Result: Not set

#### Copy data tags of a block entity head by pressing N
- Action: Join Hypixel SkyBlock, go to the SkyBlock hub, and head to 5 71 -52. Point cursor to the placed player head of a mini TNT block you are standing on. Press N.
- Expected Output:
  - Game message: `Copied data tags`
  - Clipboard content contains: `x:5,y:71,z:-53`
  - Clipboard content contains: `__gameProfile:`
  - Clipboard content contains: `value=eyJ0ZXh0dX`
- Result: Not set

### Copy Hovered Item Tooltip

#### Copy tooltip of hovered item as template call by pressing X
- Action: Go to a Hypixel lobby and open the inventory. At the bottom-left, hover over the compass for game menu. Press X.
- Expected Output:
  - Game message starts with: `Copied tooltip`
  - Game message contains: `template formatting`
  - Clipboard content: `{{Slot|Game Menu (Right Click)|title=&aGame Menu &7(Right Click)|text=&7Right Click to bring up the Game Menu!}}`
- Result: Not set

#### Copy tooltip of hovered item as module data by pressing Shift-X
- Action: Go to a Hypixel lobby and open the inventory. At the bottom-left, hover over the compass for game menu. Press Shift-X.
- Expected Output:
  - Game message starts with: `Copied tooltip`
  - Game message contains: `module formatting`
  - Clipboard content: `['Game Menu (Right Click)'] = { name = 'Game Menu (Right Click)', title = '&aGame Menu &7(Right Click)', text = '&7Right Click to bring up the Game Menu!', },`
- Result: Not set

### Copy Opened UI

#### Copy opened UI in default mode by pressing C
- Special setting: Change your game language to Afrikaans.
- Action: Go to SkyBlock and open the SkyBlock Menu. Click the Collections icon. Click the Mining Collections icon, then click the Cobblestone icon to open the Cobblestone Collection UI. Press C.
- Expected output:
  - Game message starts with: `Copied UI`
  - Clipboard content first line: `{{UI|Cobblestone Collection`
- Expected output to verify that number of rows is correct:
  - Clipboard content contains line: `|rows=6`
- Expected output to verify that close item is detected:
  - Clipboard content contains line: `|close=6, 5`
- Expected output to verify that go-back item is detected:
  - Clipboard content contains line: `|arrow=6, 4`
  - Clipboard content contains line: `|goback=&7To Mining Collections`
- Expected output to verify that stack size is correct and copying mode is correct:
  - Clipboard content contains line that starts with: `|3, 2=Cobblestone II; 2, none,`
- Expected output to verify that copying mode is correct:
  - Clipboard content contains line: `|fill=false`
  - Clipboard content contains line: `|1, 1=Blank, none`
- Result: Not set

#### Copy opened UI in fill with blank by default mode by pressing Shift-C
- Special setting: Change your game language to Afrikaans.
- Action: Join Hypixel SkyBlock, go to the SkyBlock hub, and head to 8 69 -70. Click the Zog NPC to open the Zog UI. Press Shift-C.
- Expected output:
  - Clipboard content contains line: `|close=none`
  - Clipboard content contains line: `|arrow=none`
- Expected output to verify that copying mode is correct:
  - Clipboard content *does not* contain line: `|fill=false`
  - Clipboard content contains line: `|5, 2= , none`
- Result: Not set

#### Copy opened UI in always use Minecraft item name for non skull items mode by pressing Ctrl-C
- Special setting: Change your game language to Afrikaans.
- Action: Join Hypixel SkyBlock, go to the SkyBlock hub, and head to 8 69 -70. Click the Zog NPC to open the Zog UI. Press Ctrl-C.
- Expected output to verify that non-skull items are copied in Minecraft item name in English, displayed name and lore are copied correctly:
  - Clipboard content contains line that starts with: `|6, 5=Hopper, none,`
  - Clipboard content contains line that ends with: `&aSell Item, &7Click items in your inventory to sell/&7them to this Shop!`
- Expected output to verify that enchanted works:
  - Clipboard content contains line that starts with: `|2, 2=Enchanted Iron Sword, none,`
- Expected output to verify that skull items are copied in displayed name:
  - Clipboard content contains line that starts with: `|3, 7=Bubblegum, none,`
- Result: Not set

### Copy Skull ID

#### Copy texture ID of hovered player head item by pressing Z
- Action: Go to a Hypixel lobby and open the Game Menu. Hover over the SkyBlock icon. Press Z.
- Expected Output:
  - Game message: `Copied skull ID`
  - Clipboard content should start with: `d7cc66`
  - Clipboard content should end with: `4e7bf8`
- Result: Not set

#### Copy texture ID of the head equipment slot of an entity by pressing Z
- Action: Join Hypixel SkyBlock, go to the SkyBlock hub, and head to 18 72 -2. Slowly sink into the village well at the next water block while facing East. A fairy soul can be seen at 18 68 -2. Point the cursor at it and press Z.
- Expected Output:
  - Game message: `Copied skull ID`
  - Clipboard content should start with: `299ea1`
  - Clipboard content should end with: `e8a793`
- Result: Not set

#### Copy texture ID of a placed player head by pressing Z
- Action: Join Hypixel SkyBlock, go to the SkyBlock hub, and head to 5 71 -52. Point cursor to the placed player head of a mini TNT block you are standing on. Press Z.
- Expected Output:
  - Game message: `Copied skull ID`
  - Clipboard content should start with: `dc75cd`
  - Clipboard content should end with: `b6d294`
- Result: Not set

#### Copy texture ID of a hovered player head item by pressing Z works with resolvable profiles
- Action: Open a creative world with cheats on. Run the command `/give @p minecraft:player_head[profile=Player]`. Open the inventory and hover over the player head. Press Z.
- Expected Output:
  - Game message: `Copied skull ID`
  - Clipboard content is a continuous string of numbers and letters of at least 56 characters
- Result: Not set

#### Copy texture ID of a placed player head by pressing Z works with resolvable profiles
- Action: Open a creative world with cheats on. Run the command `/give @p minecraft:player_head[profile=Player]`. Place the player head and point cursor to the placed head. Press Z.
- Expected Output:
  - Game message: `Copied skull ID`
  - Clipboard content is a continuous string of numbers and letters of at least 56 characters
- Result: Not set

### View Item ID

#### Item ID is shown on hovered SkyBlock item when advanced tooltip is set to "shown"
- Action: Toggle advanced tooltip to "shown" by using F3+H. Join Hypixel SkyBlock, go to the SkyBlock hub, and open the inventory. At the bottom-right, hover over the SkyBlock Menu icon.
- Expected Output:
  - Displayed tooltip content should end with: `Item ID: SKYBLOCK_MENU`
- Result: Not set

#### SkyBlock item ID is not shown on hovered SkyBlock item when advanced tooltip is set to "hidden"
- Action: Toggle advanced tooltip to "hidden" by using F3+H. Join Hypixel SkyBlock, go to the SkyBlock hub, and open the inventory. At the bottom-right, hover over the SkyBlock Menu icon.
- Expected Output:
  - Displayed tooltip content should end with: `Click to open!`
- Result: Not set

### Any Other Tests (Optional)

Testers can test the mod with whatever conditions they want and write any problem they found here.

### Comments and Suggestions (Optional)

Testers can provide their comments and suggestions about the mod and/or the manual tests here. They can also report any ambiguity in the test document or suggest new tests here.
