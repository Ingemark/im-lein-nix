(ns leiningen.commit
  (require [lein-nix.core :refer [sh!]]))

(defn commit
"Commits all changed files to git.

Required argument: commit message."
  [project msg]
  (sh! "git" "commit" "-a" "-m" msg))