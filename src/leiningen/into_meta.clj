(ns leiningen.into-meta)

(defn into-meta [project value key & more-values]
  (let [key (if (.startsWith key ":") (read-string key) key)
        values (map #(if (map? %) (first (vals %)) %) (concat [value] more-values))]
    ^:boxed-result
    {:project (vary-meta project update-in [key] #(apply (fnil conj []) %1 %2) values)
     :result value}))