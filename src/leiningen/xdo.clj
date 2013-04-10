(ns leiningen.xdo
  (:require [leiningen.core.main
             :refer [resolve-task task-args resolve-and-apply]]
            [leiningen.do :refer [group-args]]
            [lein-nix.core :refer [ungroup prj-result higher-order?]]))

(defn ^:no-project-needed ^:higher-order xdo
  "Higher-order task to perform other tasks in succession.

Each comma-separated group should be a task name followed by optional arguments.

USAGE: lein xdo test, compile :all, deploy private-repo"
  [project & args]
  (reduce
   (fn [[project] [arg-group, :as groups]]
     (if (higher-order? project arg-group)
       (reduced (resolve-and-apply project (ungroup groups)))
       (->> (resolve-and-apply project arg-group) (prj-result project))))
   [project]
   (take-while (complement nil?) (iterate next (group-args args)))))
