(ns leiningen.edit-version)

(defn update-project! [project new-version]
  (when new-version
    (println "Edit project.clj: set version to" new-version)
    (-> (slurp "project.clj")
        (s/replace #"(defproject\s+\S+\s+)\"(.+?)\""
                   (format "$1\"%s\"" new-version))
        (->> (spit "project.clj")))
    (-> (assoc project :version new-version)
        (vary-meta assoc-in [:without-profiles :version] new-version))))

(defn edit-version [project [curr-v new-v]]
  (when-let [new-prj (update-project! project new-v)]
    ^:boxed-result {:result new-v, :project new-prj}))
