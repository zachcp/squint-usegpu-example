;; The launch script needs to be async for use.gpu to load
(ns index
  (:require ["react-dom/client" :as ReactDOM]
            ["react" :as React]))

(defn init []
  (js/console.log "Initializing app...")
  (-> (js/import "./App")
      (.then (fn [module]
               (let [App (.-App module)
                     root (ReactDOM/createRoot (js/document.getElementById "use-gpu"))]
                 (js/console.log "App component loaded:" App)
                 (.render root #jsx [React/StrictMode [App]]))))))

(init)
