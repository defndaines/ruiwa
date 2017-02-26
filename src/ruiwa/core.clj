(ns ruiwa.core
  (:require [twitter.oauth :as oauth]
            [ruiwa.twitter :as twitter]
            [ruiwa.score :as score]
            [clojure.java.io :as io]))


;; Get the top five:
;; (take 5 (sort-by val > score-map))
