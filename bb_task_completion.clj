;; generate bb-task completions with relative ./bb.edn
(require '[clojure.string :as string])
(require '[clojure.edn :as edn])
(require '[babashka.fs :as fs])

(defn stringify-task [t]
  (string/replace (str t) #"('|\")" ""))

(defn help-string [t]
  (:doc t (stringify-task t)))

(when (fs/exists? "./bb.edn")
      (->>
        "./bb.edn"
        slurp
        edn/read-string
        :tasks
        (remove (comp keyword? first))
        (map (fn [[name task-v]]
               (str name ":'" (help-string task-v) "'")))
        (string/join " ")))
