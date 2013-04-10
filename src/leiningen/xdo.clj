(ns leiningen.xdo
  (:require [leiningen.core.main
             :refer [resolve-task task-args resolve-and-apply]]
            [leiningen.do :refer [group-args]]
            [lein-nix.core :refer [prj-result]]))

(defn ^:no-project-needed ^:higher-order xdo
  "Just like the regular do, but propagates changes to the project map.

Each comma-separated group should be a task name followed by optional arguments.

USAGE: lein xdo test, compile :all, deploy private-repo"
  [project & args]
  (with-meta (zipmap [:project :result]
                     (reduce (fn [[project] arg-group]
                               (->> (resolve-and-apply project arg-group)
                                    (prj-result project)))
                             [project] (group-args args)))
    {:boxed-result true}))
