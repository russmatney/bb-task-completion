;; generate bb-task completions with relative ./bb.edn
(require '[clojure.string :as string])
(require '[clojure.edn :as edn])
(require '[babashka.fs :as fs])

(when (fs/exists? "./bb.edn")
      (->>
        "./bb.edn"
        slurp
        edn/read-string
        :tasks
        (remove (comp keyword? first))
        (map (fn [[name task-v]]
               (str name ":'"
                    ;; TODO stringified task definition for now b/c it's easy
                    ;; could reach for a task docstring here
                    (str task-v)
                    "'")))
        (string/join " ")))
