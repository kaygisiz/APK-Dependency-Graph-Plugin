# Dependency Injection Graph

Simple plugin for Android Studio that allows display graph of dependency injections.

## How to install
- in Android Studio: go to `Settings → Plugins → Browse repositories` and search for `Dependency Graph Plugin`

_or_

- [download it](http://plugins.jetbrains.com/plugin/10107) and install via `Preferences → Plugins → Install plugin from disk`

## How to use

1. Go to `Settings → Instan Run` in Android Studio and **disable** `Enable Instant Run to hot swap code/resource changes on deploy`
2. Build Apk with disabled Instant Run
3. Click `Tools → Dependency Graph → Display Graph`
4. Select apk file

### Optional

- Go to `Tools → Dependency Graph → Set Package Filter` enter `package name` (e.g. **com.example.package**) as a filter so you will avoid unnecessary dependencies in your graph, default parameter is `nofilter`.
- Select `Tools → Dependency Graph → Disable Inner Classes` to define if you want to skip inner classes on your graph or not.
