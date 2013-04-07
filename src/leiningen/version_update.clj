(ns leiningen.version-update)

(defn ->release [v] (s/replace v #"-SNAPSHOT$" ""))

(defn ->new-snapshot [v]
  (str (s/replace v #"\d+(?!.*\d)" #(str (inc (Long/parseLong %)))) "-SNAPSHOT"))

(defn version-update [project update-fn]
  (let [curr-version (:version project), new-version (update-fn curr-version)]
    (when (not= curr-version new-version) [curr-version new-version])))