(ns leiningen.push
  (:require [lein-nix.core :refer [sh!]]))

(defn ^:no-project-needed push
"Pushes the current branch the remote repo (including tags)"
  [project]
  (sh! "git" "push")
  (sh! "git" "push" "--tags"))
