(ns leiningen.print-args
  (use clojure.pprint))

(defn print-args [project & args]
  (println "args" args)
  (println "project.clj")
  (pprint project))