# Changelog

## Unreleased

### Fixed

- `hx.hooks/useMemo`: deps are optional and can be CLJS collection.

## [0.5.3] - July 31, 2019

### Changed

- `useIRef` now uses `specify` instead of a wrapper, and refers to the `current`
property on _itself_. This allows you to pass in the ref object directly to DOM
ref prop.

## [0.5.2] - May 16, 2019

### Added

- Alpha hooks `useSmartEffect`, `useSmartLayoutEffect`, `useSmartMemo` that auto-detect 
dependencies based on the body.

### Fixed

- `useState` and `useIRef` now properly memoize their dispatch functions to prevent
unnecessary renders.
- `useReducer` can now be passed IFn's like multimethods

## [0.5.1] - May 05, 2019

### Fixed

- Function-as-children were not passing props correctly

## [0.5.0]

### Fixed

- `<-value` hook mutated ref in render, making it not safe for use in Concurrent React
- Converting prop `className` to `class` was only done when hiccup parsing; now also happens in `defnc`
so it works with `cloneElement`.
- Improved hiccup interpretation by 10x

### Changed

- Deprecated Hooks names prepended with `<-`; adopted `useXyzAbc` convention
- Changed API for extending components; now you can call `(extend-tag :new-tag ReactComponentToUse)`

### Added

- A new `hx.hooks.alpha` namespace with experimental Hooks
- Alpha-level "reloadable" hook `useReloadable` and `useStateOnce` that persist
their state across hot reloads.


## [0.4.0] - Mar 26, 2019

### Changed

- `<-state` now returns a `[value set-value]` tuple instead of an atom.
- props passed into hiccup are only converted camel->kebab and back if the element is a keyword.
- `hx.hiccup` is now cross-platform (CLJ + CLJS) and has been decoupled from React

### Added

- `<-value` hook that caches the Clojure value passed into it, and only returns a
(referentially) different value if it is `not=` the previous one. Useful for 
optimizing renders with things that might return the same data structurally, but
with a different reference.
- `<-state`'s update function can take arguments like `swap!`, e.g. `(set-value assoc :foo "bar")`
- `<-state` can take a third argument: a function that returns `true` if the new
value and previous value are equal. If true, it will not apply the change.
- Lots of tests
- `defnc` and `defcomponent` components now have prettier display names (for e.g. React devtools)
- `defnc` now can be passed a `:wrap` option with a collection of higher-order 
components to wrap the component in

### Fixed

- `:class` prop having value `nil` when `:class-name` not present
- Special chars (e.g. `?`) in props being dropped
- `<-effect` where a function is not returned by `f` caused an error
- `<-ref` was just broken

## [0.3.3] - Mar 08, 2019

### Added

- `:provider` component and `hx.react/create-context` function

### Fixed

- Race condition in `<-deref` hook
- Props with numbers, two-letter camel-case were dropped or truncated


## [0.3.2] - Feb 25, 2019

### Fixed

- `<-state`: `swap!` works on a `<-state` atom inside of `<-effect`
- `className` prop is available as either `:class` or `:class-name` when 
parsing props in `defnc` to provide parity with hiccup parser's conversion of
`:class` prop to `className`
- namespaced keywords are now preserved in props maps

## [0.3.1] - Feb 20, 2019

### Fixed

- `<-ref`: Pass initial value to `react/useRef`

## [0.3.0] - Feb 10, 2019

### Changed

- Moved `hx.react.hooks` to `hx.hooks` (breaking)
- `<-deref`: no longer takes a `deps` argument, but will automatically re-watch
if atom argument changes (breaking)
- `<-deref`: Fix bug where two calls on the same atom didn't work as expected
- `<-effect`: Convert second argument `deps` from CLJS collection to array
- `<-ref`: Have deref return the `current` value in React ref (breaking)

### Added

- Additional core hooks:
  - `<-memo`
  - `<-callback`
  - `<-imperative-handle`
  - `<-layout-effect`
  - `<-debug-value`
