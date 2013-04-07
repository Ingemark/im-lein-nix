(ns lein-nix.core)

(defn sh! [& cmd]
  (apply println "$" cmd)
  (let [res (apply eval/sh cmd)]
    (when-not (zero? res) (abort "Command failed with exit code %s: %s" res cmd))
    res))
