(ns leiningen.tag
  (require [lein-nix.core :refer [sh!]]))

(defn tag [project]
  (sh! "git" "tag" (apply format "%s-%s" ((juxt :name :version) project))))
