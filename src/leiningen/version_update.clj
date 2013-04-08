(ns leiningen.version-update
  (require [clojure.string :as s]))

(defn ->release [v] (s/replace v #"-SNAPSHOT$" ""))

(defn ->new-snapshot [v]
  (str (s/replace v #"\d+(?!.*\d)" #(str (inc (Long/parseLong %))))
       "-SNAPSHOT"))

(defn version-update [project arg]
  (let [update-fn (condp = arg
                    ":release" ->release
                    ":new-snapshot" ->new-snapshot)
        curr-version (:version project), new-version (update-fn curr-version)]
    (when (not= curr-version new-version) [curr-version new-version])))
