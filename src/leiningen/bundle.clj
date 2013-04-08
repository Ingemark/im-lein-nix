(ns leiningen.bundle
  (require [lein-nix.core :refer [sh! parse-opts abort]]
           [clojure.java.io :as io])
  (import java.io.File))

(set! *warn-on-reflection* true)

(defn- as-coll [x] (if (coll? x) x [x]))

(defn bundle [project & args]
  (let [[[src dest] opt-map] (parse-opts args)
        destdir-name (or (:dest-dir opt-map) ".")
        tgz-path (let [dest-dir (io/file destdir-name)]
                   (if (.isDirectory dest-dir)
                     (-> dest-dir
                         (io/file (format "%s-%s.tgz"
                                          ((juxt :name :version) project)))
                         .getPath)
                     (abort "Destination %s is not a directory" destdir-name)))]
    (apply sh! "tar" "cvfz" tgz-path
           (for [filespec (concat (-> project :lein-bundle :filespec)
                                  [(remove nil? [src dest])])
                 :let [[^File src ^File dest] (map io/file (as-coll filespec))]]
             (do (when src (.renameTo src dest))
                 (.getName dest))))))
