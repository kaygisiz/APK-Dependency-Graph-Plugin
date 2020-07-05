# APK Dependency Graph
[![Build Status](https://travis-ci.org/kaygisiz/Dependency-Injection-Graph.svg?branch=master)](https://travis-ci.org/kaygisiz/Dependency-Injection-Graph)

APK Dependency Graph is a plugin for Android Studio that allows display graph of dependencies.

## How to install
- in Android Studio: go to `Settings → Plugins → Browse repositories` and search for `APK Dependency Graph`

_or_

- [download it](http://plugins.jetbrains.com/plugin/10107) and install via `Preferences → Plugins → Install plugin from disk`

## How to use

1. Go to `Settings → Instan Run` in Android Studio and **disable** `Enable Instant Run to hot swap code/resource changes on deploy`
2. Build Apk with disabled Instant Run
3. Click `Tools → APK Dependency Graph → Generate Dependency Graph`
4. Select apk file from shown dialog
5. Click `Tools → APK Dependency Graph → Set Package Filter` enter `package name` (e.g. **com.example.package**) as a filter so you will avoid unnecessary dependencies in your graph.

### Optional

- Go to `Tools → APK Dependency Graph → Show Generated Graph` to see last generated dependencies graph of current project in browser.
- Select `Tools → APK Dependency Graph → Disable Inner Classes` to define if you want to skip inner classes on your graph or not.
