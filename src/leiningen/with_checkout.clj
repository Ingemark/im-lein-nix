(ns leiningen.with-checkout
  (:require [lein-nix.core :refer [sh! abort]]
            (leiningen.core [main :as main] [eval :as eval])
            (clojure.java [shell :as sh] [io :as io])
            [clojure.string :as s]))

(set! *warn-on-reflection* true)

(defn with-checkout
  "Check out a revision from git and apply tasks on it

Note: this task constructs a command line and executes it in a subprocess.
Therefore it does not support vectors in cmdline (as supported by the do task)"
  [project tag & cmdline]
  (let [checkout-dir "target/lein-with-checkout"
        tag (if (= tag ":latest")
              (let [r (sh/sh "git" "describe" "--tags" "--abbrev=0")]
                (when (or (not (zero? (:exit r))) (s/blank? (:out r)))
                  (abort "Cannot determine the latest tag: %s"
                         (str (:err r) (:out r))))
                (s/trim (:out r)))
              tag)]
    (sh! "mkdir" "-p" checkout-dir)
    (try
      (sh! "sh" "-c" (format "git archive %s | tar -xC %s" tag checkout-dir))
      (sh! "sh" "-c"
           (format "cd %s ; %s %s" checkout-dir
                   (System/getProperty "leiningen.script") (s/join " " cmdline)))
      (finally (sh! "rm" "-rf" checkout-dir)))))
