(defproject pudge "0.1.0-SNAPSHOT"
  :description "Hook a cljs repl to an html file as fast as possible."
  :url "https://github.com:cesarbp/pudge.git"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [http-kit "2.1.16"]
                 [ring/ring-core "1.2.2"]
                 [selmer "0.6.6"]
                 [com.cemerick/austin "0.1.5-SNAPSHOT"]])
