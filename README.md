# lein-nix

A leiningen plugin that contributes an arsenal of composable, *small-is-beautiful* tasks with the intent to replace bundling/deployment shell scripts.

## Usage

Put `[lein-nix "0.1.0"]` into the `:plugins` vector of your `~/.lein/profiles.clj` or `project.clj`.

Now you can issue a command such as
```
$ lein xdo git-check-clean, \
  thrush version-update :release, edit-version, \
  xdo deploy clojars, commit "New release", tag, \
  thrush version-update :new-snapshot, edit-version, \
  xdo commit "New snapshot", push
```
or, better, define this as an alias in `project.clj`:
```clojure
(defproject ...
  :aliases {"release"
              ["xdo" "git-check-clean,"
               "thrush" "version-update" ":release," "edit-version,"
               "xdo" "deploy" "clojars," "commit" "New release," "tag,"
               "thrush" "version-update" ":new-snapshot," "edit-version,"
               "xdo" "commit" "New snapshot," "push"]})
```
NOTE: `lein-nix` requires a still-unreleased version of Leiningen (it works against the current state of its `master` branch).

## License

Copyright © 2013 Marko Topolnik

Distributed under the Eclipse Public License, the same as Clojure.
