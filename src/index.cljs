(ns index
  (:require ["@use-gpu/live" :as React :refer [render]]))

(-> (js/import "./LiveComponent")
    (.then  (fn [module]
              (let [app module.App]
                (js/console.log "imported")
                (js/console.log app)
                (render app)))))
