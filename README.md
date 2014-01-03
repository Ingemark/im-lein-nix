# lein-nix

A leiningen plugin that contributes an arsenal of composable, *small-is-beautiful* tasks with the intent to replace bundling/deployment shell scripts.

## Usage

Put `[lein-nix "0.1.12"]` into the `:plugins` vector of your `~/.lein/profiles.clj` or `project.clj`.

The best way to use lein-nix is by defining aliases in `project.clj`. The following example demonstrates the usage of most of the tasks:

```clojure
(defproject ...
  :lein-bundle {:filespec ["README.md" "logback.xml"
                           ["config.clj.template" "config.clj"]]}
  :aliases
  {"release" ["xdo"
              ["git-check-clean"]
              ["thrush" ["version-update" ":release"] ["edit-version"]]
              ["deploy" "clojars"]
              ["commit" "New release"]
              ["tag"]
              ["thrush" ["version-update" ":new-snapshot"] ["edit-version"]]
              ["commit" "New snapshot"]
              ["push"]]
     "publish-latest" ["with-checkout" ":latest"
                       "thrush" "uberjar," "bundle" ".," "upload" "bundle"]}
```

- `release` will create a new release version and deploy it to Clojars. This is appropriate for public library projects;

- `publish-latest` will first checkout the latest tagged release and then create and upload a bundle that can be downloaded, unpacked, and executed. This is appropriate for projects that build end-user products.

Note that `xdo` is used in the above example; this has the same syntax as `do` but supports the propagation of changes to the project map.

The following example demonstrates the usage of `into-meta`, `using-meta`, and `tar-deps`:

```clojure
(defproject ...
  :aliases
  {"bundle" ["xdo" ["thrush" ["jar"] ["into-meta" ":bundle-args" "main.jar"]]
                   ["thrush" ["tar-deps"] ["into-meta" ":bundle-args" "deps.tgz"]]
                   ["using-meta" ":bundle-args" "bundle"]]})
```

- `into-meta` and `using-meta` continue where `thrush` stops: when you need to collect the results of several tasks as arguments to a downstream task.


##RELEASE NOTES

0.1.12

Version 0.1.11 was an invalid build, this fixes it.

0.1.11

Fixed and documented `into-meta` and `using-meta`.

0.1.10

Added `into-meta` and `using-meta` tasks.



NOTE: `lein-nix` works with Leiningen 2.1.3 and higher, but the syntax in above examples relies on a feature introduced in Leiningen 2.3.4 (grouping of subtasks into vectors). The examples can be rewritten to the old, command-line oriented syntax, which uses comma as vector delimiters.

## License

Copyright © 2013 Marko Topolnik

Distributed under the Eclipse Public License, the same as Clojure.
