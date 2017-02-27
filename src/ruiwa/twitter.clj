(ns ruiwa.twitter
  (:require [twitter.oauth :as oauth]
            [twitter.api.restful :as api]))

;; Note, the Twitter API supports cursors to page through data. At this time,
;; These functions are assuming we only want a single batch (the latest) in
;; order to evaluate "friends".

;; Relevant on all responses:
;; [:headers :x-rate-limit-remaining]
;; [:status :code]

;; [:body :description]
;; [:body :profile_image_url_https]
;; [:body :screen_name]
;; [:body :url]
;; [:body :statuses_count]
;; [:body :favourites_count]
(defn user-info
  "Returns a variety of information about the user. The user’s most recent Tweet
  will be returned inline when possible."
  [creds user]
  (api/users-show :oauth-creds creds
                  :params {:user-id user :include-entities true}))

;; :body -> [{}]
;; :in_reply_to_status_id_str
;; [:entities :hashtags]
;; [:entities :user_mentions [:screen_name]]
;; [:user :screen_name]
(defn user-favorites
  "Returns the most recent Tweets favorited by the specified user."
  [creds user]
  (api/favorites-list :oauth-creds creds
                      :params {:screen-name user :count 200}))

;; [:entities :hashtags]
;; [:entities :user_mentions [:screen_name]]
;; [:retweeted_status :in_reply_to_screen_name] ... when it's a retweet
(defn user-timeline
  "Returns a collection of the most recent Tweets posted by the user."
  [creds user]
  (api/statuses-user-timeline :oauth-creds creds
                              :params {:screen-name user :include-rts true :count 200}))

;; [:body :ids []]
(defn following
  "Returns a collection of user IDs for every user the specified user is following."
  [creds user]
  (api/friends-ids :oauth-creds creds
                   :params {:screen-name user :count 5000}))

(defn followers
  "Returns a collection of user IDs for every user following the specified user."
  [creds user]
  (api/followers-ids :oauth-creds creds
                     :params {:screen-name user :count 5000}))
