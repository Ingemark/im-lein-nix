(ns leiningen.thrush
  (:require [leiningen.core.main :refer [resolve-and-apply]]
            [leiningen.do :refer [group-args]]
            [leiningen.xdo :refer [higher-order? prj-result ungroup]]))

(defn ^:no-project-needed ^:higher-order thrush
  "Like ->, but for leiningen tasks.

Each comma-separated group should be a task name followed by optional arguments."
  [project & args]
  (reduce
   (fn [[project r] [[task-name & args :as group] :as groups]]
     (if (higher-order? project group)
       (reduced (resolve-and-apply project (ungroup groups)))
       (->> (resolve-and-apply project (concat [task-name r] args))
            (prj-result project))))
   [project nil]
   (iterate next (group-args args))))
