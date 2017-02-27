(ns ruiwa.core
  (:gen-class)
  (:require [twitter.oauth :as oauth]
            [ruiwa.twitter :as twitter]
            [ruiwa.score :as score]
            [clojure.java.io :as io])
  (:import [java.io PushbackReader]))

(defn- read-conf
  [file]
  (with-open [reader (-> file io/reader PushbackReader.)]
    (clojure.edn/read reader)))

;; Twitter oauth takes four values, but "access token" not needed for this app.
(defn- gen-creds
  [config]
  (let [{api-key :app-consumer-key api-secret :app-consumer-secret} config]
    (oauth/make-oauth-creds api-key api-secret)))

(defn- select-info [creds id score]
  (merge
    {:score score}
    (select-keys
      (:body (twitter/user-info creds id))
      [:description :profile_image_url_https :name :screen_name :url])))

(defn -main [& args]
  (if-let [[conf-file user & _] args]
    (let [config (read-conf conf-file)
          creds (gen-creds config)]
      (doseq [[id score] (take 5 (sort-by val > (score/friends creds user)))]
        (println (select-info creds id score))))))
