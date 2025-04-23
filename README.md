# UnclaimFinder
_Developed by Juan Valero (Choukas)_

## About
UnclaimFinder is a plugin that allows players to find containers in a specified range.

## Configuration
```yaml
finders: # The list of finders to be used
  - namespace: "minecraft:compass" # The namespace of the finder
    range: 30 # The range in blocks to search for containers
    containers: # The list of container namespace to search for (e.g., chests, furnaces, etc.)
      - "minecraft:chest"
      - "minecraft:furnace"
    # Sent to the player when the finder is used
    # {containers} will be replaced with the number of containers found
    # {range} will be replaced with the finder's range
    message: "&7UnclaimFinder &8| &7&c{containers} &7conteneur(s) trouvé(s) dans un rayon de &c{range} &7bloc(s)"
  - namespace: "minecraft:diamond"
    range: 10
    containers:
      - "minecraft:enchanting_table"
    message: "&7UnclaimFinder &8| &f&c{containers} &fconteneur(s) trouvé(s) dans un rayon de &c{range} &fbloc(s)"
```
