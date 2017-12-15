# Android App Shortcuts 

If your app targets Android 7.1 (API level 25) or higher, you can define shortcuts to specific actions in your app. These shortcuts can be displayed in a supported launcher. Shortcuts let your users quickly start common or recommended tasks within your app.

#  Types of shortcuts

1). Static shortcuts 

2). Dynamic shortcuts

3). Pinned shortcuts

# Static Shortcuts:

Static shortcuts are defined in a resource file that is packaged into an APK. Therefore, you must wait until you update your entire app to change the details of these static shortcuts.

# Dynamic Shortcuts:

Dynamic shortcuts are published at runtime using the ShortcutManager API. During runtime, your app can publish, update, and remove its dynamic shortcuts.

# Pinned Shortcuts:

Pinned shortcuts are published at runtime and also use the ShortcutManager API. During runtime, your app can attempt to pin the shortcut, at which time the user receives a confirmation dialog asking their permission to pin the shortcut. The pinned shortcut appears in supported launchers only if the user accepts the pinning request.

