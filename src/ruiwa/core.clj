(ns ruiwa.core
  (:require [ruiwa.twitter :as twit]))

(defn following-scores
  "Map users followed with 5 points apiece."
  [creds user]
  (let [{:keys [status body]} (twit/following creds user)]
    (if (= 200 (:code status))
      (zipmap (:ids body) (repeat 5))
      {})))

(defn follower-scores
  "Map users followed with 3 points apiece."
  [creds user]
  (let [{:keys [status body]} (twit/followers creds user)]
    (if (= 200 (:code status))
      (zipmap (:ids body) (repeat 3))
      {})))

(defn follow-scores
  "Initialize a map of known user IDs following or followed by the user.
  Following is worth 5 points, being followed is worth 3."
  [creds user]
  (merge-with +
              (following-scores creds user)
              (follower-scores creds user)))

(defn credit-ids
  "Add 2 points to each user."
  [scores ids]
  (reduce (fn [acc e] (update acc e (fnil + 0) 2)) scores ids))

(defn favorited
  "Gets a list of user IDs where the provided user has favorited their tweets."
  [creds user]
  (let [{:keys [status body]} (twit/user-favorites creds user)]
    (if (= 200 (:code status))
      (map #(get-in % [:user :id]) body)
      [])))

(defn retweeted-users
  "Extract retweeted user IDs from a timeline body."
  [body]
  (map #(get-in % [:retweeted_status :user :id]) body))

(defn user-mentions
  "Extract mentioned user IDs from a timeline body."
  [body]
  (->> body
       (map #(get-in % [:entities :user_mentions]))
       (mapcat #(map :id %))))
