(defproject lein-nix "0.1.3-SNAPSHOT"
  :description "An arsenal of composable bundling/deployment tasks"
  :url "https://github.com/Inge-mark/lein-nix"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in :leiningen
  :aliases {"release" ["xdo" "git-check-clean,"
                       "thrush" "version-update" ":release," "edit-version,"
                       "xdo" "deploy" "clojars," "commit" "New release," "tag,"
                       "thrush" "version-update" ":new-snapshot," "edit-version,"
                       "xdo" "commit" "New snapshot," "push"]})
