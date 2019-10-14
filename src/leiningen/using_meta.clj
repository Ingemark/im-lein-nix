(ns leiningen.using-meta
  (:require [leiningen.core.main :as main]))

(defn ^:higher-order using-meta
  "Invoke a task using project map's metadata

Retrieves the collection under key in project map's metadata, concatenates it
onto task-call, and evaluates the resulting form as a leiningen task invocation.
Designed to be used in conjunction with into-meta, which prepares the collection in
project map's metadata."
  [project key & task-call]
  (main/resolve-and-apply
    project (concat task-call ((meta project) (if (.startsWith key ":") (read-string key) key)))))
