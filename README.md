[![Build Status](https://travis-ci.com/luddwichr/triominos.svg?branch=master)](https://travis-ci.com/luddwichr/triominos)
[![codecov](https://codecov.io/gh/luddwichr/triominos/branch/master/graph/badge.svg)](https://codecov.io/gh/luddwichr/triominos)
[![Maintainability](https://api.codeclimate.com/v1/badges/23a9728a83310e36f54f/maintainability)](https://codeclimate.com/github/luddwichr/triominos/maintainability)

# Game Server and AI for Triominos

## Purpose of this project

The purpose of this project is to experiment with:

* how to implement game play for Triominos with Java (applying TDD and Clean Code practices as much as possible)
* how different drawing and placement strategies can optimize for likelihood of victory by using AI for automated
  gameplay

## Playing Triominos

There exist several variants of the game regarding the tile set, game termination conditions and scoring rules.

Some rule sets can be found here:

- https://www.coololdgames.com/tile-games/triominos/
- http://gambiter.com/domino/Triominoes.html

### Game Setup

The game triominos is played by **at least two and up to six players**.

Depending on the game variant, the game is played with **either 56 or
84 [tiles](#tile-number-up-facing-down-facing-corner)**. In the former case, the corner numbers of the tiles are
monotonically non-decreasing from 0 to 5, resulting in 56 tiles (*"0-0-0", "0-0-1", "0-0-2"*, ..., *"
4-5-5", "5-5-5"*). In the later case, the corner numbers of the tiles are monotonically non-decreasing from 0 to 6,
resulting in 84 tiles.

Each player has a **tray** where she keeps tiles drawn from the pile. Only the player herself can see the tiles. Other
players can only see how many tiles are on the tray, but not which tiles.

Depending on the game variant, the game is played in one or more rounds.

### Round Start

At round start, the tiles are **shuffled randomly** and stacked in a **pile**. The tiles are "face down" so that the
players cannot see the numbers on the tiles.

Each player draws a number of tiles from the pile and places it on her tray. For two players each player draws 9 tiles,
for 3-4 players 7 tiles and for 5-6 players 6 tiles.

#### Who Starts?

There are three variants to determine the first player of a round.

##### Random Selection

In this variant, the first player is selected randomly. This can be done by letting each player role a dice. The player
with the highest number starts (repeat on a draw).

##### Tile With The Highest Score

In this variant, the round is started by the player with the highest scored tile on her tray. The score of a tile is
computed by taking the sum of its corner numbers. However, "triple" tiles (e.g. *"4-4-4" or "2-2-2"*) score higher than
the highest non-triple tile (*"4-5-5"* or *"5-5-6"*, depending on the game variant). When two or more players hold
equally scored tiles, the next highest tile counts and so on until the draw is resolved.

**Note**: this rule is actually not unambiguous. It may be the case that all players have equally scored tiles (e.g.
player A: *"0-0-1", "0-0-2", "0-0-3", "0-0-4", "0-0-5", "2,2,3"* and player B: *"0-1-0", "0-1-1", "0-1-2", "0-2-2", "
0-2-3", "0,3,4"*). Therefore, we define to fall back to the "Random Selection" rule in such configurations.

##### Winner of Last Round

In this variant, the player who won the last round starts the next round.

#### Playing The First Tile

The first player places the first tile on the game board depending on the variant used to determine the first player.

If "Random Selection" or "Winner of Last Round" was used, the first player can pick any tile from her tray to start
with. The player's score is increased by the sum of tile's corner numbers.

**Note**: in this variant, the player can choose to draw up to three tiles from the pile before placing a tile or opting
to not play a tile at all after having drawn three tiles. See [Gameplay](#gameplay) for the score penalties per drawed
tile and not placing a tile.

If "Tile With The Highest Score" was used, the player starts with the highest scored tile on her tray (according to the
scoring rules of the selection variant). The player's score is increased by the sum of the tile's corner numbers. If the
tile is a "triple" tile, the player scores an additional 10 points. If the player has the tile *"0-0-0"* on her tray,
she can opt to play this tile instead and get a score of 40 points.

### Gameplay

After the first player played her tile, the players play in clockwise turn.

Each player can place a single tile per turn. For a [valid placement](#valid-placement), the score is increased
according to the following rules:

- the sum of the corner numbers of the placed tile. E.g., placing a tile *"1-2-3"*
  results in a score of 6. Placing a tile *"5-5-5"* result in a score of 15.
- for a placement that completes a [special figure](#special-figures), additional points are credited as follows:
  - a single hexagon:, 50 points
  - two hexagons:, 100 points
  - three hexagons:, 150 points
  - a completed or extended bridge: 40 points

If the player cannot perform a valid placement or if she does not want to play a tile, she must draw a tile from the
pile at the penalty of 5 points. If the pile is empty, the turn ends, and the player is penalized additional 10 points.

If the player has drawn 3 tiles from the pile (or the pile is empty) and she cannot (or does not want to) place a valid
placement, her turn ends at the cost of additional 10 points.

### End of Round

A game round ends, if:

- A player played the last tile on her tray
- Or, all players subsequently ended their turn without placing a tile (the pile needs not to be empty)

If a player played her last tile, she gets a bonus of 25 points plus the total remaining tile scores the opponents have
yet to play.

If no player could place a tile, the player with the lowest total score of remaining tiles on her tray wins the round.
She gets credited the total tile score of each other player minus her own remaining tile score. Example: Player A: 30
total tile score, Player B: 24, Player C: 50. Here, Player B gets (50-24)+(30-24)=32 points.

**Note**: the rule is ambiguous in case multiple players have the same total tile score. We define that each of those
players get the same score credited, i.e. all of them are winners.

### End of Game

Depending on the game variant, the game ends either if:

- the first (and only) round ended
- or, a player reached 400 points during a round (the round was not necessarily ended by that event, though)

In any case, the player with the highest overall score wins.'

## Terminology

### Tile, Number, Up-Facing, Down-Facing, Corner

A **tile** is a triangular piece, and it is the core element of the game.

It (obviously) has three **corner**s, where each corner has an assigned **number** in the range **0-5**.

Depending on the tile's orientation (see next section for a definition), it is either **up-facing** or **down-facing**.

Corners are referred to as **left**, **middle**, and **right** (when referring to a corner, this nomenclature is more
useful than using the conventional naming of corners with *A, B, C*, because it is independent of the orientation of a
tile).

A tile is denoted as ***"x-y-z"***, where x,y and z are the numbers of the left, middle and right corners, respectively
for base orientation *ABC*.

The following graphic visualizes the concepts introduced in this section:

![Visualization of basic terminology](doc/basic_terminology.svg)

### Rotation, Orientation

The **orientation** of a tile can be changed by **rotating** it. There are six orientations: ***ABC, ACB, CAB, CBA, BCA,
BAC***.

Note: here the naming follows the conventional naming of triangle corners (instead of *left, middle, right*), because it
aids in understanding how the corner numbers change with each rotation.

For instance, the base orientation of the tile *"1-2-3"* is *ABC*. I.e., the left corner is "1", the middle corner is "
2", and the right corner is "3". After rotating the tile clockwise, it is in orientation *ACB*. Now, the left corner
is "1", the middle corner is "3", and the right corner is "2".

To exemplify the concept, all orientations for the tile *"1-2-3"* are depicted below:

![Possible tile orientations](doc/orientation.svg)

### Game Board, Coordinate System, Location

A **game board** is a set of slots, where tiles can be placed.

A 2D **coordinate system** is employed to specify where a tile is placed on the game board.

A tile is located by a tuple ***(x,y)***, where x (*horizontal*) and y (*vertical*) are the coordinates of the
**location**.

The center of the coordinate system is defined at *(0, 0)*.

Per definition, **only an up-facing tile can be placed at the center location**. Whether a tile must be placed up- or
down-facing at a location logically results from this requirement.

The following graphic visualizes the coordinate system.

![Coordinate system visualization](doc/location.svg)

### Placement

A **placement** defines in which *orientation* and at which *location* a given *tile* should be placed on a game board.

For instance, a placement {*"1-2-3"*, *ACB*, *(-2,1)*}, places a tile *"1-2-3"* in orientation *ACB* at location
*(-2/1)*.

### Neighbors

Checking the validity of a placement or calculating its resulting score is a non-trivial task. Therefore, the following
neighbor names are defined to simplify referencing a neighboring location:
**left, right, middle, opposite, far-left, far-right, left-to-opposite, right-to-opposite, left-to-middle,
far-left-to-middle, right-to-middle, far-right-to-middle**.

The following graphics depict which name is referring to which neighbor (for both, up-facing and down-facing tiles).

![Neighbors for up-facing tile](doc/neighbors_up-facing.svg)

![Neighbors for down-facing tile](doc/neighbors_down-facing.svg)

### (Semi-)Adjacent Placement

A placement has an **adjacent placement** if two corners of the placements are tangent. This is the case if a tile was
placed at its *left*, *middle*, or *right* neighbor location.

A placement has a **semi-adjacent placement** if a tile is placed at a neighboring location that only shares a single
corner with the placement. This is the case if a tile was placed at its *far-left, far-left-to-middle, left-to-middle,
right-to-middle, far-right-to-middle, far-right, right-to-opposite, opposite*, or *left-to-opposite* neighbor location.

### Valid Placement

The first placement is valid, if it is placed at *(0,0)* for an up-facing tile and at *(1,0)* for a down-facing tile.

**Note**: The positional "normalization" to the center is enforced so that the size of the game board can be restricted
depending on the number of tiles in the game. The orientation enforcement allows to easily verify that the orientation
of two adjacent placements is opposing so that two corners are tangent.

Any subsequent placement is valid if:

- the tile's orientation and the orientation defined for the location are facing in the same direction
- no other tile has been placed at the location yet
- it has at least one existing adjacent placement
- for each existing adjacent placement, the values of the tangent corners match accordingly
- and captain obvious: the tile must be on the players tray

#### Examples

Consider the following board constellation.

![board constellation to demonstrate (in)validity of placements](doc/placement_validity.svg)

The following placements are invalid:

- {*3-4-5*, *ABC*, *(-1,-1)*}: location is already taken
- {*3-4-5*, *ACB*, *(0,0)*}: orientation of tile and location do not match
- {*2-4-5*, *ABC*, *(0,0)*}: tangent corners do not match with tile at *(-1, 0)*
- {*3-4-5*, *CAB*, *(0,0)*}: tangent corners do not match, neither for *(-1, 0)* nor for *(1, 0)*
- {*3-4-5*, *ACB*, *(0,0)*}: orientation of tile and location do not match
- {*3-4-5*, *BAC*, *(3,0)*}: no adjacent placement (*(1, 0)* is only semi-adjacent)

The only valid placement is: {*3-4-5*, *ABC*, *(0,0)*}

### Special figures

A placement may complete a **special figure**, resulting in a higher score. The different types of special figures are
described in the following sections.

#### Hexagon

A placement completes a **hexagon** if placements for all five neighbor locations around any of its corners exist
already. That is:

- for the left corner, placements must exist for the neighbors *left, far-left, far-left-to-middle, left-to-middle,
  middle*.
- for the middle corner, placements must exist for the neighbors *left, right, left-to-opposite, opposite,
  right-to-opposite*.
- for the right corner, placements must exist for the neighbors *right, far-right, far-right-to-middle, right-to-middle,
  middle*.

This reasoning can be better understood by looking at the graphic in the [Neighbors](#neighbors) section.

To detect a placement that completes multiple hexagons, simply check if hexagons are completed at multiple corners.

##### Example for Single Hexagon

![Example for a single hexagon](doc/hexagon.svg)

##### Example for Double Hexagon

![Example for a double hexagon](doc/double_hexagon.svg)

##### Example for Triple Hexagon

![Example for a triple hexagon](doc/triple_hexagon.svg)

#### Bridge (Completed / Extended)

A placement completes a **bridge** if the corner opposite to an adjacent placement has one or more semi-adjacent
placement(s). That is:

- for an adjacent left neighbor, placements for any of the neighbors *far-left, far-left-to-middle, left-to-middle* must
  exist.
- for an adjacent middle neighbor, placements for any of the neighbors *opposite, left-to-opposite, right-to-opposite*
  must exist.
- for an adjacent right neighbor, placements for any of the neighbors *far-right, far-right-to-middle, right-to-middle*
  must exist.

This reasoning can be better understood by looking at the graphic in the [Neighbors](#neighbors) section.

##### Example for completed bridge

![Example for complete bridge](doc/bridge_completed.svg)

#### Example for extended bridge

![Example for extended bridge](doc/bridge_extended.svg)

## Related work:

https://github.com/AgileBitFlipper/triominos
