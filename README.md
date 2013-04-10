# lein-nix

A leiningen plugin that contributes an arsenal of composable, *small-is-beautiful* tasks with the intent to replace bundling/deployment shell scripts.

## Usage

Put `[lein-nix "0.1.6"]` into the `:plugins` vector of your `~/.lein/profiles.clj` or `project.clj`.

The best way to use lein-nix is by defining aliases in `project.clj`:
```clojure
(defproject ...
  :aliases {"to-release-version" ["thrush" "version-update" ":release," "edit-version"]
            "to-snapshot" ["thrush" "version-update" ":new-snapshot," "edit-version"]
            "release" ["xdo" "git-check-clean," "to-release-version,"
                       "deploy" "clojars," "commit" "New release," "tag,"
                       "to-snapshot," "commit" "New snapshot," "push"]})
```
NOTE: `lein-nix` requires a still-unreleased version of Leiningen (it works against the current state of its `master` branch).

## License

Copyright © 2013 Marko Topolnik

Distributed under the Eclipse Public License, the same as Clojure.
