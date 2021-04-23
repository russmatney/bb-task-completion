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
                    (:doc task-v
                          ;; falls back to stringified task definition
                          (str task-v))
                    "'")))
        (string/join " ")))
