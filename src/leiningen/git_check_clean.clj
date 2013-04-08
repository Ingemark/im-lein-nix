(ns leiningen.git-check-clean
  (require [clojure.java.shell :as sh]
           [leiningen.core.main :as main]))

(defn git-check-clean [project]
  (when-not (= 0 (:exit (sh/sh "git" "diff-index" "--quiet" "HEAD")))
    (main/abort "Cannot proceed: the project has uncommited changes.")))
