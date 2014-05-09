# pudge

The clojurescript REPL needs to talk to a webserver in order to function. It
cannot be hooked to an HTML file opened by a browser due to same-origin
policies.

This thing gives you a quick and dirty way to get started developing
single-page clojurescript apps. It provides the webserver that serves your
single HTML file and whatever static files and turns your clojure REPL into a
clojurescript REPL.

## Usage

Using the pudge-app template (note that this template gets you started with Om):
From a terminal:
```bash
$ lein new pudge-app hello-world
$ cd hello-world
$ lein cljsbuild once
$ lein repl
user> (require '[pudge.core :as p])
user> (p/start-repl! {:port 8000}) ; the map is optional, defaults to port 8000
;; Point your browser to localhost:8000
cljs.user> (js/alert "Hello World")
```

--
Not using the pudge-app template:

project.clj:
```clojure
:dependencies [[org.clojure/clojurescript "0.0-2197"]]
:profiles {:dev {:dependencies [pudge "FIXME"]}}
```
somewhere in index.html, where index.html is a file in your classpath, note that
`goog/base.js` and `my-app.js` also have to be in your classpath:
```html
<script src="/goog/base.js"></script>
<script src="my-app.js"></script>
{{ connection-script|safe }}
```
In your core.cljs file
```clojure
(ns my-project.core
  (:require [clojure.browser.repl]))

In your REPL:
```clojure
user> (require '[pudge.core :refer [start-repl!]])
user> (start-repl!)
;; Then point to localhost:8000
cljs.user> (js/alert "Hello World")
```
```

## License

Copyright © 2014 César Bolaños

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
