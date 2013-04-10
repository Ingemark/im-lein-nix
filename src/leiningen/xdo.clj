(ns leiningen.xdo
  (:require [leiningen.core.main
             :refer [resolve-task task-args resolve-and-apply]]
            [leiningen.do :refer [group-args]]
            [lein-nix.core :refer [prj-result]]))

(defn ^:no-project-needed ^:higher-order xdo
  "Higher-order task to perform other tasks in succession.

Each comma-separated group should be a task name followed by optional arguments.

USAGE: lein xdo test, compile :all, deploy private-repo"
  [project & args]
  ^:boxed-result
  (zipmap [:project :result]
          (reduce (fn [[project] arg-group] (->> (resolve-and-apply project arg-group)
                                                 (prj-result project)))
                  [project] (group-args args))))
