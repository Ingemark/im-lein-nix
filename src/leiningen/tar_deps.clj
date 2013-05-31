(ns leiningen.tar-deps
  (require (leiningen.core [classpath :as classpath]
                           [project :as project])
           [leiningen.jar :as jar]
           [clojure.java.io :as io])
  (import (org.apache.tools.tar TarEntry TarOutputStream)
          java.io.FileOutputStream
          java.util.zip.GZIPOutputStream))

(defn deps-for [project]
  (->> (classpath/resolve-dependencies
        :dependencies (merge (project/unmerge-profiles project [:default])
                             (select-keys project jar/whitelist-keys)))
       (filter #(.endsWith (.getName %) ".jar"))))

(defn- add-file [tar f]
  (let [entry (doto (TarEntry. f) (.setName (.getName f)))]
    (when (.canExecute f) (.setMode entry 0755))
    (.putNextEntry tar entry)
    (when-not (.isDirectory f) (io/copy f tar))
    (.closeEntry tar)))

(defn tar-deps [project]
  (let [tar-file
        (doto (io/file (:root project) "target"
                       (apply format "%s-%s-dependencies.tgz"
                              ((juxt :name :version) project)))
          io/make-parents)]
    (with-open [tar (-> tar-file FileOutputStream. GZIPOutputStream.
                        TarOutputStream.)]
      (.setLongFileMode tar TarOutputStream/LONGFILE_GNU)
      (doseq [j (deps-for project)] (add-file tar j))
      (println "Wrote" (.getName tar-file))
      (.getPath tar-file))))
