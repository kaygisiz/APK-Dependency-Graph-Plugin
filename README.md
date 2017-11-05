# Dependency Injection Graph

Dependency Injection Graph is a plugin for Android Studio that allows display graph of dependency injections.

## How to install
- in Android Studio: go to `Settings → Plugins → Browse repositories` and search for `Dependency Injection Graph`

_or_

- [download it](http://plugins.jetbrains.com/plugin/10107) and install via `Preferences → Plugins → Install plugin from disk`

## How to use

1. Go to `Settings → Instan Run` in Android Studio and **disable** `Enable Instant Run to hot swap code/resource changes on deploy`
2. Build Apk with disabled Instant Run
3. Click `Tools → Dependency Injection Graph → Generate Dependency Injection Graph`
4. Select apk file from shown dialog
5. Click `Tools → Dependency Injection Graph → Set Package Filter` enter `package name` (e.g. **com.example.package**) as a filter so you will avoid unnecessary dependencies in your graph.

### Optional

- Go to `Tools → Dependency Injection Graph → Show Generated Dependencies` to see last generated dependencies graph of current project in browser.
- Select `Tools → Dependency Injection Graph → Disable Inner Classes` to define if you want to skip inner classes on your graph or not.

## Credits

Thanks to [Alex Zaitsev](https://github.com/alexzaitsev). I have inspired by his project: https://github.com/alexzaitsev/apk-dependency-graph	
I used source codes of this project to analyze dependencies of decompiled apk, web scripts to show these dependencies in browser.