(ns leiningen.git-check-clean
  (require [clojure.java.shell :as sh]))

(defn git-check-clean [project]
  (when-not (= 0 (:exit (sh/sh "git" "diff-index" "--quiet" "HEAD")))
    (abort "Cannot proceed: the project has uncommited changes.")))
