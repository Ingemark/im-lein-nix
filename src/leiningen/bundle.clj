(ns leiningen.bundle
  (require [lein-nix.core :refer [sh! parse-opts abort]]
           [clojure.java.io :as io])
  (import java.io.File))

(set! *warn-on-reflection* true)

(defn- as-coll [x] (if (coll? x) x [x]))

(defn- relative-path [^File f]
  (let [canon-fname (.getCanonicalPath f)
        curr-dir (.getCanonicalPath (io/file "."))]
    (cond
     (= canon-fname curr-dir)
     "."
     (.startsWith canon-fname (str curr-dir "/"))
     (subs canon-fname (inc (count curr-dir)))
     :else
     (-> (format "File %s is not within the current directory subtree"
                 canon-fname)
         Exception. throw))))

(defn bundle [project & args]
  (let [[fspec opt-map]
        (parse-opts (for [a args, a (if (map? a) (vals a) [a])] a))
        destdir-name (or (:dest-dir opt-map) ".")
        tgz-path (let [dest-dir (io/file destdir-name)]
                   (if (.isDirectory dest-dir)
                     (-> dest-dir
                         (io/file (apply format "%s-%s.tgz"
                                         ((juxt :name :version) project)))
                         .getPath)
                     (abort "Destination %s is not a directory" destdir-name)))]
    (apply sh! "tar" "cvfz" tgz-path
           (for [filespec (concat (-> project :lein-bundle :filespec)
                                  (partition 2 fspec))
                 :let [[^File src ^File dest] (map io/file (as-coll filespec))
                       ^File dest (if (and dest (.isDirectory dest))
                                    (io/file dest (.getName src))
                                    dest)]]
             (relative-path (if dest
                              (do (.renameTo src dest) dest)
                              src))))
    tgz-path))
