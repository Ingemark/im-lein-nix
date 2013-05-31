# lein-nix

A leiningen plugin that contributes an arsenal of composable, *small-is-beautiful* tasks with the intent to replace bundling/deployment shell scripts.

## Usage

Put `[lein-nix "0.1.10"]` into the `:plugins` vector of your `~/.lein/profiles.clj` or `project.clj`.

The best way to use lein-nix is by defining aliases in `project.clj`. The following example demonstrates the usage of most of the tasks:

```clojure
(defproject ...
  :lein-bundle {:filespec ["README.md" "logback.xml"
                           ["config.clj.template" "config.clj"]]}
  :aliases {"to-release-version" ["thrush" "version-update" ":release," "edit-version"]
            "to-snapshot" ["thrush" "version-update" ":new-snapshot," "edit-version"]
            "release" ["xdo" "git-check-clean," "to-release-version,"
                       "deploy" "clojars," "commit" "New release," "tag,"
                       "to-snapshot," "commit" "New snapshot," "push"]
            "upload-bundle" ["thrush" "uberjar," "bundle" ".," "upload" "bundle"]
            "publish-latest" ["with-checkout" ":latest" "upload-bundle"]})
```

- `release` will create a new release version and deploy it to Clojars. This is appropriate for public library projects;

- `upload-bundle` will create a bundle that can be downloaded, unpacked, and executed. This is appropriate for projects that build end-user products.

- `to-release-version` and `to-snapshot` are helper aliases not meant to be executed directly. They can't be inlined due to the constraints of the `do` syntax (it doesn't support nested task chaining). Note that `xdo` is used in the above example; this has the same syntax as `do` but supports the propagation of changes to the project map.

The following example demonstrates the usage of `into-meta`, `using-meta`, and `tar-deps`:

```clojure
(defproject ...
  :aliases
  {"bundle-jar" ["thrush" "jar," "into-meta" ":bundle-args" "main.jar"]
   "bundle-deps" ["thrush" "tar-deps," "into-meta" ":bundle-args" "deps.jar"]
   "bundle" ["xdo" "bundle-jar," "bundle-deps," "using-meta" ":bundle-args" "bundle"]})
```

- `into-meta` and `using-meta` continue where `thrush` stops: when you need to collect the results of several tasks as arguments to a downstream task.


##RELEASE NOTES

0.1.10

Added `into-meta` and `using-meta` tasks.



NOTE: `lein-nix` works with Leiningen 2.1.3 and above.

## License

Copyright © 2013 Marko Topolnik

Distributed under the Eclipse Public License, the same as Clojure.
