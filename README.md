# Pehkui: Continued

**Pehkui: Continued** is an updated and enhanced version of the original Pehkui mod. Pehkui is a library mod
for the Quilt, NeoForge and Fabric mod loaders that allows mod developers to modify the size of entities

> [!IMPORTANT] This project is a downstream fork of the original
> [Virtuoel/Pehkui](https://github.com/Virtuoel/Pehkui) repository. It's an independent fork that
> is dedicated to ensuring the mod remains up-to-date.

## For players

Here a list of required mods for each supported platform:

### Quilt

- Latest version of the [Quilt mod loader](https://quiltmc.org/en/install/)
- Latest version of the [Quilt Standard Libraries](https://modrinth.com/mod/qsl/versions)

### NeoForge

- Latest version of the [NeoForge mod loader](https://files.minecraftforge.net/) for whichever Minecraft version you're
  playing on.

### Forge

- Latest version of the [Forge mod loader](https://neoforged.net/) for whichever Minecraft version you're playing on

### Fabric

- Latest version of the [Fabric mod loader](https://fabricmc.net/use/installer/)
- Latest version of the [Fabric API mod](https://modrinth.com/mod/fabric-api/versions) for whichever Minecraft version
  you're playing on

## Mod loaders compatibility

|  Mod loader  | Current Supported Minecraft versions   |
|:------------:|:---------------------------------------|
|  **Fabric**  | 1.14.4-1.21.11                         |
|  **Quilt**   | 1.14.4-1.21.1                          |
| **NeoForge** | 1.20.1-1.21.1                          |
|  **Forge**   | 1.16.5; 1.17.1; 1.18.x; 1.19.x; 1.20.1 |

> [!NOTE]
> Current development is focused on achieving a stable release for **Fabric**.
> Support for Quilt and NeoForge will be fully updated once the core architecture stabilizes.

## What changed in this project

#### Branching

- **Version-specific Branches**: Each update for a new Minecraft version now has its own branch;
- **Stability Flow:** Features are developed in `dev` branches and merged into `main` only after reaching high
  stability.
- **Critical Bug Fixing**: Critical bug fixes (if done) will be merged into main branch and development

#### Config System

Replacing the external *Kanos Config* dependency with a **built-in implementation** for handling configurations,
reducing the number of required dependencies.

## What is coming on Pehkui for Minecraft version 26.1?

Minecraft 26.1 is a big change for the mod community. The transition to newer Minecraft versions requires
a significant refactor of the compatibility system. **Pehkui: Continued** is moving away from the
"Single JAR" approach (which supported all versions from 1.14 to latest). In newer releases, for older
Minecraft versions is going to drop the support, while more recent versions is going to have updates for
bug fixes (specially for versions 1.21.2 to 1.21.11)

### Technical info

#### Minecraft compatibility workflow

Starting from version 5.0.0 for Pehkui, the internal Minecraft compatibility system for Mixins using
"compat-packages" (e.g. compat1205plus, compat115minus, compat116) is going to be removed. These changes
make the update workflow for newer Minecraft versions easier, because you won't need to add new Mixins for
the newer version and keeping an outdated mixin in the project. And this prevents to fail the build process
if a package or class got renamed. This also brings initialization performance

#### Mixins Architectural Refactor

The "compat-packages" system (e.g., `compat1205plus`, `compat115minus`) is going to be removed.
Mixins is going to be **mirrored to Minecraft's internal package structure**. _This approach
also improves initialization performance._

*Example:* A Mixin targeting `net.minecraft.world.entity.Entity` is now located at
`virtuoel.pehkui.mixins.world.entity.EntityMixin`. This makes the code easier to maintain against
Mojang's renames.


## For developers

To integrate Pehkui into your project, add the following to your `build.gradle`:

``` Gradle
repositories {
	maven { url: 'https://jitpack.io }
}

dependencies {
// Replace VERSION with your target Minecraft version (e.g., 4.0.0)
	modImplementation "com.github.StopMotionEGames:Pehkui:VERSION
}
```

If you want some more flexibility, you can add these lines to your build.gradle:

``` Gradle
dependencies {
	if (project.hasProperty("pehkui_mod_version")) {
		modImplementation "com.github.StopMotionEGames:Pehkui:${project.pehkui_mod_version}"
	}
}
```

And in your gradle.properties, add this line:

``` Gradle
// Replace VERSION with your target Minecraft version (e.g., 4.0.0)
pehkui_mod_version=VERSION
```
