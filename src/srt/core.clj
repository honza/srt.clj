(ns srt.core
  (:refer-clojure :exclude [char])
  (:use [cheshire.core :only [generate-string]])
  (:use [the.parsatron])
  (:gen-class))

(defn list->int [x]
  (Integer. (apply str x)))

(def special-parser (token #{\space \tab \, \' \. \? \! \: \> \< \/ \- \% \$ \" \’}))
(def text-parser (choice
                   (many1 (digit))
                   (many1 (letter))
                   (many1 special-parser)))

(defn join [xs]
  (apply str (map (partial apply str) xs)))

(def newline-parser (>> (char \return) (char \newline)))

(defparser line-parser []
  (let->> [text (many1 text-parser)
           _ newline-parser]
    (always (join text))))

(defparser time-parser []
  (let->> [hours   (times 2 (digit))
           _       (char \:)
           minutes (times 2 (digit))
           _       (char \:)
           seconds (times 2 (digit))
           _       (char \,)
           ms      (times 3 (digit))]
    (always {:hours   (list->int hours)
             :minutes (list->int minutes)
             :seconds (list->int seconds)
             :ms      (list->int ms)})))

(defparser timing-parser []
  (let->> [start (time-parser)
           _     (string " --> ")
           end   (time-parser)]
    (always {:start start 
             :end end})))

(defparser subtitle-parser []
  (let->> [number (many1 (digit))
           _ newline-parser
           timing (timing-parser)
           _ newline-parser
           content (many1 (line-parser))
           _ (many newline-parser)
           ]
    (always (assoc timing :content (join content)))))

(defn parse [text]
  (run (many1 (subtitle-parser)) text))

(defn srt->json [srt]
  (generate-string (parse srt) {:pretty true}))

(defn -main
  [& args]
  (let [srt (slurp (first args))]
    (println (srt->json (rest srt)))))
