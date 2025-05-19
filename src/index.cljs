(ns index
  (:require ["@use-gpu/live" :refer [render]]
            ["@use-gpu/react" :refer [HTML LiveCanvas]]
            ["@use-gpu/inspect/theme.css"]
            ["./LiveComponent" :refer [Component]]
            ["./Fallback" :refer [makeFallback]]
            ["react" :as react]))

;; React component that uses LiveCanvas to integrate USE.GPU
(defn App []
  #jsx [LiveCanvas {:fallback (fn [error] #jsx [(makeFallback error)])}
        (fn [canvas] #jsx [Component {:canvas canvas}])])

(set! (.-displayName App) "App")

(defn init []
  (if-let [_root-element (js/document.querySelector "#use-gpu")]
    (do
      (js/console.log "Found #use-gpu element, rendering App")
      (render #jsx [App]))))

(if (= (.-readyState js/document) "loading")
  (js/document.addEventListener "DOMContentLoaded" init)
  (init))

;; Also try on window load as a fallback
(set! (.-onload js/window) init)

;; Export the App component for use in larger React applications
(def ^:export ReactApp App)
