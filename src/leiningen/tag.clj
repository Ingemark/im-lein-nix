(ns leiningen.tag
  (:require [lein-nix.core :refer [sh!]]))

(defn tag
"Tags the HEAD commit with the project name and version."
  [project]
  (sh! "git" "tag" (apply format "%s-%s" ((juxt :name :version) project))))
