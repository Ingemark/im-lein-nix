(defproject lein-nix "0.1.12"
  :description "An arsenal of composable bundling/deployment tasks"
  :url "https://github.com/Inge-mark/lein-nix"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in :leiningen
  :aliases
  {"release" ["xdo"
              ["git-check-clean"]
              ["clean"]
              ["thrush" ["version-update" ":release"] ["edit-version"]]
              ["deploy" "clojars"]
              ["commit" "New release"]
              ["tag"]
              ["thrush" ["version-update" ":new-snapshot"] ["edit-version"]]
              ["commit" "New snapshot"]
              ["push"]]}
  :dependencies [[org.apache.ant/ant "1.9.0"]])
