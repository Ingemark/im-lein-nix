(defproject lein-nix "0.1.5"
  :description "An arsenal of composable bundling/deployment tasks"
  :url "https://github.com/Inge-mark/lein-nix"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in :leiningen
  :aliases {"to-release-version" ["thrush" "version-update" ":release," "edit-version"]
            "to-snapshot" ["thrush" "version-update" ":new-snapshot," "edit-version"]
            "release" ["xdo" "git-check-clean," "to-release-version,"
                       "deploy" "clojars," "commit" "New release," "tag,"
                       "to-snapshot," "commit" "New snapshot," "push"]})
