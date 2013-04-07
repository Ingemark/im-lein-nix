(ns leiningen.push
  (require [lein-nix.core :refer [sh!]]))

(defn ^:no-project-needed push [project]
  (sh! "git" "push")
  (sh! "git" "push" "--tags"))