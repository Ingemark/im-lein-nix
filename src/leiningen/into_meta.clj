(ns leiningen.into-meta)

(defn into-meta
  "Add values to project map's metadata

Modifies the project map's metadata by appending (concat [value] more-values) to the
collection under key, creating one if absent."
  [project value key & more-values]
  (let [key (if (.startsWith key ":") (read-string key) key)
        values (map #(if (map? %) (first (vals %)) %) (concat [value] more-values))]
    ^:boxed-result
    {:project (vary-meta project update-in [key] #(apply (fnil conj []) %1 %2) values)
     :result value}))