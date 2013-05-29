(ns leiningen.using-meta
  (require [leiningen.core.main :as main]))

(defn ^:higher-order using-meta [project key & cmdline]
  (main/resolve-and-apply project (concat cmdline ((meta project) key))))