(ns leiningen.commit
  (require [lein-nix.core :refer [sh!]]))

(defn commit [project msg]
  (sh! "git" "commit" "-a" "-m" msg))