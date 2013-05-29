(ns leiningen.into-meta)

(defn into-meta [project value key]
  (let [key (if (.startsWith key ":") (read-string key) key)
        value (if (map? value) (first (vals value)) value)]
    ^:boxed-result
    {:project (vary-meta project update-in [key] (fnil conj []) value)
     :result value}))