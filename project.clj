(defproject drfa-clojure "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/test.check "0.10.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 ]
  :main ^:skip-aot drfa-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
