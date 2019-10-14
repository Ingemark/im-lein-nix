(ns leiningen.version-update
  (:require [clojure.string :as s]))

(defn ->release [v] (s/replace v #"-SNAPSHOT$" ""))

(defn ->new-snapshot [v]
  (str (s/replace v #"\d+(?!.*\d)" #(str (inc (Long/parseLong %))))
       "-SNAPSHOT"))

(defn version-update
"Computes a version-update vector suitable for edit-version.

Required argument: either :release (remove -SNAPSHOT from current version)
or :new-snapshot (bump the least significant number in the version and add
-SNAPSHOT suffix)."
[project arg]
  (let [update-fn (condp = arg
                    ":release" ->release
                    ":new-snapshot" ->new-snapshot)
        curr-version (:version project), new-version (update-fn curr-version)]
    (when (not= curr-version new-version) [curr-version new-version])))
