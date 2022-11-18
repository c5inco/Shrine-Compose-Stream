# Shrine Compose livestream
Repo for building Shrine with Jetpack Compose, on Material Design Live. 

Shrine is an e-commerce Material Design case study: https://material.io/design/material-studies/shrine.html. This is a prototype, focused on translating the design intent, intended for teaching designers and those new to Jetpack Compose on how to build UI.

Progress:

- [x] Backdrop
- [x] Cart
- [x] Catalog
- [ ] Details

![Shrine stickersheet from Material Design website](https://lh3.googleusercontent.com/b84vzNg1_NOFTBQkVpa-lMy4VX-wyIZGET8nbV7MFgcIsnJa8AMojLZXgvM8QFkuSPlCpxr831Z2xdbNbBIR7hsKzIuzgxpY7Rhzcw=w1024-v0)

## 🧑‍💻 Development setup

- [Android Studio Dolphin 2021.3.1](https://developer.android.com/studio) or newer
- [Compose 1.1.1+](https://developer.android.com/jetpack/compose)

**Note:** Above Studio version required only for specific Animation Inspector features, otherwise Bumblebee 2021.1.1 should still work at a minimum to build and deploy the app

## 📚 Topics covered

### Episode 5 ([stream](https://www.youtube.com/watch?v=zfCgp-r1J1s))
- Adding the Catalog to the Backdrop front layer
  - Create the custom Card
  - Build a weaved list with LazyRow
- Hook up interaction to add items to the cart

### Episode 4 ([stream](https://www.youtube.com/watch?v=sNxRwTVGuOI))
- Creating the expanding Cart [BottomSheet](https://material.io/components/sheets-bottom#expanding-bottom-sheet)
  - Build the collapsed version of Cart
  - Expanded <-> collapsed transition of Cart
- ~~Add animated Checkout button to expanded cart~~
- Hide/show Cart bottom sheet in coordination with Backdrop

### Episode 3 ([stream](https://www.youtube.com/watch?v=nCPEuWCQlWk))
- Intro to animation
  - APIs
    - updateTransition
    - AnimatedVisibility
    - AnimatedContent
  - Animation Inspector
- Animating parts of Backdrop
  - [Motion in Shrine](https://material.io/design/material-studies/shrine.html#motion)
  - TopAppBar Text <-> Search field
  - Navigation menu items

### Episode 2 ([stream](https://www.youtube.com/watch?v=T9uMu8nIVM0))
- Intro to state management
- Slot-based approach in Compose
- Creating the navigation [Backdrop](https://developer.android.com/jetpack/compose/layouts/material#backdrop) menu
  - Layers
    - Placeholder front layer
    - TopAppBar
    - Back layer with menu items
  - State
    - Show selection of nav menu item

### Episode 1 ([stream](https://www.youtube.com/watch?v=6-1l2nrJpqI))
- Compose basics on building UI
- Material Theming
  - Color, shape, and typography
  - [Shrine theme](https://material.io/design/material-studies/shrine.html#color)
- Using Material components in Compose
  - Button, Card, Icons
- Creating a List with a complex list item
- Using @Preview for your design components
- Deploying our first screen to a device!

## 🧰 Helpful resources

- [Full YouTube Playlist of series and highlights](https://www.youtube.com/playlist?list=PLsoLz-E4Os4UMUXAuhpXaQzN4d8B9mQqV)
- Compose Material
  - [Compose Material reference docs](https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary)
  - [Compose Material Components layout guide](https://developer.android.com/jetpack/compose/layouts/material)
- General Compose
  - [Tutorial](https://developer.android.com/jetpack/compose/tutorial)
  - [Animation](https://developer.android.com/jetpack/compose/animation)
  - [Other guides](https://developer.android.com/jetpack/compose/documentation)
  - [Sample apps on GitHub](https://github.com/android/compose-samples)
- Design resources
  - "Slot" method in Figma - [YouTube](https://www.youtube.com/watch?v=FOGgsPz3UTk), [Community file](https://www.figma.com/community/file/969234311094210750)
  - [Material studies](https://material.io/design/material-studies)
- Other tools
  - [scrcpy](https://github.com/Genymobile/scrcpy) - mirrors physical Android devices on desktop
  - [Direct](https://github.com/material-motion/direct) - animation spec tool made by Google
- Kotlin resources
  - [Learn Kotlin by example](https://play.kotlinlang.org/byExample/overview)
  - [Beginner Kotlin course on Codeacademy](https://www.codecademy.com/learn/learn-kotlin)

## Disclaimer

This project is not an official Google project. It is not supported by Google and Google specifically disclaims all warranties as to its quality, merchantability, or fitness for a particular purpose.
