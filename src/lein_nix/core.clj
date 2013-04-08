(ns lein-nix.core
  (require (leiningen.core [eval :as eval] [main :as main])))

(defn sh! [& cmd]
  (apply println "$" cmd)
  (let [res (apply eval/sh cmd)]
    (when-not (zero? res)
      (main/abort "Command failed with exit code %s: %s" res cmd))
    res))

(defn abort [fmt & args] (main/abort (apply format fmt args)))

(defn keywordable? [^String arg] (and arg (.startsWith arg ".")))

(defn parse-opts [args]
  (let [[args opts] (split-with (complement keywordable?) args)]
    [args
     (reduce (fn [opt-map [k v]]
               (when-not (keywordable? k)
                 (throw (Exception. (format "Option %s is not a keyword" k))))
               (apply assoc opt-map (read-string k)
                      (if (keywordable? v) [true, (read-string v) true] [v])))
             {} (partition-all 2 opts))]))