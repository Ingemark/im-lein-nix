(ns lein-nix.core
  (require (leiningen.core [eval :as eval] [main :as main])))

(defn abort [fmt & args] (main/abort (apply format fmt args)))

(defn raise [fmt & args] (throw (Exception. (apply format fmt args))))

(defn sh! [& cmd]
  (apply println "$" cmd)
  (let [res (apply eval/sh cmd)]
    (when-not (zero? res)
      (raise "Command failed with exit code %s: %s" res cmd))
    res))

(defn keywordable? [^String arg] (and arg (.startsWith arg ":")))

(defn parse-opts [args]
  (let [[args opts] (split-with (complement keywordable?) args)]
    [args
     (reduce (fn [opt-map [k v]]
               (when-not (keywordable? k)
                 (raise "Option %s is not a keyword" k))
               (apply assoc opt-map (read-string k)
                      (if (keywordable? v) [true, (read-string v) true] [v])))
             {} (partition-all 2 opts))]))

(defn prj-result [project r] (if (-> r meta :boxed-result)
                               ((juxt :project :result) r)
                               [project r]))

(defn ungroup [groups]
  (-> (for [g (drop-last groups)
            :let [g (update-in g [(dec (count g))] #(str % ","))]
            arg g] arg)
      (concat (last groups))))

(defn higher-order? [project args] (-> args first
                                       (resolve-task (constantly nil))
                                       meta :higher-order))
