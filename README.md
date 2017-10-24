# Dependency-Graph-Plugin

Simple plugin for Android Studio that allows display graph of dependency injections.

## How to install
- in Android Studio: go to `Settings → Plugins → Browse repositories` and search for `Dependency Graph Plugin`

_or_

- [download it](http://plugins.jetbrains.com/plugin/10107) and install via `Preferences → Plugins → Install plugin from disk`

## How to use

1. Download to `apk-dependency-graph`(http://github.com/alexzaitsev/apk-dependency-graph) tool
2. Go to `Settings → Instan Run` in Android Studio and **disable** `Enable Instant Run to hot swap code/resource changes on deploy`
3. Build Apk with disabled Instant Run
4. Click `Tools → Dependency Graph → Display Graph`