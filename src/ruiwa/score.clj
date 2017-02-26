;;;; Calculate friend scores by accessing the Twitter API.
(ns ruiwa.score
  (:require [ruiwa.twitter :as twit]))

;;; NOTE: Not checking for rate-limiting at this time.
;;; NOTE: Some repeated patterns here could be extracted.

;;; Values added for each occurrence.
(def following-val 5)
(def follower-val 2)
(def favorited-val 4)
(def retweet-val 3)
(def mention-val 2)

(defn following-scores
  "Map users followed with 5 points apiece."
  [creds user]
  (let [{:keys [status body]} (twit/following creds user)]
    (if (= 200 (:code status))
      (zipmap (:ids body) (repeat following-val)))))

(defn follower-scores
  "Map users followed with 3 points apiece."
  [creds user]
  (let [{:keys [status body]} (twit/followers creds user)]
    (if (= 200 (:code status))
      (zipmap (:ids body) (repeat follower-val)))))

(defn credit-ids
  "Add points to each user ID."
  [ids points]
  (reduce (fn [acc e] (update acc e (fnil + 0) points)) {} ids))

(defn favorited
  "Gets scores for user IDs where the provided user has favorited their tweets."
  [creds user]
  (let [{:keys [status body]} (twit/user-favorites creds user)]
    (if (= 200 (:code status))
      (-> (map #(get-in % [:user :id]) body)
          (credit-ids favorited-val)))))

(defn retweeted-users
  "Extract retweeted user IDs from a timeline body."
  [body]
  (remove nil?
          (map #(get-in % [:retweeted_status :user :id]) body)))

(defn user-mentions
  "Extract mentioned user IDs from a timeline body."
  [body]
  (->> body
       (map #(get-in % [:entities :user_mentions]))
       (mapcat #(map :id %))))

(defn timelined
  "Extract user IDs of value from timeline."
  [creds user]
  (let [{:keys [status body]} (twit/user-timeline creds user)]
    (if (= 200 (:code status))
      (merge-with +
                  (credit-ids (retweeted-users body) retweet-val)
                  (credit-ids (user-mentions body) mention-val)))))

(defn friends
  "Assemble friend scores for a user."
  [creds user]
  (merge-with +
              (following-scores creds user)
              (follower-scores creds user)
              (favorited creds user)
              (timelined creds user)))

;; Get the top five:
;; (take 5 (sort-by val > score-map))
