# Known bugs in the project

This MD will contain a list of known bugs in the project.
Well, fell free to open an issue if you want to keep
tracking some listed bug.

| Bug                                                                                                          | Status | Since  | Fixed&nbsp;in | Note                                                                                           |
|--------------------------------------------------------------------------------------------------------------|:------:|:------:|:-------------:|------------------------------------------------------------------------------------------------|
| Wrong scaled shulker hitbox alignment                                                                        | Fixed  | 1.21.2 |    1.21.3     | F3-B and see the hitbox                                                                        |
| Wrong scaled item frame model alignment                                                                      | Fixed  | 1.21.2 |    1.21.2     | Increase or decrease the scale and you will se the model floating or inside the attached block |
| Killed slimes doesn't transfer the scales for the "children"                                                 |  Open  | 1.21.2 |       -       | Kill a level 2 or 3 slime. The new slimes will have the default scales                         |
| Wrong player model alignment when model height is < 1 while sneaking                                         |  Open  | 1.21.2 |       -       | Decrease your scale, sneak. You will be inside the ground (if you're not falling)              |
| "Bedrock slab" from scaled Ender Crystal has a bad alignment                                                 |  Open  | 1.21.2 |       -       | Maybe the whole entity has a bad alignment                                                     |
| Rendering a Leash causes a insta-crash                                                                       | Fixed  | 1.21.2 |    1.21.5     |                                                                                                |
| Shulker's eyes stuck inside the attached block when upside-down or attached horizontally                     |  Open  | 1.21.3 |       -       |                                                                                                |
| Leash doesn't render in the correct alignment                                                                |  Open  | 1.21.5 |       -       | Use a lead at any leashable entity.                                                            |
| Ender Dragon's interaction hitboxes does not follow the scaling                                              |  Open  | 1.21.5 |       -       |                                                                                                |
| Physics from projectiles thrown by an entity seems wrong when the owner (and the projectile) are scaled down |  Open  | 1.21.2 |       -       |                                                                                                |
| Ender dragon's and Wither's flying speed is wrong when scaled up or down                                     |  Open  | 1.21.6 |       -       | Check another entities (and if found another, update the bug title)!                           |
| Bob view speed isn't scaled based on the... scale                                                            |  Open  | 1.21.9 |       -       | Too slow if scaled down and too fast if scaled up                                              |
