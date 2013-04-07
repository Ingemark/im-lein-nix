(ns leiningen.xdo
  (:require [leiningen.core.main
             :refer [resolve-task task-args resolve-and-apply]]
            [leiningen.do :refer [group-args]]))

(defn prj-result [project r] (if (-> r meta :boxed-result)
                               ((juxt :project :result) r)
                               [project r]))

(defn ungroup [groups]
  (for [g groups] (update-in g [(dec (count g))] #(str % ","))))

(defn higher-order? [project args] (-> (task-args args project) first
                                       (resolve-task (constantly nil))
                                       meta :higher-order))

(defn ^:no-project-needed ^:higher-order xdo
  "Higher-order task to perform other tasks in succession.

Each comma-separated group should be a task name followed by optional arguments.

USAGE: lein xdo test, compile :all, deploy private-repo"
  [project & args]
  (reduce
   (fn [[project] [arg-group, :as groups]]
     (if (higher-order? arg-group project)
       (reduced (resolve-and-apply project (ungroup groups)))
       (->> (resolve-and-apply project arg-group) (prj-result project))))
   [project]
   (iterate next (group-args args))))