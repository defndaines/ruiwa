(defproject ruiwa "0.1.0-SNAPSHOT"
  :description "A Twitter 'friend' analyzer."
  :url "https://github.com/defndaines/ruiwa"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [twitter-api "0.8.0"]]
  :main ^:skip-aot ruiwa.core
  :target-path "target/%s"
  :resource-paths ["resources"]
  :profiles {:uberjar {:aot :all}})
