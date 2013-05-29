(ns leiningen.print-args
  (use clojure.pprint))

(defn print-args [project & args]
  (println "args" args)
  (println "project")
  (pprint project)
  (println "\n\nproject meta")
  (pprint (update-in (meta project) [:profiles] dissoc :leiningen/test)))