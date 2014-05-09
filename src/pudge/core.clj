(ns pudge.core
  (:require [org.httpkit.server :as http]
            [selmer.parser :as selmer]
            [clojure.java.io :as io]
            [cemerick.austin]
            [cemerick.austin.repls]
            [ring.middleware.content-type :as middleware]))

(declare make-handler)

(defn start-repl!
  "Turns your clojure REPL into a cljs REPL. Also starts a local webserver that
serves an specified html file on the root page and serves static files on every
other page. The html file and the static files must be in the classpath.

Takes a map of options. Which defaults to:
 {
  :port 8000
  :html-path \"index.html\"
  :script-key :connection-script
  }
- port is the server port.
- html-path must be a string that points to an html file in your classpath.
- script-key is a keyword that must be present as a template placeholder
  in the html file, according to the library Selmer's rules. This will be used
  to insert the '<script>' tag with the required javascript for the browser
  to connect with our REPL. This script must be inserted after the 'goog/base.js'
  script.

The HTML should then contain something like: {{ connection-script|safe }}

For a full example of an index.html file refer to the README.
"
  [& [{:keys [port html-path script-key]
       :or {port 8000 html-path "index.html" script-key :connection-script}}]]
  (let [repl-env (reset! cemerick.austin.repls/browser-repl-env
                         (cemerick.austin/repl-env))
        app (-> (make-handler html-path script-key
                              (str "<script type=\"text/javascript\">"
                                   (cemerick.austin.repls/browser-connected-repl-js)
                                   "</script>"))
                (middleware/wrap-content-type))]
    (http/run-server app {:port port})
    (cemerick.austin.repls/cljs-repl repl-env)))

(defn make-handler
  "Serve the html file on root. Everything else is treated as an static file.
uris sent to the server must be paths on the classpath that point to static
files except for the root uri."
  [html-path script-key connection-js]
  (fn [request]
    (let [uri (:uri request)]
      (if (= "/" uri)
        {:status 200
         :headers {"Content-Type" "text/html"}
         :body (selmer/render-file html-path {script-key connection-js})}
        (if-let [p (io/resource (subs uri 1))]
          {:status 200
           :body (io/file p)}
          {:status 404})))))
