(ns srt.core
  (:refer-clojure :exclude [char])
  (:use [the.parsatron])
  (:gen-class))


(def subtitles "1
00:02:17,440 --> 00:02:20,375
Senator, we're making
our final approach into Coruscant.

2
00:02:20,476 --> 00:02:22,501
Very good, Lieutenant.


")

(defn list->int [x]
  (Integer. (apply str x)))

(def special-parser (token #{\space \tab \, \' \.}))
(def text-parser (choice
                   (many1 (digit))
                   (many1 (letter))
                   (many1 special-parser)))

(defn join [xs]
  (apply str (map (partial apply str) xs)))

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
           _ (char \newline)
           timing (timing-parser)
           _ (char \newline)
           content (many1 text-parser)
           _ (char \newline)
           content2 (many text-parser)
           _ (char \newline)
           _ (char \newline)]
    (always (assoc timing
                   :content (str
                              (join content) 
                              \newline 
                              (join content2))))))

(defn parse [text]
  (run (many1 (subtitle-parser)) text))

(defn -main
  [& args]
  (println (parse subtitles)))
