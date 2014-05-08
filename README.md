# pudge

The clojurescript REPL needs to talk to a webserver in order to function. It
cannot be hooked to an HTML file opened by a browser due to same-origin
policies.

This thing gives you a quick and dirty way to get started developing
single-page clojurescript apps. It provides the webserver that serves your
single HTML file and whatever static files and turns your clojure REPL into a
clojurescript REPL.

## Quickstart
project.clj:

```clojure
:profiles {:dev {:dependencies [pudge "FIXME"]}}
```
somewhere in index.html, where index.html is a file in your classpath:
```html
<script src="/goog/base.js"></script>
<script src="my-app.js"></script>
{{ connection-script|safe }}
```
In your REPL:
```
user> (require '[pudge.core :refer [start-repl!]])
user> (start-repl!)
;; Then point to localhost:8000
cljs.user> (js/alert "Hello World")
```

## License

Copyright © 2014 César Bolaños

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
